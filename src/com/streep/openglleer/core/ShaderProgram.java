package com.streep.openglleer.core;
import static org.lwjgl.opengl.GL20.GL_COMPILE_STATUS;
import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_VALIDATE_STATUS;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;
import static org.lwjgl.opengl.GL20.glAttachShader;
import static org.lwjgl.opengl.GL20.glCompileShader;
import static org.lwjgl.opengl.GL20.glCreateProgram;
import static org.lwjgl.opengl.GL20.glCreateShader;
import static org.lwjgl.opengl.GL20.glDeleteProgram;
import static org.lwjgl.opengl.GL20.glDetachShader;
import static org.lwjgl.opengl.GL20.glGetProgramInfoLog;
import static org.lwjgl.opengl.GL20.glGetProgrami;
import static org.lwjgl.opengl.GL20.glGetShaderInfoLog;
import static org.lwjgl.opengl.GL20.glGetShaderi;
import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glLinkProgram;
import static org.lwjgl.opengl.GL20.glShaderSource;
import static org.lwjgl.opengl.GL20.glUniformMatrix4fv;
import static org.lwjgl.opengl.GL20.glUseProgram;
import static org.lwjgl.opengl.GL20.glValidateProgram;

import java.util.ArrayList;
import java.util.HashMap;

import org.joml.Matrix4f;
import org.lwjgl.system.MemoryStack;

public class ShaderProgram {
	
	//Waar word het programma opgeslagen?
	private final int programID;
	
	//Shader types
	public static enum ShaderType {
		Vertex,
		Fragment
	}
	
	//Waar word de shader opgeslagen?
	public ArrayList<ShaderPart> shaders = new ArrayList<ShaderPart>();
	
	//Uniform locaties op de gpu
	private final HashMap<String, Integer> uniforms = new HashMap<>();
	
	//Maak een shader programma
	public ShaderProgram() {
		programID = glCreateProgram();
		if(programID == 0) {
			System.err.println("Could not create shader program!");
		}
	}
	
	//Maak een uniform
	public void createUniform(String uniformName) {
	    int uniformLocation = glGetUniformLocation(programID,
	        uniformName);
	    if (uniformLocation < 0) {
	        System.err.println("Could not find uniform:" +
	            uniformName);
	        System.exit(0);
	    }
	    uniforms.put(uniformName, uniformLocation);
	}
	
	//Stuur de uniform waarde naar de gpu [Matrix4f]
	public void setUniform(String uniformName, Matrix4f value) {
	    try (MemoryStack stack = MemoryStack.stackPush()) {
	        glUniformMatrix4fv(uniforms.get(uniformName), false,
	                           value.get(stack.mallocFloat(16)));
	    } catch(Exception e) {
	    	System.err.println("Could not send uniform to gpu!");
	    	e.printStackTrace();
	    }
	}
	
	//Compileer de shader code
	public void setCode(String code, ShaderType type) throws Exception {
		//Maak een shader part
		ShaderPart part = new ShaderPart(0, type);
		switch(type) {
			case Fragment:
				part = new ShaderPart(glCreateShader(GL_FRAGMENT_SHADER), type);
				break;
			case Vertex:
				part = new ShaderPart(glCreateShader(GL_VERTEX_SHADER), type);
				break;
			default:
				break;
		}
		
		//Compileer de shader
		glShaderSource(part.shaderID, code);
		glCompileShader(part.shaderID);
		
		if (glGetShaderi(part.shaderID, GL_COMPILE_STATUS) == 0) {
            throw new Exception("Error compiling Shader code: " + glGetShaderInfoLog(part.shaderID, 1024));
        }

		//Paas de shader naar het programa
		glAttachShader(programID, part.shaderID);
		if(part.shaderID != 0) {
			this.shaders.add(part);
		}
	}
	
	public void link() {
		//link de shader aan de GPU
		glLinkProgram(programID);
		
		
		//IDK waarom dit maar het stond in de tutorial die ik volg
		for(ShaderPart part : this.shaders) {
	        if (part.shaderID != 0) {
	            glDetachShader(programID, part.shaderID);
	        }
		}

		//Kijk of de GPU het eens is met deze shader
        glValidateProgram(programID);
        if (glGetProgrami(programID, GL_VALIDATE_STATUS) == 0) {
            System.err.println("Warning validating Shader code: " + glGetProgramInfoLog(programID, 1024));
        }
	}
	
	
	//Koppel deze shader aan de GPU
	public void bind() {
		glUseProgram(programID);
	}
	
	//Ontkoppel deze shader van de GPU
	public void unbind() {
		glUseProgram(0);
	}
	
	public void cleanUp() {
		unbind();
		if(programID != 0) {
			glDeleteProgram(programID);
		}
	}
	
}
