package com.streep.openglleer.mangement;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import com.streep.openglleer.core.GLWindow;
import com.streep.openglleer.engine.GameObject;

public class Camera {

    private float FOV = (float) Math.toRadians(60.0f);

    private float Z_NEAR = 0.01f;

    private float Z_FAR = 1000.f;
    
    private float aspectRatio = 1;

    private Matrix4f projectionMatrix = new Matrix4f();
    private final Matrix4f worldMatrix = new Matrix4f();
    private Matrix4f viewMatrix = new Matrix4f();
    
    public Vector3f position = new Vector3f(0,0,0);
    public Vector3f rotation = new Vector3f(0,0,0);
	
	public Camera(float FOV, float Z_NEAR, float Z_FAR) {
		this.FOV = (float) Math.toRadians(FOV);
		this.Z_NEAR = Z_NEAR;
		this.Z_FAR = Z_FAR;
	}
	
	public void rotate(float pitch, float roll, float yaw) {
		this.rotation.add(new Vector3f(pitch,roll, yaw).div(360f));
		while(this.rotation.x > 1) {
			this.rotation.x -= 1;
		}
		while(this.rotation.y > 1) {
			this.rotation.y -= 1;
		}
		while(this.rotation.z > 1) {
			this.rotation.z -= 1;
		}
	}
	
	public void translate(float x, float y, float z) {
		this.position.add(new Vector3f(x,y,z));
	}
	
	public void rotate(Vector3f newrotation) {
		this.rotation.add(newrotation.div(360f));
		while(this.rotation.x > 1) {
			this.rotation.x -= 1;
		}
		while(this.rotation.y > 1) {
			this.rotation.y -= 1;
		}
		while(this.rotation.z > 1) {
			this.rotation.z -= 1;
		}
	}
	
	public void translate(Vector3f translation) {
		this.position.add(translation);
	}
	
	public void updateProjectionMatrix(GLWindow glw) {
		aspectRatio = (float) glw.width / glw.height;
		projectionMatrix = new Matrix4f().setPerspective(FOV, aspectRatio,
		        Z_NEAR, Z_FAR);
	}
	
	public void updateProjectionMatrix(GLWindow glw, float FOV, float Z_NEAR, float Z_FAR) {
		this.FOV = (float) Math.toRadians(FOV);
		this.Z_NEAR = Z_NEAR;
		this.Z_FAR = Z_FAR;
		aspectRatio = (float) glw.width / glw.height;
		projectionMatrix = new Matrix4f().setPerspective(FOV, aspectRatio,
		        Z_NEAR, Z_FAR);
	}
	
	public Matrix4f getProjectionMatrix() {
		return projectionMatrix;
	}
	
	public Matrix4f getWorldMatrix(Vector3f offset, Vector3f rotation, Vector3f scale) {
        return worldMatrix.translation(offset).
                rotateX((float)Math.toRadians(rotation.x)).
                rotateY((float)Math.toRadians(rotation.y)).
                rotateZ((float)Math.toRadians(rotation.z)).
                scale(scale);
    }
	
	public String toString() {
		return "Camera[FOV: " + Math.toDegrees(this.FOV) + "*, Z_NEAR: " + this.Z_NEAR + ", Z_FAR: " + this.Z_FAR + ", aspectRatio: " + this.aspectRatio + "]"; 
	}
	
	public void updateViewMatrix() {
	    Vector3f cameraPos = this.position;
	    Vector3f rotation = this.rotation;
	    this.viewMatrix = new Matrix4f();
	    this.viewMatrix.identity();
	    // First do the rotation so camera rotates over its position
	    this.viewMatrix.rotate((float)Math.toRadians(rotation.x), new Vector3f(1, 0, 0))
	        .rotate((float)Math.toRadians(rotation.y), new Vector3f(0, 1, 0));
	    // Then do the translation
	    this.viewMatrix.translate(-cameraPos.x, -cameraPos.y, -cameraPos.z);
	}
	
	public Matrix4f getViewMatrix() {
		return this.viewMatrix;
	}
	
	public Matrix4f getModelViewMatrix(GameObject go, Matrix4f viewMatrix) {
	    Vector3f rotation = go.rotation;
	    Matrix4f modelViewMatrix = new Matrix4f();
	    modelViewMatrix.set(viewMatrix).translate(go.position).
	        rotateX((float)Math.toRadians(-rotation.x)).
	        rotateY((float)Math.toRadians(-rotation.y)).
	        rotateZ((float)Math.toRadians(-rotation.z)).
	            scale(go.scale);
	    return modelViewMatrix;
	}
	
}
