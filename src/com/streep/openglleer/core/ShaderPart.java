package com.streep.openglleer.core;

import com.streep.openglleer.core.ShaderProgram.ShaderType;

public class ShaderPart {

	public int shaderID = 0;
	public ShaderType type;
	
	public ShaderPart(int shaderID, ShaderType type) {
		this.shaderID = shaderID;
		this.type = type;
	}
	
}
