package robotGame.CustomUI;

import UI.Panel;
import core.Game;
import core.IUpdatable;
import graphics.Color;

public class SidePanel extends Panel implements IUpdatable
{
	public SidePanel()
	{
		super();
		this.color = new Color(0.1f, 0.1f, 0.1f);
		this.tag = "UI_sidePanel";
		this.size.x = 232;
	}
	
	@Override
	public void update(double time)
	{
		this.size.y = Game.getWindow().getViewport().length();
	}
	
}
