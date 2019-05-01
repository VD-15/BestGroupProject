package robotGame.CustomUI;

import java.util.LinkedList;

import UI.Panel;
import UI.UIObject;
import graphics.Color;
import graphics.RenderBatch;
import graphics.RenderInstance;
import robotGame.Instruction;
import utils.Region;
import utils.Vector2;

public class InstructionViewer extends Panel
{
	private LinkedList<Instruction> instructions;
	private int playerNumber;
	private Color highlight;
	
	public InstructionViewer(int playerNumber, Vector2 location)
	{
		super();
		this.playerNumber = playerNumber;
		this.position = location;
		this.size = new Vector2(212, 44);
		this.color = new Color(0.05f, 0.05f, 0.05f);
		
		this.tag = "InstructionViewer" + String.valueOf(playerNumber);
		
		this.instructions = new LinkedList<Instruction>();
		
		//Assign highlight color based on player number
		switch (this.playerNumber)
		{
			case 1:
				this.highlight = new Color(0.925f, 0.063f, 0.063f); // red
				break;
			case 2:
				this.highlight = new Color(0.055f, 0.416f, 0.875f); // blue
				break;
			case 3:
				this.highlight = new Color(0.886f, 0.545f, 0.024f); // yellow
				break;
			case 4:
				this.highlight = new Color(0.035f, 0.667f, 0.090f); // green
				break;
			default:
				this.highlight = Color.WHITE();
				break;
		}
	}
	
	public int getPlayerNumber() { return this.playerNumber; }
	
	public void pushInstruction(Instruction i)
	{
		this.instructions.offer(i);
	}
	
	public void removeFront()
	{
		this.instructions.removeFirst();
	}
	
	public void removeBack()
	{
		if (this.instructions.size() > 0)
			this.instructions.removeLast();
	}
	
	public void clear()
	{
		this.instructions = new LinkedList<Instruction>();
	}
	
	@Override
	public void draw(RenderBatch b)
	{
		b.draw(new RenderInstance()
				.withTexture("uiBlank")
				.withDepth(16f)
				.withLayer(2)
				.withColor(this.color)
				.withDestinationRegion(new Region(this.position, this.size, false))
				.build()
				);
		
		for (int i = 0; i < this.instructions.size(); i++)
		{
			b.draw(new RenderInstance()
				.withTexture("uiBlank")
				.withDepth(17f)
				.withLayer(2)
				.withColor(new Color(0.2f, 0.2f, 0.2f))
				.withDestinationRegion(new Region(
					new Vector2(this.position.x + 6 + (i * 42), this.position.y + 6),
					new Vector2(32), 
					false
					))
				.build()
				);
			
			b.draw(new RenderInstance()
				.withTexture("icon" + this.instructions.get(i).toString())
				.withDestinationRegion(new Region(
					new Vector2(this.position.x + 6 + (i * 42), this.position.y + 6),
					new Vector2(32),
					false
					))
				.withColor(this.highlight)
				.withLayer(2)
				.withDepth(18f)
				.build()
				);
		}
	}
}
