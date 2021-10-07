package com.streep.openglleer.mangement;

import java.util.HashMap;

import com.streep.openglleer.core.GLRenderer;

public class GameManager {

	public int activeEnviroment = 0;
	public HashMap<Integer, Enviroment> enviroments = new HashMap<Integer, Enviroment>();
	public GLRenderer renderer;
	
	
	public void setEnviroment(Enviroment enviroment) {
		activeEnviroment = enviroment.id;
		if(!enviroments.containsKey(enviroment.id)) {
			enviroments.put(enviroment.id,enviroment);
		} else {
			enviroments.remove(enviroment.id);
			enviroments.put(enviroment.id,enviroment);
		}
	}
	
	public Enviroment getEnviroment() {
		return getEnviroment(activeEnviroment);
	}
	
	//Geeft nul als het niet bestaat
	public Enviroment getEnviroment(int id) {
		if(enviroments.containsKey(id)) {
			return enviroments.get(id);
		} else {
			return null;
		}
	}

	public void setRenderer(GLRenderer renderer) {
		this.renderer = renderer;
	}

	public void init() {
		for(Enviroment e : enviroments.values()) {
			e.init(this);
		}
	}

	public void setActiveCamera(Camera camera) {
		this.renderer.target = camera;
	}
	
}
