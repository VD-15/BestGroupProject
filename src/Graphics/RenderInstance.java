package Graphics;

import Utils.Region;

public class RenderInstance
{
	public Texture texture;
	public Region destination;
	public Region source;
	public Color color;
	public float depth;
	
	public RenderInstance()
	{
		this.texture = null;
		this.destination = new Region();
		this.source = new Region();
		this.color = Color.WHITE();
		this.depth = 0f;
	}
}
