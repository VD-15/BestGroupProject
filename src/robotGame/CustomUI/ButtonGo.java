package robotGame.CustomUI;

import UI.Button;
import core.Game;
import core.IUpdatable;
import graphics.Color;
import utils.Vector2;

public class ButtonGo extends Button implements IUpdatable
{
	public ButtonGo()
	{
		this.setText("    GO!");
		this.setWidth(236);
	}
	
	@Override
	public void update(double time)
	{
		this.position = new Vector2(10, Game.getWindow().getViewport().y - (Button.BUTTON_HEIGHT + 10));
	}

	@Override
	protected void onClick()
	{
		
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
