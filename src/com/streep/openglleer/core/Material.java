package com.streep.openglleer.core;

import com.streep.openglleer.core.ShaderProgram.ShaderType;
import com.streep.openglleer.util.FileUtils;

public class Material {

	public ShaderProgram shader;
	public String vertex = "./shaders/defaultVertex.vs";
	public String fragment = "./shaders/defaultFragment.fs";
	
	
	
	public void load() throws Exception {
		shader = new ShaderProgram();
		shader.setCode(FileUtils.loadFile(vertex), ShaderType.Vertex);
		shader.setCode(FileUtils.loadFile(fragment), ShaderType.Fragment);
		//Link de shader aan de GPU
		shader.link();
	}

	public void cleanUp() {
		if (shader != null) {
	        shader.cleanUp();
	    }
	}


	public ShaderProgram getShader() {
		return shader;
	}
	
	
}
