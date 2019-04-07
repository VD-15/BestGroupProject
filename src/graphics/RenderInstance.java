package graphics;

import robotGame.Core.ContentManager;
import utils.Region;
import utils.Vector2;

/**
 * Stores a single instance of a rendered sprite
 * @author Vee
 */
public class RenderInstance
{
	public Texture texture;
	public Region destination;
	public Region source;
	public Color color;
	public Float depth;
	public Float rotation;
	public Vector2 rotationOrigin;
	
	public RenderInstance()
	{
		this.texture = null;
		this.destination = null;
		this.source = null;
		this.color = null;
		this.depth = null;
		this.rotation = null;
	}
	
	public RenderInstance withTexture(String textureName)
	{
		this.texture = ContentManager.getImageByName(textureName);
		return this;
	}

	public RenderInstance withDestinationRegion(Region destination)
	{
		this.destination = destination.clone();
		return this;
	}
	
	public RenderInstance withSourceRegion(Region source)
	{
		this.source = source.clone();
		return this;
	}
	
	public RenderInstance withColor(Color color)
	{
		this.color = color.clone();
		return this;
	}
	
	public RenderInstance withDepth(Float depth)
	{
		this.depth = new Float(depth);
		return this;
	}
	
	public RenderInstance withRotation(Float rotation)
	{
		this.rotation = new Float(rotation);
		return this;
	}
	
	public RenderInstance withRotationOrigin(Vector2 origin)
	{
		this.rotationOrigin = origin;
		return this;
	}
}
