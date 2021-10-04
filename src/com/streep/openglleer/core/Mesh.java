package com.streep.openglleer.core;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glDeleteBuffers;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glDeleteVertexArrays;
import static org.lwjgl.opengl.GL40.*;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.system.MemoryUtil.*;

public class Mesh {

	public int vaoID = 0; //Vertex attribute list
	public int vboID = 0; //Vertex positions vertex buffer object
	public int inVboID = 0; //Indices vertex buffer object
	public int colorVboID = 0; //Color vertex buffer object
	public int vertexCount = 0;
	public float[] vertexPositions = {};
	
	
	public Mesh(float[] vertexPositions, int[] indices, float[] colors) {
		storeVertexPositions(vertexPositions);
		storeIndexPositions(indices);
		storeColors(colors);
		freeAndUnbind();
	}
	
	public Mesh(float[] vertexPositions, int[] indices) {
		float[] colors = new float[indices.length * 3];
		for(int i = 0; i < indices.length * 3; i++) {
			colors[i] = 1.0f;
		}
		storeVertexPositions(vertexPositions);
		storeIndexPositions(indices);
		storeColors(colors);
		freeAndUnbind();
	}
	
	private void storeColors(float[] colors) {
		// Colour VBO
		colorVboID = glGenBuffers();
		FloatBuffer colorBuffer = memAllocFloat(colors.length);
		colorBuffer.put(colors).flip();
		glBindBuffer(GL_ARRAY_BUFFER, colorVboID);
		glBufferData(GL_ARRAY_BUFFER, colorBuffer, GL_STATIC_DRAW);
		glEnableVertexAttribArray(1);
		glVertexAttribPointer(1, 3, GL_FLOAT, false, 0, 0);
		
		//Free up memory
		if(colorBuffer != null) {
			memFree(colorBuffer);
		}
	}
	
	private void storeIndexPositions(int[] indices) {
		this.vertexCount = indices.length;
		//Store the indices
		inVboID = glGenBuffers();
		IntBuffer indicesBuffer = memAllocInt(indices.length);
		indicesBuffer.put(indices).flip();
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, inVboID);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, GL_STATIC_DRAW);
		
		//Free up memory
		if(indicesBuffer != null) {
			memFree(indicesBuffer);
		}
	}
	
	private void storeVertexPositions(float[] vertexPositions) {
		//Store the vertecies
		this.vertexPositions = vertexPositions;
		FloatBuffer verticesBuffer = memAllocFloat(vertexPositions.length);
		verticesBuffer.put(vertexPositions).flip();
		vaoID = glGenVertexArrays();
		glBindVertexArray(vaoID);
		vboID = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, vboID);
		glBufferData(GL_ARRAY_BUFFER, verticesBuffer, GL_STATIC_DRAW);
		glEnableVertexAttribArray(0);
		glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
		
		//Free up memory
		if(verticesBuffer != null) {
			memFree(verticesBuffer);
		}
	}
	
	private void freeAndUnbind() {

		//Unbind the VBO
		glBindBuffer(GL_ARRAY_BUFFER, 0);

		//Unbind the VAO
		glBindVertexArray(0);
	}
	
    public int getVaoId() {
        return vaoID;
    }

    public int getVertexCount() {
        return vertexCount;
    }

    public void cleanUp() {
        glDisableVertexAttribArray(0);

        // Delete the VBO
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glDeleteBuffers(vboID);
        glDeleteBuffers(inVboID);
        glDeleteBuffers(colorVboID);

        // Delete the VAO
        glBindVertexArray(0);
        glDeleteVertexArrays(vaoID);
    }
	
}
