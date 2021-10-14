package com.streep.openglleer.util;

import static org.lwjgl.opengl.GL11.GL_NEAREST;
import static org.lwjgl.opengl.GL11.GL_RGBA;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.GL_UNPACK_ALIGNMENT;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glGenTextures;
import static org.lwjgl.opengl.GL11.glPixelStorei;
import static org.lwjgl.opengl.GL11.glTexImage2D;
import static org.lwjgl.opengl.GL11.glTexParameteri;
import static org.lwjgl.opengl.GL30.glGenerateMipmap;

import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;
import static org.lwjgl.system.MemoryUtil.*;

public class Texture {
	
	private int id = 0;
	
	public Texture(String fileName) {
		this.id = loadTexture(fileName);
	}

	private static int loadTexture(String fileName) {
	    int width;
	    int height;
        try {
        	BufferedImage i = ImageIO.read(new File(fileName));
        	ByteBuffer buf = convertImage(i);
	        width = i.getWidth();
	        height = i.getHeight();

        	int ID = glGenTextures();
            
            glBindTexture(GL_TEXTURE_2D, ID);
            
            glPixelStorei(GL_UNPACK_ALIGNMENT, 1);
            
            glGenerateMipmap(GL_TEXTURE_2D);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
            
            glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, buf);
            memFree(buf);
	        return ID;
        } catch(Exception e) {
        	System.err.println("Could not load texture " + fileName + ". Reason:\n");
        	e.printStackTrace();
        	return 0;
        }
	}
	
	
	
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

	public int getId() {
		return id;
	}
}
