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

/**
 * A control to view all the instructions present in a robot
 * @author Vee
 */
public class InstructionViewer extends Panel
{
	/**
	 * A copy of the instructions in the robot
	 * This is a can't really be a reference to the robot or it's instructions because it is created before the board (and robot) is loaded
	 */
	private LinkedList<Instruction> instructions;
	
	/**
	 * The player number of the robot this object is viewing
	 */
	private int playerNumber;
	
	/**
	 * The highlight of the player robot that can be used to identify the control
	 */
	private Color highlight;
	
	/**
	 * Created an instruction viewer
	 * @param playerNumber The player number of the robot this object is viewing
	 * @param location The location on screen of the control, measured in pixels
	 */
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
	
	/**
	 * Gets the player number this control is observing
	 * @return The player number this control is observing
	 */
	public int getPlayerNumber() { return this.playerNumber; }
	
	/**
	 * Pushes an instruction to the back of this instruction list
	 * @param i the instruction to add
	 */
	public void pushInstruction(Instruction i)
	{
		this.instructions.addLast(i);
	}

	/**
	 * Removes an instruction from the front of this instruction list
	 * Has no effect if the list is empty
	 */
	public void removeFront()
	{
		if (this.instructions.size() > 0)
			this.instructions.removeFirst();
	}

	/**
	 * Removes an instruction from the back of this instruction list
	 * Has no effect if the list is empty
	 */
	public void removeBack()
	{
		if (this.instructions.size() > 0)
			this.instructions.removeLast();
	}
	
	/**
	 * Empties the instruction list
	 */
	public void clear()
	{
		this.instructions = new LinkedList<Instruction>();
	}
	
	@Override
	public void draw(RenderBatch b)
	{
		//Draw the background of the viewer
		b.draw(new RenderInstance()
				.withTexture("uiBlank")
				.withDepth(16f)
				.withLayer(2)
				.withColor(this.color)
				.withDestinationRegion(new Region(this.position, this.size, false))
				.build()
				);
		
		//Draw each instruction
		for (int i = 0; i < this.instructions.size(); i++)
		{
			//Back panel
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
			
			//Instruction icon
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
