package UI;

import graphics.Color;
import graphics.IDrawable;
import graphics.RenderBatch;
import graphics.StringRenderInstance;

public class Label extends UIObject implements IDrawable
{
	protected String text;
	protected Color color;
	
	public Label()
	{
		super();
		this.text = "";
		this.color = Color.WHITE();
	}
	
	
	@Override
	public void draw(RenderBatch b)
	{
		b.drawString(new StringRenderInstance()
			.withFont("fontSmall")
			.withLocation(this.position)
			.withText(this.text)
			.withColor(this.color)
			.build()
			);
	}
	
}
