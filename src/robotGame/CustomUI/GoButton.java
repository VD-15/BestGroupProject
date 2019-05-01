package robotGame.CustomUI;

import UI.Button;
import core.Game;
import core.IUpdatable;
import graphics.Color;
import robotGame.GameManager;
import utils.Vector2;

public class GoButton extends Button implements IUpdatable
{
	public GoButton()
	{
		this.setText("  GO!");
		this.setWidth(138);
		this.tag = "buttonGo";
	}
	
	@Override
	public void update(double time)
	{
		this.position = new Vector2(10, Game.getWindow().getViewport().y - (Button.BUTTON_HEIGHT + 10));
	}

	@Override
	protected void onClick()
	{
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
