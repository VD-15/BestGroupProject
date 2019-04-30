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
 * @version 24/05/2019
 */
public class GearTile extends BoardTile 
{
	/**
	 * Stores the rotation quantity, 1 for left, -1 for right.
	 */
	private int rotation;
	
	private boolean isClockwise;

	/**
	 * 
	 * @param index {@inheritDoc}
	 * @param clockwise Boolean which determines what direction the gear acts in, true for clockwise, false for counterclockwise.
	 */
	public GearTile(Point index, boolean isClockwise) 
	{
		super(index);
		this.isClockwise = isClockwise;
		if(isClockwise) {
			rotation = -1;
		} else {
			rotation = 1;
		}
	}
	
	/**
	 * {@inheritDoc}:
	 * Rotates the robot 90°
	 * */
	@Override
	public void act()
	{
		currentRobot.rotate(rotation);
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
			.build()
			);
	}

	
}
