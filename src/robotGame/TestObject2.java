package robotGame;

import core.GameObject;
import core.IUpdatable;
import graphics.IDrawable;
import graphics.RenderBatch;
import graphics.RenderInstance;
import utils.Region;
import utils.Vector2;

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
			.withDestinationRegion(new Region(this.position, new Vector2(200), true))
			.withRotation(this.rotation)
			.withRotationOrigin(this.position)
			.withDepth(2f)
			);
	}

	@Override
	public void destroy()
	{
		// TODO Auto-generated method stub
		
	}
}
