package com.streep.openglleer.util;

import static org.lwjgl.opengl.GL11.GL_RGBA;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_UNPACK_ALIGNMENT;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glGenTextures;
import static org.lwjgl.opengl.GL11.glPixelStorei;
import static org.lwjgl.opengl.GL11.glTexImage2D;
import static org.lwjgl.opengl.GL40.*;
import static org.lwjgl.stb.STBImage.stbi_failure_reason;
import static org.lwjgl.stb.STBImage.stbi_load;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import org.lwjgl.system.MemoryStack;

public class Texture {
	
	private int id = 0;
	
	public Texture(String fileName) {
		try {
			this.id = loadTexture(fileName);
		} catch(Exception e) {
			System.err.println("Could not load texture " + fileName +" error:");
			e.printStackTrace();
			this.id = 0;
		}
	}

	private static int loadTexture(String fileName) throws Exception {
	    int width;
	    int height;
	    ByteBuffer buf;
	    // Load Texture file
	    try (MemoryStack stack = MemoryStack.stackPush()) {
	        IntBuffer w = stack.mallocInt(1);
	        IntBuffer h = stack.mallocInt(1);
	        IntBuffer channels = stack.mallocInt(1);

	        buf = stbi_load(fileName, w, h, channels, 4);
	        if (buf == null) {
	            throw new Exception("Image file [" + fileName  + "] not loaded: " + stbi_failure_reason());
	        }

	        /* Get width and height of image */
	        width = w.get();
	        height = h.get();
	     }
		// Create a new OpenGL texture 
		int textureId = glGenTextures();
		// Bind the texture
		glBindTexture(GL_TEXTURE_2D, textureId);
		glPixelStorei(GL_UNPACK_ALIGNMENT, 1);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		glGenerateMipmap(GL_TEXTURE_2D);
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height,
			    0, GL_RGBA, GL_UNSIGNED_BYTE, buf);
		glBindTexture(GL_TEXTURE_2D,0);
		return textureId;
	}
	
	
	/*
	//The code below is copied from the internet
	//Source http://forum.lwjgl.org/index.php?topic=5901.0
    private static final int BYTES_PER_PIXEL = 4;
    
	public static ByteBuffer convertImage(BufferedImage image)
	{     
	    int[] pixels = new int[image.getWidth() * image.getHeight()];
	    image.getRGB(0, 0, image.getWidth(), image.getHeight(), pixels, 0, image.getWidth());
	     
	    ByteBuffer buffer = BufferUtils.createByteBuffer(image.getWidth() * image.getHeight() * BYTES_PER_PIXEL);
	     
	    for(int y = 0; y < image.getHeight(); y++)
	    {
	        for(int x = 0; x < image.getWidth(); x++)
	        {
	            int pixel = pixels[y * image.getWidth() + x];
	            buffer.put((byte) ((pixel >> 16) & 0xFF));     // Red component
	            buffer.put((byte) ((pixel >> 8) & 0xFF));      // Green component
	            buffer.put((byte) (pixel & 0xFF));               // Blue component
	            buffer.put((byte) ((pixel >> 24) & 0xFF));    // Alpha component. Only for RGBA
	        }
	    }
	 
	    buffer.flip();
	     
	    return buffer;
	}
	*/

	public int getId() {
		return id;
	}
}
