package Game;

import Game.Core.GameObject;
import Game.Core.IUpdatable;
import Graphics.IDrawable;
import Graphics.RenderBatch;
import Graphics.RenderInstance;
import Utils.Region;
import Utils.Vector2;

public class TestObject2 extends GameObject implements IDrawable, IUpdatable
{
	private float rotation;
	
	public TestObject2(Vector2 v)
	{
		super(v.clone(), "testObject");
		this.rotation = 0f;
	}

	@Override
	public void init()
	{
		
	}

	@Override
	public void update(double time)
	{
		this.rotation -= (float)time;
	}

	@Override
	public void draw(RenderBatch b)
	{
		b.draw(new RenderInstance()
			.withTexture("testImage")
			.withDestinationRegion(new Region(this.location, new Vector2(200), true))
			.withRotation(this.rotation)
			.withRotationOrigin(this.location)
			.withDepth(2f)
			);
	}
}
