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
	public TestObject(Vector2 v)
	{
		super(v.clone(), "testObject");
	}

	@Override
	public void init()
	{
		
	}

	@Override
	public void update(double time)
	{
		this.position = this.position.rotateAround((float)time, new Vector2(500, 500));
	}

	@Override
	public void draw(RenderBatch b)
	{
		b.draw("testImage2", new Region(this.position, new Vector2(631, 270), true), Color.WHITE(), 1f);
	}
	
}
