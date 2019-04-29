package core;

import graphics.IDrawable;
import graphics.RenderBatch;
import utils.Region;
import utils.Vector2;

/**
 * Facilitates the separation of drawable objects
 * into layers, allowing for multiple viewpoints
 * at once (e.g. for world and UI)
 * @author Vee
 */
public class Camera extends GameObject implements IDrawable
{
	/**
	 * The size of the viewport, in pixels
	 */
	protected Vector2 viewportSize;
	
	/**
	 * The layer this camera operates in
	 */
	protected int layer;
	
	/**
	 * Constructs a camera that observes layer -1
	 */
	public Camera()
	{
		super();
		this.tag = "camera";
		this.layer = -1;
		this.viewportSize = new Vector2();
	}
	
	/**
	 * Constructs a camera that observes the specified layer
	 * @param layer The layer to observe
	 */
	public Camera(int layer)
	{
		super();
		this.tag = "camera";
		this.layer = layer;
		this.viewportSize = new Vector2();
	}
	
	/**
	 * Gets the region of pixels this camera will observe
	 * @return The bounds of the camera's viewport
	 */
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
		//Let the RenderBatch know there is a camera observing this layer
		b.registerCamera(this.layer, this);
	}

	@Override
	public void destroy()
	{
		
	}
	
}
