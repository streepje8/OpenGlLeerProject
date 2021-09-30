package com.streep.openglleer;


import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;
import static org.lwjgl.opengl.GL11.glClearColor;

import org.lwjgl.opengl.GL;

import com.streep.openglleer.core.GLRenderer;
import com.streep.openglleer.core.GLWindow;
import com.streep.openglleer.engine.MeshRenderer;

//Wessel Roelofse 1C Dev
public class Main {

	//De window class
	public static GLWindow window;
	public static GLRenderer renderer = new GLRenderer();

	public void run() {
		try {
			init();
			renderer.init();
			long lastTime = System.nanoTime();
		    final double ns = 1000000000.0 / 60.0;
		    double delta = 0;
		    while(!glfwWindowShouldClose(window.getWindow())){
		        long now = System.nanoTime();
		        delta += (now - lastTime) / ns;
		        lastTime = now;
		        while(delta >= 1){
		            loop();
		            delta--;
		        }
		    }
	    } catch (Exception excp) {
	        excp.printStackTrace();
	    } finally {
	        cleanup();
	    }
	}
	
	private void init() {
		//Maak een window
		window = new GLWindow("YUSSSSSSSSSSS", 800, 400);
		MeshRenderer mr = new MeshRenderer();
		renderer.register(mr);
	}

	private void loop() {
		//update de window
		GL.createCapabilities();
		glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		window.clear();
		renderer.render();
		window.update();
	}

	//Remove de stuff van ram enzo
	public void cleanup() {
		renderer.cleanUp();
		window.destroy();
	}
	
	//iets om het programma te starten
	public static void main(String[] args) {
		new Main().run();
	}
	
}
