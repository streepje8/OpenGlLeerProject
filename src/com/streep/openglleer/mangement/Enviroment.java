package com.streep.openglleer.mangement;

import java.util.ArrayList;

import com.streep.openglleer.engine.Component;
import com.streep.openglleer.engine.GameObject;
import com.streep.openglleer.engine.Renderer;

public class Enviroment {

	
	public ArrayList<GameObject> objects = new ArrayList<GameObject>();
	public int id = 0;
	public String name = "";
	public Camera camera = new Camera(60f,0.01f,1000.0f);
	
	public Enviroment(int id, String name, GameObject... gameobjects) {
		this.id = id;
		this.name = name;
		for(int i = 0; i < gameobjects.length; i++) {
			objects.add(gameobjects[i]);
		}
	}
	
	public Enviroment(int id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public Enviroment(int id) {
		this.id = id;
	}

	public void addObject(GameObject gameObject) {
		this.objects.add(gameObject);
	}

	public void init(GameManager manager) {
		for(GameObject go : objects) {
			for(Component c : go.getComponents()) {
				c.start();
				if(c instanceof Renderer) {
					Renderer r = (Renderer) c;
					manager.renderer.register(r);
				}
			}
		}
		manager.setActiveCamera(this.camera);
	}
}
