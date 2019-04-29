package UI;

import core.Game;
import core.IUpdatable;
import graphics.Color;
import graphics.IDrawable;
import graphics.RenderBatch;
import graphics.RenderInstance;
import utils.Region;
import utils.Vector2;

public class Panel extends UIObject implements IDrawable, IUpdatable
{
	private Vector2 size;
	
	public Panel()
	{
		super();
		size = new Vector2(256, 700);
	}
	
	@Override
	public void update(double time)
	{
		this.size.y = Game.getWindow().getViewport().y;
	}
	
	@Override
	public void draw(RenderBatch b)
	{
		b.draw(new RenderInstance()
			.withTexture("uiBlank")
			.withDepth(15f)
			.withLayer(2)
			.withColor(new Color(0.1f, 0.1f, 0.1f))
			.withDestinationRegion(new Region(this.position, this.size, false))
			.build()
			);
	}
	
}
