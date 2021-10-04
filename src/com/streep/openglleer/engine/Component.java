package com.streep.openglleer.engine;

import java.io.Serializable;

public abstract class Component implements Serializable {

	public abstract void start();
	public abstract void update();
	
	private static final long serialVersionUID = 1L;
	@SuppressWarnings("unused")
	private GameObject gameObject;

	public void setGameObject(GameObject gameObject) {
		this.gameObject = gameObject;
	}

	public void destroy() {}

}
