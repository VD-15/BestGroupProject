package robotGame;

import graphics.IDrawable;
import graphics.RenderBatch;
import graphics.RenderInstance;
import robotGame.Core.GameObject;
import robotGame.Core.IUpdatable;
import utils.Region;
import utils.Vector2;

public class TestObject extends GameObject implements IDrawable, IUpdatable
{
	private float rotation;
	
	public TestObject(Vector2 v)
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
		this.rotation += (float)time;
	}

	@Override
	public void draw(RenderBatch b)
	{
		b.draw(new RenderInstance()
			.withTexture("testImage")
			.withDestinationRegion(new Region(this.position, new Vector2(200), true))
			.withRotation(this.rotation)
			.withRotationOrigin(this.position)
			.withDepth(1f)
			);
	}
	
}
