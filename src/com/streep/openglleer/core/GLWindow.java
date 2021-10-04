package com.streep.openglleer.core;

import org.lwjgl.glfw.*;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.*;

import java.nio.*;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

public class GLWindow {

	// The window handle
	private long window;
	public int width;
	public int height;
	private boolean resized = false;
	
	public GLWindow(String title, int width, int height) {
		//Redirect de errors naar de java error console
		GLFWErrorCallback.createPrint(System.err).set();
		
		//Start GLFW
		if ( !glfwInit() )
			throw new IllegalStateException("Unable to initialize GLFW");
		glfwDefaultWindowHints(); // optional, the current window hints are already the default
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
		glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // the window will be resizable
		
		//Maak de window
		window = glfwCreateWindow(width, height, title, NULL, NULL);
		if ( window == NULL )
			throw new RuntimeException("Failed to create the GLFW window");
		
		//Add een systeem om keypresses en updates te detecteren
		glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
			if ( key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE )
				glfwSetWindowShouldClose(window, true); // We will detect this in the rendering loop
		});
		
		//Add een system om resize te detecteren
		glfwSetFramebufferSizeCallback(window, (window, newwidth, newheight) -> {
		    GLWindow.this.width = newwidth;
		    GLWindow.this.height = newheight;
		    GLWindow.this.setResized(true);
		});
		
		//Krijg de window size van de gpu en zet GLFW op ermee
		try ( MemoryStack stack = stackPush() ) {
			IntBuffer pWidth = stack.mallocInt(1);
			IntBuffer pHeight = stack.mallocInt(1); 
			glfwGetWindowSize(window, pWidth, pHeight); //Get the window size
			GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
			glfwSetWindowPos(
				window,
				(vidmode.width() - pWidth.get(0)) / 2,
				(vidmode.height() - pHeight.get(0)) / 2
			);
		}
		
		//Iets wat openGL graag wilde
		glfwMakeContextCurrent(window);
		GL.createCapabilities();
		
		//VSYNC aan
		glfwSwapInterval(1);
		
		//Show de window
		glfwShowWindow(window);
	}
	
	public void setResized(boolean b) {
		this.resized = b;
	}


	public void destroy() {
		glfwFreeCallbacks(window);
		glfwDestroyWindow(window);
		glfwTerminate();
		glfwSetErrorCallback(null).free();
	}


	public void clear() {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	}
	
	public void update() {
		glfwSwapBuffers(window);
		glfwPollEvents();
	}


	public long getWindow() {
		return window;
	}


	public boolean isResized() {
		return this.resized;
	}
	
}
