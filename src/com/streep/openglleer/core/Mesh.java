package com.streep.openglleer.core;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

import java.nio.FloatBuffer;

import org.lwjgl.system.MemoryUtil;

public class Mesh {

	public int vaoID = 0;
	public int vboID = 0;
	
	public Mesh(float[] vertices) {
		FloatBuffer verticesBuffer = MemoryUtil.memAllocFloat(vertices.length);
		verticesBuffer.put(vertices).flip();
		vaoID = glGenVertexArrays();
		glBindVertexArray(vaoID);
		vboID = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, vboID);
		glBufferData(GL_ARRAY_BUFFER, verticesBuffer, GL_STATIC_DRAW);
		glEnableVertexAttribArray(0);
		glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
		
		// Unbind the VBO
		glBindBuffer(GL_ARRAY_BUFFER, 0);

		// Unbind the VAO
		glBindVertexArray(0);

		if (verticesBuffer != null) {
		    MemoryUtil.memFree(verticesBuffer);
		}
	}
	
}
