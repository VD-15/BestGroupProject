package Game;

import Graphics.RenderBatch;
import Graphics.RenderInstance;
import Utils.Region;
import Utils.Vector2;

/**
 * Gear Tile. 
 * 
 * @author Jedd Morgan, Vanessa Kostadinova
 * @version 01/04/2019
 */
public class GearTile extends Location {
	
	private Robot currentRobot;
	private boolean clockwise;

	/**
	 * 
	 * @param placement The place where the tile is drawn.
	 * @param clockwise True = clockwise. False = counterclockwise.
	 */
	public GearTile(Vector2 placement, boolean clockwise) 
	{
		super();
		this.location = placement;
		this.clockwise = clockwise;
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
		String gearType = (clockwise) ? "tileGearC" : "tileGearCC";
		
		b.draw(new RenderInstance()
			.withTexture(gearType)
			.withDestinationRegion(new Region(this.location, new Vector2(64), true))
			.withDepth(1f)
			);
	}

	
}
