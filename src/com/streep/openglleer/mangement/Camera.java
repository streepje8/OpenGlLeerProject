package com.streep.openglleer.mangement;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import com.streep.openglleer.core.GLWindow;

public class Camera {

    private float FOV = (float) Math.toRadians(60.0f);

    private float Z_NEAR = 0.01f;

    private float Z_FAR = 1000.f;
    
    private float aspectRatio = 1;

    private Matrix4f projectionMatrix = new Matrix4f();
    private final Matrix4f worldMatrix = new Matrix4f();
	
	public Camera(float FOV, float Z_NEAR, float Z_FAR) {
		this.FOV = (float) Math.toRadians(FOV);
		this.Z_NEAR = Z_NEAR;
		this.Z_FAR = Z_FAR;
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
	
}
