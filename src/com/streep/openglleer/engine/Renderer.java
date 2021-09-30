package com.streep.openglleer.engine;

public abstract class Renderer extends Component {

	public abstract void render();

	public abstract void load() throws Exception;

	public abstract void cleanUp();

}
