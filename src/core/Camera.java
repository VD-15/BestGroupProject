package core;

import graphics.IDrawable;
import graphics.RenderBatch;
import utils.Region;
import utils.Vector2;

public class Camera extends GameObject implements IDrawable
{
	protected Vector2 viewportSize;
	protected int layer;
	
	public Camera()
	{
		super();
		this.tag = "camera";
		this.layer = -1;
		this.viewportSize = new Vector2();
	}
	
	public Camera(int layer)
	{
		super();
		this.tag = "camera";
		this.layer = layer;
		this.viewportSize = new Vector2();
	}
	
	public Region getViewport()
	{
		return new Region(this.position, this.viewportSize, true);
	}
	
	@Override
	public void init()
	{
		
	}

	@Override
	public void draw(RenderBatch b)
	{
		b.registerCamera(this.layer, this);
	}
	
}
