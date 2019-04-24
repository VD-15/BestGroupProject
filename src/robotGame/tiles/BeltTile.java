package robotGame.tiles;

import core.Game;
import graphics.RenderBatch;
import graphics.RenderInstance;
import robotGame.Robot;
import utils.Direction;
import utils.LogSeverity;
import utils.Logger;
import utils.Point;
import utils.Region;
import utils.Vector2;

/**
 * Belt Tile
 * 
 * @author Jedd Morgan, Vanessa Kostadinova
 * @version 01/04/2019
 */
public class BeltTile extends BoardTile 
{
	private Direction direction;
	private int rotation;
	
	/**
	 * Creates a new straight belt
	 * @param direction The direction the tile acts in.
	 */
	public BeltTile(Point index, Direction direction) 
	{
		this(index, direction, 0);
	}
	
	/**
	 * Creates a new belt
	 * @param index
	 * @param direction The direction the tile acts in.
	 * @param rotation for corner belts
	 */
	public BeltTile(Point index, Direction direction, int rotation) 
	{
		super(index);
		this.direction = direction;
		this.tag = "tileBelt";
		this.rotation = rotation;
	}
	
	@Override
	public void init() 
	{
		// No implementation
	}
	
	@Override
	public void act() 
	{
		//TODO
		//currentRobot.move(direction);
	}

	@Override
	public void onRobotEnter(Robot robot) 
	{
		//TODO
	}
	
	@Override
	public void onRobotLeave(Robot robot)
	{
		//TODO
	}

	@Override
	public void draw(RenderBatch b) 
	{
		String name = "tileBelt" + direction.getName();
		
		switch (rotation % Direction.values().length) {
		case 1:
			name += "CC";
			break;
		case -1:
			name += "C";
			break;
		case 0: break;
		default:
			name = "tileError";
			Logger.log(this, LogSeverity.WARNING, "No sprite for belt with rotation" + rotation);
			break;
		}
		
		
		b.draw(new RenderInstance()
			.withTexture(name)
			.withDestinationRegion(new Region(this.position, new Vector2(TILE_SIZE), true))
			.withDepth(1f)
			.withLayer(1)
			);		
	}

}
