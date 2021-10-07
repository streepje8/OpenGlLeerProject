package com.streep.openglleer.engine;

import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

import org.joml.Vector3f;

import com.streep.openglleer.core.GLRenderer;
import com.streep.openglleer.core.Material;
import com.streep.openglleer.core.Mesh;
import com.streep.openglleer.util.FileUtils;

public class MeshRenderer extends Renderer {

	private static final long serialVersionUID = -6813518508896938622L;
	public Material mat = new Material();
	//Voor een plane, tijdelijk voor testen hardcoded maar wordt vervangen voor een obj file.
	float[] vertices = new float[] {
		    // VO
		    -0.5f,  0.5f,  0.5f,
		    // V1
		    -0.5f, -0.5f,  0.5f,
		    // V2
		    0.5f, -0.5f,  0.5f,
		    // V3
		     0.5f,  0.5f,  0.5f,
		    // V4
		    -0.5f,  0.5f, -0.5f,
		    // V5
		     0.5f,  0.5f, -0.5f,
		    // V6
		    -0.5f, -0.5f, -0.5f,
		    // V7
		     0.5f, -0.5f, -0.5f,
		};
	int[] indices = new int[] {
		    // Front face
		    0, 1, 3, 3, 1, 2,
		    // Top Face
		    4, 0, 3, 5, 4, 3,
		    // Right face
		    3, 2, 7, 5, 3, 7,
		    // Left face
		    6, 1, 0, 6, 0, 4,
		    // Bottom face
		    2, 1, 6, 2, 6, 7,
		    // Back face
		    7, 6, 4, 7, 4, 5,
		};
	float[] colors = new float[]{
		    0.5f, 0.0f, 0.0f,
		    0.0f, 0.5f, 0.0f,
		    0.0f, 0.0f, 0.5f,
		    0.0f, 0.5f, 0.5f,
		    0.5f, 0.0f, 0.0f,
		    0.0f, 0.5f, 0.0f,
		    0.0f, 0.0f, 0.5f,
		    0.0f, 0.5f, 0.5f,
		};
	public Mesh mesh = new Mesh(vertices, indices, colors); //

	float i = 1;
	
	@Override
	public void render(GLRenderer rend) {
		mat.getShader().bind();
		mat.getShader().setUniform("projectionMatrix", rend.target.getProjectionMatrix());
		mat.getShader().setUniform("worldMatrix", rend.target.getWorldMatrix(this.gameObject.position, this.gameObject.rotation, this.gameObject.scale));
		

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
		mat.getShader().createUniform("projectionMatrix");
		mat.getShader().createUniform("worldMatrix");
	}

	@Override
	public void cleanUp() {
		mat.cleanUp();
		mesh.cleanUp();
	}

	@Override
	protected MeshRenderer copy() {
		MeshRenderer rend = new MeshRenderer();
		rend.vertices = this.vertices;
		rend.indices = this.indices;
		rend.mesh = new Mesh(this.vertices,this.indices);
		rend.mat = new Material();
		return rend;
	}
	
}
