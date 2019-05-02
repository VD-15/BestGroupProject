package robotGame.CustomUI;

import UI.Button;
import core.Game;
import core.IUpdatable;
import graphics.RenderBatch;
import graphics.RenderInstance;
import robotGame.GameManager;
import robotGame.Instruction;
import utils.Region;
import utils.Vector2;

/**
 * A button representing an instruction that can be used to add an instruction to a robot
 * @author Vee
 */
public class InstructionButton extends Button implements IUpdatable
{
	/**
	 * The instruction that this button represents
	 */
	private Instruction instruction;
	
	/**
	 * The offset from the bottom-left of the window
	 */
	private Vector2 offset;
	
	/**
	 * Creates an instruction button
	 * @param offset The {@link #offset offset} from the bottom-left of the window
	 * @param i The {@link #instruction instruction} that is stored in this button
	 */
	public InstructionButton(Vector2 offset, Instruction i)
	{
		super();
		this.instruction = i;
		this.offset = offset;
		this.tag = "instructionButton" + i.toString();
		this.setWidth(64);
		if (GameManager.getfromFile() == true)
		{
			this.disable();
		}
	}
	
	@Override
	protected void onClick()
	{
		String button = this.tag.split("instructionButton")[1];
		GameManager o = (GameManager) Game.getGameObjectsByTag("gameManager").get(0);
		o.programInstructions(button);

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
