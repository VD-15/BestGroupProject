package Game;

import Graphics.Color;
import Graphics.RenderBatch;
import Graphics.RenderInstance;
import Utils.Region;
import Utils.Vector2;

/**
 * Pit Tile
 * 
 * @author Jedd Morgan, Vanessa Kostadinova
 * @version 01/04/2019
 */
public class PitTile extends Location
{
	public PitTile(Vector2 placement)
	{
		super();
		this.location = placement;
	}
	
	
	@Override
	public void init()
	{
		
	}
	
	@Override
	public void act()
	{
		//No implementation required
	}

	@Override
	public void onLanded(Robot robot)
	{
		robot.resetLocation();
		
	}

	@Override
	public void draw(RenderBatch b)
	{
		b.draw(new RenderInstance()
				.withTexture("tilePit")
				.withDestinationRegion(new Region(this.location, new Vector2(64), true))
				.withDepth(1f)
				);
	}
}
