package com.streep.openglleer.engine;

import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

import com.streep.openglleer.core.Material;
import com.streep.openglleer.core.Mesh;

public class MeshRenderer extends Renderer {

	private static final long serialVersionUID = -6813518508896938622L;
	public Material mat = new Material();
	//Voor een plane, tijdelijk voor testen hardcoded maar wordt vervangen voor een obj file.
	float[] vertices = new float[]{
	        -0.5f,  0.5f, 0.0f,
	        -0.5f, -0.5f, 0.0f,
	         0.5f, -0.5f, 0.0f,
	         0.5f,  0.5f, 0.0f,
	    };
	int[] indices = new int[]{
	        0, 1, 3, 3, 1, 2,
	    };
	float[] colors = new float[]{
		    1f, 0.0f, 1f,
		    1f, 0.0f, 1f,
		    0.5f, 0.0f, 0.5f,
		    0.5f, 0.0f, 0.5f,
		};
	public Mesh mesh = new Mesh(vertices, indices, colors);

	@Override
	public void render() {
		mat.getShader().bind();

	    // Bind to the VAO
	    glBindVertexArray(mesh.vaoID);

	    // Draw the vertices
	    glDrawElements(GL_TRIANGLES, mesh.getVertexCount(), GL_UNSIGNED_INT, 0);

	    // Restore state
	    glBindVertexArray(0);
	    
	    mat.getShader().unbind();
	}

	@Override
	public void load() throws Exception {
		mat.load();
	}

	@Override
	public void cleanUp() {
		mat.cleanUp();
		mesh.cleanUp();
	}
	
}
