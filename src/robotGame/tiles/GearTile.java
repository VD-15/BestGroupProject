package robotGame.tiles;

import core.Game;
import graphics.RenderBatch;
import graphics.RenderInstance;
import robotGame.Robot;
import utils.Point;
import utils.Region;
import utils.Vector2;

/**
 * Gear Tile. 
 * 
 * @author Jedd Morgan, Vanessa Kostadinova
 * @version 01/04/2019
 */
public class GearTile extends BoardTile 
{
	private boolean isClockwise;

	public GearTile(Point index, boolean clockwise) 
	{
		super(index);
		this.isClockwise = clockwise;
		this.tag = "tileGear";
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
		
	}
	
	@Override
	public void onRobotEnter(Robot robot)
	{
		//currentRobot = robot;
	}
	
	@Override 
	public void onRobotLeave(Robot robot)
	{
		
	}

	@Override
	public void draw(RenderBatch b)
	{
		String gearType = (isClockwise) ? "tileGearC" : "tileGearCC";
		
		b.draw(new RenderInstance()
			.withTexture(gearType)
			.withDestinationRegion(new Region(this.position, new Vector2(TILE_SIZE), true))
			.withDepth(1f)
			.withLayer(1)
			);
	}

	
}
