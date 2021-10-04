package com.streep.openglleer.mangement;

import org.joml.Matrix4f;

import com.streep.openglleer.core.GLWindow;

public class Camera {

    private float FOV = (float) Math.toRadians(60.0f);

    private float Z_NEAR = 0.01f;

    private float Z_FAR = 1000.f;
    
    private float aspectRatio = 1;

    private Matrix4f projectionMatrix;
	
	public Camera(float FOV, float Z_NEAR, float Z_FAR) {
		this.FOV = (float) Math.toRadians(FOV);
		this.Z_NEAR = Z_NEAR;
		this.Z_FAR = Z_FAR;
	}
	
	public void createProjectionMatrix(GLWindow glw) {
		aspectRatio = (float) glw.width / glw.height;
	    projectionMatrix = new Matrix4f().setPerspective(FOV, aspectRatio,
	        Z_NEAR, Z_FAR);
	}
	
	public Matrix4f getProjectionMatrix() {
		return projectionMatrix;
	}
	
}
