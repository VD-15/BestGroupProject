package Graphics;

import java.util.ArrayList;

import Game.Core.ContentManager;
import Utils.Region;

public class RenderBatch
{
	private ArrayList<RenderInstance> instances;
	
	public RenderBatch()
	{
		instances = new ArrayList<RenderInstance>();
	}
	
	public void draw(String textureName, Region destination)
	{
		draw(textureName, destination, Color.WHITE(), 0f);
	}
	
	public void draw(String textureName, Region destination, float depth)
	{
		draw(textureName, destination, Color.WHITE(), 0f);
	}
	
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
	
	public void draw(String textureName, Region destination, Region source, float depth)
	{
		draw(textureName, destination, source, Color.WHITE(), depth);
	}
	
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
	
	public ArrayList<RenderInstance> getInstances()
	{
		return instances;
	}
}
