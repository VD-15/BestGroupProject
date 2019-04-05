package Graphics;

import java.util.ArrayList;

import Game.Core.ContentManager;
import Utils.Region;

/**
 * A storage class for all rendering operations occuring on a particular frame
 * @author Vee
 */
public class RenderBatch
{
	/**
	 * All the {@link #RenderInstance instances} being drawn this frame
	 */
	private ArrayList<RenderInstance> instances;
	
	/**
	 * Constructs and empty RenderBatch
	 */
	public RenderBatch()
	{
		instances = new ArrayList<RenderInstance>();
	}
	
	/**
	 * Draws a texture in the specified on screen region
	 * @param textureName the name of the texture to draw
	 * @param destination where to draw the texture on screen
	 */
	public void draw(String textureName, Region destination)
	{
		draw(textureName, destination, Color.WHITE(), 0f);
	}
	
	/**
	 * Draws a texture in the specified region on screen at the specified depth
	 * @param textureName the name of the texture to draw
	 * @param destination where to draw the texture on screen
	 * @param depth how deep to draw the texture, higher values are rendered in front
	 */
	public void draw(String textureName, Region destination, float depth)
	{
		draw(textureName, destination, Color.WHITE(), 0f);
	}
	
	/**
	 * Draws a texture in the specified region on screen at the specified depth and multiplies it by the given color
	 * @param textureName the name of the texture to draw
	 * @param destination where to draw the texture on screen
	 * @param color the color to multiply the texture by
	 * @param depth how deep to draw the texture, higher values are rendered in front
	 */
	public void draw(String textureName, Region destination, Color color, float depth)
	{
		RenderInstance r = new RenderInstance();
		Texture t = ContentManager.getImageByName(textureName);
		
		r.texture = t;
		r.destination = destination.clone();
		r.source = new Region(0, 0, t.getWidth(), t.getHeight());
		r.color = color;
		r.depth = depth;
		
		instances.add(r);
	}
	
	/**
	 * Draws a subsection of a texture on screen in the specified region at the specified depth
	 * @param textureName the name of the texture to draw
	 * @param destination where to draw the texture on screen
	 * @param source the region of the source image to render on screen
	 * @param depth how deep to draw the texture, higher values are rendered in front
	 */
	public void draw(String textureName, Region destination, Region source, float depth)
	{
		draw(textureName, destination, source, Color.WHITE(), depth);
	}
	
	/**
	 * Draws a subsection of a texture on screen in the specified region at the specified depth and multiplies it by the given color
	 * @param textureName the name of the texture to draw
	 * @param destination where to draw the texture on screen
	 * @param source the region of the source image to render on screen
	 * @param color the color to multiply the texture by
	 * @param depth how deep to draw the texture, higher values are rendered in front
	 */
	public void draw(String textureName, Region destination, Region source, Color color, float depth)
	{
		RenderInstance r = new RenderInstance();
		Texture t = ContentManager.getImageByName(textureName);
		
		r.texture = t;
		r.destination = destination.clone();
		r.source = source.clone();
		r.color = color;
		r.depth = depth;
		
		instances.add(r);
	}
	
	/**
	 * Gets the {@link #RenderInstance render instances} in this renderbatch
	 * @return an {@link #ArrayList array} containing all the {@link RenderBatch#RenderInstance render instances} drawn this frame
	 */
	public ArrayList<RenderInstance> getInstances()
	{
		return instances;
	}
}
