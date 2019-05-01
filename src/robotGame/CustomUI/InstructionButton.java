package robotGame.CustomUI;

import UI.Button;
import core.Game;
import core.IUpdatable;
import graphics.RenderBatch;
import graphics.RenderInstance;
import robotGame.Instruction;
import utils.Region;
import utils.Vector2;

public class InstructionButton extends Button implements IUpdatable
{
	private Instruction instruction;
	private Vector2 offset;
	
	public InstructionButton(Vector2 offset, Instruction i)
	{
		super();
		this.instruction = i;
		this.offset = offset;
		this.tag = "instructionButton" + i.toString();
		this.setWidth(64);
	}
	
	@Override
	protected void onClick()
	{
		////////////////////////////////////////////
		////THIS IS WHERE YOU WANT TO PUT STUFF!////
		////////////////////////////////////////////
	}

	@Override
	public void update(double time)
	{
		this.position = new Vector2(offset.x, Game.getWindow().getViewport().y - offset.y);
	}
	
	@Override
	public void draw(RenderBatch b)
	{
		super.draw(b);
		
		b.draw(new RenderInstance()
			.withTexture("icon" + instruction.toString())
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
