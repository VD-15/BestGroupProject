package graphics;

import core.ContentManager;
import utils.LogSeverity;
import utils.Logger;
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
	public Integer layer;
	
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
		this.rotationOrigin = origin.clone();
		return this;
	}
	
	public RenderInstance withLayer(Integer layer)
	{
		this.layer = new Integer(layer);
		return this;
	}
	
	public RenderInstance build()
	{
		if (texture == null)
		{
			Logger.log(this, LogSeverity.ERROR, "RenderInstance texture was null. You actually NEED to supply this.");
		}
		
		if (destination == null)
		{
			Logger.log(this, LogSeverity.WARNING, "RenderInstance destination region was null. Will draw at origin point.");
			destination = new Region(0, 0, texture.getWidth(), texture.getHeight());
		}
		
		if (source == null)
		{
			source = new Region(0, 0, texture.getWidth(), texture.getHeight());
		}
		
		if (color == null)
		{
			color = Color.WHITE();
		}
		
		if (depth == null)
		{
			depth = new Float(0f);
		}
		
		if (rotation == null)
		{
			rotation = new Float(0f);
		}

		if (rotationOrigin == null)
		{
			rotationOrigin = new Vector2();
		}
		
		if (layer == null)
		{
			layer = new Integer(-1);
		}
		
		return this;
	}
}
