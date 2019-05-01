package robotGame.CustomUI;

import UI.Button;
import core.Game;
import core.IUpdatable;
import graphics.Color;
import robotGame.GameManager;
import utils.Vector2;

/**
 * A big, fat go button to confirm instructions for a robot and start a round
 * @author Vee
 */
public class GoButton extends Button implements IUpdatable
{
	/**
	 * Creates a go button
	 */
	public GoButton()
	{
		super();
		this.setText("  GO!");
		this.setWidth(138);
		this.tag = "buttonGo";
	}
	
	/**
	 * Called every frame to keep the button aligned with the bottom of the window
	 */
	@Override
	public void update(double time)
	{
		this.position = new Vector2(10, Game.getWindow().getViewport().y - (Button.BUTTON_HEIGHT + 10));
	}

	@Override
	protected void onClick()
	{
		//on click the go button runs GameManager.setRound
		GameManager o = (GameManager) Game.getGameObjectsByTag("gameManager").get(0);
		o.setRound();
	}
	
	@Override
	protected Color getEnabledColor()
	{
		return new Color(1.0f, 1.5f, 1.0f);
	}
	
	@Override
	protected Color getHoveredColor()
	{
		return new Color(1.5f, 2.0f, 1.5f);
	}
	
	@Override
	protected Color getClickedColor()
	{
		return new Color(0.5f, 1.0f, 0.5f);
	}
}
