package com.streep.openglleer.engine;

import java.io.Serializable;
import java.util.ArrayList;

import org.joml.Vector3f;

public class GameObject implements Serializable {

	private static final long serialVersionUID = 1L;

	public Vector3f position = new Vector3f(0,0,0);
	public Vector3f rotation = new Vector3f(0,0,0);
	public Vector3f scale = new Vector3f(1,1,1);
	public String name = "GameObject(no name set)";
	
	private ArrayList<Component> components = new ArrayList<Component>();
	
	public GameObject() {
		this.position = new Vector3f(0,0,0);
		this.rotation = new Vector3f(0,0,0);
		this.scale = new Vector3f(1,1,1);
	}
	
	public GameObject(float x, float y, float z) {
		this.position = new Vector3f(x,y,z);
		this.rotation = new Vector3f(0,0,0);
		this.scale = new Vector3f(1,1,1);
	}
	
	public GameObject(Vector3f position) {
		this.position = position;
		this.rotation = new Vector3f(0,0,0);
		this.scale = new Vector3f(1,1,1);
	}
	
	
	/*
	public Vector3f forward() {
		Vector3f forward = new Vector3f(0,0,-1);
		Matrix4f mat = SMath.createTransformationMatrix(new Vector3f(0,0,0), this.rotation, new Vector3f(1,1,1));
		forward = forward.mulMatrix(mat);
		return forward;
	}
	
	public Vector3f left() {
		Vector3f forward = new Vector3f(-1,0,0);
		Matrix4f mat = SMath.createTransformationMatrix(new Vector3f(0,0,0), this.rotation, new Vector3f(1,1,1));
		forward = forward.mulMatrix(mat);
		return forward;
	}
	
	public Vector3f up() {
		Vector3f forward = new Vector3f(0,1,0);
		Matrix4f mat = SMath.createTransformationMatrix(new Vector3f(0,0,0), this.rotation, new Vector3f(1,1,1));
		forward = forward.mulMatrix(mat);
		return forward;
	}
	*/
	
	public void Destory() {
		for(Component c : components) {
			c.destroy();
		}
		components = new ArrayList<Component>();
	}
	
	
	public boolean addComponent(Component c) {
		if(this.hasComponent(c.getClass())) {
			return false;
		} else {
			this.components.add(c);
			c.setGameObject(this);
			return true;
		}
	}
	
	public <T> void removeComponent(Class<T> componenttype) {
		if(this.hasComponent(componenttype)) {
			this.components.remove(this.getComponent(componenttype));
		}
	}
	
	public <T> T getComponent(Class<T> componenttype) {
		if(this.hasComponent(componenttype)) {
			for(Component c : this.components) {
				if(componenttype.isInstance(c)) {
					return componenttype.cast(c);
				}
			}
		}
		return null;
	}
	
	public <T> boolean hasComponent(Class<T> componenttype) {
		for(Component c : this.components) {
			if(componenttype.isInstance(c)) {
				return true;
			}
		}
		return false;
	}
	
	public ArrayList<Component> getComponents() {
		return this.components;
	}

	public boolean removeComponent(Component c) {
		if(this.components.contains(c)) {
			c.destroy();
			this.components.remove(c);
			return true;
		}
		return false;
	}
	
	public Vector3f getRotation() {
		return rotation;
	}

	public void setRotation(Vector3f rotation) {
		this.rotation = rotation;
	}

	public Vector3f getScale() {
		return scale;
	}

	public void setScale(Vector3f scale) {
		this.scale = scale;
	}

	public void rotate(float x, float y, float z) {
		this.rotation.add(new Vector3f(x,y,z));
	}
	
	public void rotate(Vector3f rotation) {
		this.rotation.add(rotation);
	}

	public GameObject copy() {
		GameObject go = new GameObject();
		for(Component c : this.components) {
			go.addComponent(c.copy());
		}
		return go;
	}

}