package robotGame;

import core.Camera;
import core.Game;
import core.IUpdatable;
import utils.Region;

public class UICamera extends Camera implements IUpdatable
{
	public UICamera()
	{
		super(2);
	}
	
	@Override
	public void init()
	{
		
	}
	
	@Override
	public void update(double time)
	{
		this.viewportSize = Game.getWindow().getViewport();
	}
	
	@Override
	public Region getViewport()
	{
		return new Region(this.position, this.viewportSize, false);
	}
	
}
