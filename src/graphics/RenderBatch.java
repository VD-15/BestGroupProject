package graphics;

import java.util.ArrayList;
import java.util.HashMap;

import core.Camera;
import utils.LogSeverity;
import utils.Logger;
import utils.Region;
import utils.Vector2;

/**
 * Stores all of the RenderInstances to be drawn on a single frame
 * @author Vee
 */
public class RenderBatch
{
	/**
	 * Instances to draw
	 */
	private ArrayList<RenderInstance> instances;
	
	/**
	 * Cameras to draw with
	 */
	private HashMap<Integer, Camera> cameras;
	
	/**
	 * Creates a new renderbatch
	 */
	public RenderBatch()
	{
		instances = new ArrayList<RenderInstance>();
		cameras = new HashMap<Integer, Camera>();
	}
	
	/**
	 * Draw a new RenderInstance
	 * @param i the RenderInstance to draw
	 */
	public void draw(RenderInstance i)
	{
		//FIXME: move this to RenderInstance.build()?
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
		
		if (i.layer == null)
		{
			Logger.log(this, LogSeverity.VERBOSE, "RenderInstance layer was null. Will use layer -1.");
			i.layer = new Integer(-1);
		}
		
		instances.add(i);
	}
	
	/**
	 * Registers a camera to this RenderBatch
	 * @param layer the layer this camera is observing
	 * @param c the camera observing this layer
	 */
	public void registerCamera(int layer, Camera c)
	{
		cameras.put(new Integer(layer), c);
	}
	
	/**
	 * Gets all the RenderInstances in this RenderBatch
	 * @return
	 */
	public ArrayList<RenderInstance> getInstances()
	{
		return instances;
	}
	
	/**
	 * Gets all the cameras in this RenderBatch
	 * @return
	 */
	public HashMap<Integer, Camera> getCameras()
	{
		return cameras;
	}
}
