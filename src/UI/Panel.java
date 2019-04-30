package UI;

import graphics.Color;
import graphics.IDrawable;
import graphics.RenderBatch;
import graphics.RenderInstance;
import utils.Region;
import utils.Vector2;

public class Panel extends UIObject implements IDrawable
{
	protected Vector2 size;
	protected Color color;
	
	public Panel()
	{
		super();
		this.size = new Vector2(256, 256);
		this.color = Color.WHITE();
	}
	
	@Override
	public void draw(RenderBatch b)
	{
		b.draw(new RenderInstance()
			.withTexture("uiBlank")
			.withDepth(15f)
			.withLayer(2)
			.withColor(this.color)
			.withDestinationRegion(new Region(this.position, this.size, false))
			.build()
			);
	}
	
}
