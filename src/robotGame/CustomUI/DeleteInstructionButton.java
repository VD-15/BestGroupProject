package robotGame.CustomUI;

import UI.Button;
import core.Game;
import core.IUpdatable;
import graphics.Color;
import graphics.RenderBatch;
import graphics.RenderInstance;
import robotGame.GameManager;
import utils.Region;
import utils.Vector2;

/**
 * A button that allows the player to remove an instruction from their robot
 * @author Vee
 */
public class DeleteInstructionButton extends Button implements IUpdatable
{
	/**
	 * Creates a delete instruction button
	 */
	public DeleteInstructionButton()
	{
		super();
		this.tag = "deleteInstructionButton";
		this.setWidth(64);
	}

	/**
	 * Called every frame to keep the button aligned with the bottom of the window
	 */
	@Override
	public void update(double time)
	{
		this.position = new Vector2(158, Game.getWindow().getViewport().y - (Button.BUTTON_HEIGHT + 10));
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
		GameManager o = (GameManager) Game.getGameObjectsByTag("gameManager").get(0);
		o.deleteInstruction();
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
