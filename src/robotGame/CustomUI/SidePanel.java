package robotGame.CustomUI;

import UI.Panel;
import core.Game;
import core.IUpdatable;
import graphics.Color;

/**
 * A side panel at the left-hand side of the screen that resizes with the window and seperates the UI from the world
 * @author Valkyrie
 *
 */
public class SidePanel extends Panel implements IUpdatable
{
	/**
	 * Creates a side panel
	 */
	public SidePanel()
	{
		super();
		this.color = new Color(0.1f, 0.1f, 0.1f);
		this.tag = "UI_sidePanel";
		this.size.x = 232;
	}
	
	/**
	 * Updates every frame to resize with the window
	 */
	@Override
	public void update(double time)
	{
		this.size.y = Game.getWindow().getViewport().length();
	}
	
}
