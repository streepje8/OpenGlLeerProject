package com.streep.openglleer.core;

import java.util.ArrayList;

import com.streep.openglleer.engine.Renderer;

public class GLRenderer {

	public ArrayList<Renderer> renderers = new ArrayList<Renderer>();
	
	public GLRenderer() {}
	
	public void register(Renderer rend) {
		this.renderers.add(rend);
	}
	
	public void init() throws Exception {
		for(Renderer rend : renderers) {
			rend.load();
		}
	}
	
	public void render() {
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
