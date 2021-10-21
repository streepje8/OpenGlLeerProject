package com.streep.openglleer.core;

import static org.lwjgl.opengl.GL40.*;

import java.util.ArrayList;

import com.streep.openglleer.engine.Renderer;
import com.streep.openglleer.mangement.Camera;
import com.streep.openglleer.mangement.Enviroment;

public class GLRenderer {

	public ArrayList<Renderer> renderers = new ArrayList<Renderer>();
	public GLWindow window;
	public Camera target;
	
	
	public GLRenderer(GLWindow win) {
		this.window = win;
	}

	public void register(Renderer rend) {
		this.renderers.add(rend);
	}
	
	public void init() throws Exception {
		target.updateProjectionMatrix(window);
		glEnable(GL_DEPTH_TEST);
		for(Renderer rend : renderers) {
			rend.load();
		}
	}
	
	public void render(Enviroment env) {
		if (window.isResized()) {
	        glViewport(0, 0, window.width, window.height);
	        target.updateProjectionMatrix(window);
	        target.updateViewMatrix();
	        window.setResized(false);
	    }
		for(Renderer rend : renderers) {
			rend.render(this);
		}
	}

	public void cleanUp() {
		for(Renderer rend : renderers) {
			rend.cleanUp();
		}
	}
	
}
