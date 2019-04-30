package robotGame.CustomUI;

import UI.Button;
import graphics.RenderBatch;
import graphics.RenderInstance;
import robotGame.Instruction;
import utils.Region;
import utils.Vector2;

public class InstructionButton extends Button
{
	private Instruction instruction;
	
	public InstructionButton(Vector2 location, Instruction i)
	{
		this.instruction = i;
		this.position = location;
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
