package com.streep.openglleer.engine;

import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glDeleteBuffers;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glDeleteVertexArrays;

import com.streep.openglleer.core.Material;
import com.streep.openglleer.core.Mesh;

public class MeshRenderer extends Renderer {

	public Material mat = new Material();
	float[] vertices = new float[]{
		     0.0f,  0.5f, 0.0f,
		    -0.5f, -0.5f, 0.0f,
		     0.5f, -0.5f, 0.0f
		};
	public Mesh mesh = new Mesh(vertices);

	@Override
	public void render() {
		mat.getShader().bind();

	    // Bind to the VAO
	    glBindVertexArray(mesh.vaoID);

	    // Draw the vertices
	    glDrawArrays(GL_TRIANGLES, 0, 3);

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

		//Disable the VAO
	    glDisableVertexAttribArray(0);

	    // Delete the VBO
	    glBindBuffer(GL_ARRAY_BUFFER, 0);
	    glDeleteBuffers(mesh.vboID);

	    // Delete the VAO
	    glBindVertexArray(0);
	    glDeleteVertexArrays(mesh.vaoID);
	}
	
}
