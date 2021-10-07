package com.streep.openglleer.engine;

import com.streep.openglleer.core.GLRenderer;

public abstract class Renderer extends Component {
	
	public abstract void render(GLRenderer rend);

	public abstract void load() throws Exception;

	public abstract void cleanUp();
	
	public void start() {}
	public void update() {}

}
