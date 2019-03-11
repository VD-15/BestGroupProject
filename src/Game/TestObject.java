package Game;

import Game.Core.GameObject;
import Game.Core.IUpdatable;
import Graphics.Color;
import Graphics.IDrawable;
import Graphics.RenderBatch;
import Utils.LogSeverity;
import Utils.Logger;
import Utils.Region;
import Utils.Vector2;

public class TestObject extends GameObject implements IDrawable, IUpdatable
{
	public TestObject()
	{
		super(new Vector2(), "testObject");
	}

	@Override
	public void init()
	{
		
	}

	@Override
	public void update(double time)
	{
		this.location = this.location.rotateAround((float)time, new Vector2(1024));
		Logger.log(this, LogSeverity.VERBOSE, Double.toString(time));
	}

	@Override
	public void draw(RenderBatch b)
	{
		b.draw("testTexture", new Region(this.location, new Vector2(1024), true), Color.WHITE(), 0f);
	}
	
}
