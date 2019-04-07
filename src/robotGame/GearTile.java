package robotGame;

import graphics.RenderBatch;
import graphics.RenderInstance;
import utils.Region;
import utils.Vector2;

/**
 * Gear Tile. 
 * 
 * @author Jedd Morgan, Vanessa Kostadinova
 * @version 01/04/2019
 */
public class GearTile extends Location 
{
	private boolean isClockwise;

	/**
	 * 
	 * @param placement The place where the tile is drawn.
	 * @param clockwise True = clockwise. False = counterclockwise.
	 */
	public GearTile(Vector2 position, boolean clockwise) 
	{
		super();
		this.position = position;
		this.isClockwise = clockwise;
	}
	
	@Override
	public void init()
	{
		
	}

	/**
	 * {@inheritDoc}:
	 * Rotates the robot 90°
	 * */
	@Override
	public void act()
	{
		// currentRobot.rotate();
	}
	
	@Override
	public void onLanded(Robot robot)
	{
		currentRobot = robot;
	}

	@Override
	public void draw(RenderBatch b)
	{
		String gearType = (isClockwise) ? "tileGearC" : "tileGearCC";
		
		b.draw(new RenderInstance()
			.withTexture(gearType)
			.withDestinationRegion(new Region(this.position, new Vector2(64), true))
			.withDepth(1f)
			);
	}

	
}
