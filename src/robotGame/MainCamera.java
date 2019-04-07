package robotGame;

import core.Camera;
import core.Game;
import core.IUpdatable;

public class MainCamera extends Camera implements IUpdatable
{
	public MainCamera()
	{
		super(1);
	}
	
	@Override
	public void update(double time)
	{
		this.viewportSize = Game.getWindow().getViewport();
	}
}
