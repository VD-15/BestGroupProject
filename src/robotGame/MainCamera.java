package robotGame;

import core.Camera;
import core.Game;
import core.IUpdatable;
import utils.Vector2;

public class MainCamera extends Camera implements IUpdatable
{
	public MainCamera()
	{
		super(1);
	}
	
	@Override
	public void init()
	{
		this.viewportSize = Game.getWindow().getViewport();
		this.position = new Vector2(300,200);
	}
	
	@Override
	public void update(double time)
	{
		
	}
}
