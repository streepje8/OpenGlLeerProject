package com.streep.openglleer.core;

import static org.lwjgl.opengl.GL11.glViewport;

import java.util.ArrayList;

import com.streep.openglleer.engine.Renderer;
import com.streep.openglleer.mangement.Enviroment;

public class GLRenderer {

	public ArrayList<Renderer> renderers = new ArrayList<Renderer>();
	public GLWindow window;
	
	
	public GLRenderer(GLWindow win) {
		this.window = win;
	}

	public void register(Renderer rend) {
		this.renderers.add(rend);
	}
	
	public void init() throws Exception {
		for(Renderer rend : renderers) {
			rend.load();
		}
	}
	
	public void render(Enviroment env) {
		if (window.isResized()) {
	        glViewport(0, 0, window.width, window.height);
	        window.setResized(false);
	    }
		env.preRender(window);
		for(Renderer rend : renderers) {
			rend.render();
		}
	}

	public void cleanUp() {
		for(Renderer rend : renderers) {
			rend.cleanUp();
		}
	}
	
}
