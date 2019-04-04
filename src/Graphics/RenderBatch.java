package Graphics;

import java.util.ArrayList;

import Utils.LogSeverity;
import Utils.Logger;
import Utils.Region;
import Utils.Vector2;

public class RenderBatch
{
	private ArrayList<RenderInstance> instances;
	
	public RenderBatch()
	{
		instances = new ArrayList<RenderInstance>();
	}
	
	public void draw(RenderInstance i)
	{
		if (i.texture == null)
		{
			Logger.log(this, LogSeverity.ERROR, "RenderInstance texture was null. You actually NEED to supply this.");
			return;
		}
		
		if (i.destination == null)
		{
			Logger.log(this, LogSeverity.WARNING, "RenderInstance destination region was null. Will draw at origin point.");
			i.destination = new Region(0, 0, i.texture.getWidth(), i.texture.getHeight());
		}
		
		if (i.source == null)
		{
			Logger.log(this, LogSeverity.VERBOSE, "RenderInstance source region was null. Will draw the whole image.");
			i.source = new Region(0, 0, i.texture.getWidth(), i.texture.getHeight());
		}
		
		if (i.color == null)
		{
			Logger.log(this, LogSeverity.VERBOSE, "RenderInstance color was null. Will draw as white.");
			i.color = Color.WHITE();
		}
		
		if (i.depth == null)
		{
			Logger.log(this, LogSeverity.VERBOSE, "RenderInstance depth was null. Will draw at depth 0.");
			i.depth = new Float(0f);
		}
		
		if (i.rotation == null)
		{
			Logger.log(this, LogSeverity.VERBOSE, "RenderInstance rotation was null. Will draw unrotated.");
			i.rotation = new Float(0f);
		}

		if (i.rotationOrigin == null)
		{
			Logger.log(this, LogSeverity.VERBOSE, "RenderInstance rotation origin was null. Will rotate around origin.");
			i.rotationOrigin = new Vector2();
		}
		
		instances.add(i);
	}
	
	public ArrayList<RenderInstance> getInstances()
	{
		return instances;
	}
}
