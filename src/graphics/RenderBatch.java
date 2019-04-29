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
		instances.add(i);
	}
	
	public void drawString(StringRenderInstance i)
	{
		instances.addAll(i.toRenderInstanceArray());
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
