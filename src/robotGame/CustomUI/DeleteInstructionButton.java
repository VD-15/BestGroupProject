package robotGame.CustomUI;

import UI.Button;
import graphics.Color;
import graphics.RenderBatch;
import graphics.RenderInstance;
import utils.Region;
import utils.Vector2;

public class DeleteInstructionButton extends Button
{

	public DeleteInstructionButton(Vector2 location)
	{
		this.position = location;
		this.tag = "deleteInstructionButton";
		this.setWidth(64);
	}
	
	@Override
	protected Color getEnabledColor()
	{
		return new Color(1.5f, 1.0f, 1.0f);
	}
	
	@Override
	protected Color getHoveredColor()
	{
		return new Color(2.0f, 1.5f, 1.5f);
	}
	
	@Override
	protected Color getClickedColor()
	{
		return new Color(1.0f, 0.5f, 0.5f);
	}
	
	@Override
	protected void onClick()
	{
		////////////////////////////////////////////
		////THIS IS WHERE YOU WANT TO PUT STUFF!////
		////////////////////////////////////////////
	}
	
	@Override
	public void draw(RenderBatch b)
	{
		super.draw(b);
		
		b.draw(new RenderInstance()
			.withTexture("iconDelete")
			.withLayer(2)
			.withDepth(17f)
			.withColor(this.getHighlight())
			.withDestinationRegion(
				new Region(
					this.position,
					new Vector2(64f),
					false
					)
				)
			.build()
			);
	}
}
