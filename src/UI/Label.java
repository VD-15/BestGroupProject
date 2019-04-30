package UI;

import graphics.Color;
import graphics.IDrawable;
import graphics.RenderBatch;
import graphics.StringRenderInstance;

public class Label extends UIObject implements IDrawable
{
	protected String text;
	
	public Label()
	{
		super();
		this.text = "";
	}
	
	
	@Override
	public void draw(RenderBatch b)
	{
		b.drawString(new StringRenderInstance()
			.withFont("fontSmall")
			.withLocation(this.position)
			.withText(this.text)
			.withColor(Color.WHITE())
			.build()
			);
	}
	
}
