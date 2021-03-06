package com.streep.openglleer;


import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;
import static org.lwjgl.opengl.GL11.glClearColor;

import java.util.ArrayList;

import org.joml.Vector3f;
import org.lwjgl.opengl.GL;

import com.streep.openglleer.core.GLRenderer;
import com.streep.openglleer.core.GLWindow;
import com.streep.openglleer.engine.GameObject;
import com.streep.openglleer.engine.MeshRenderer;
import com.streep.openglleer.mangement.Enviroment;
import com.streep.openglleer.mangement.GameManager;

//Wessel Roelofse 1C Dev
public class Main {

	//De window class
	public static GLWindow window;
	public static GLRenderer renderer;
	public static GameManager gameManager;

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
	
	GameObject meshObject = new GameObject();
	private void init() {
		//Maak een window
		window = new GLWindow("Geweldige Engine", 800, 400);
		renderer = new GLRenderer(window);
		
		gameManager = new GameManager();
		gameManager.setEnviroment(new Enviroment(0, "Main enviroment"));
		gameManager.setRenderer(renderer);
		meshObject.addComponent(new MeshRenderer());
		meshObject.position = new Vector3f(0,0,-1.6f);
		meshObject.scale = new Vector3f(0.5f);
		gameManager.getEnviroment().addObject(meshObject);
		gameManager.init();
	}

	private void loop() {
		//update de window
		GL.createCapabilities();
		glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		window.clear();
		//meshObject.rotate(1f,1f,1f);
		renderer.render(gameManager.getEnviroment());
		renderer.target.rotate(new Vector3f(3f,0f,3f));
		renderer.target.updateViewMatrix();
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
