package com.streep.openglleer.engine;

import java.io.Serializable;

public abstract class Component implements Serializable {

	public abstract void start();
	public abstract void update();
	
	private static final long serialVersionUID = 1L;
	protected GameObject gameObject = new GameObject();

	public void setGameObject(GameObject gameObject) {
		this.gameObject = gameObject;
	}

	public void destroy() {}
	protected abstract Component copy();

}
