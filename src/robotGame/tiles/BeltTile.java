package robotGame.tiles;

import core.Game;
import graphics.RenderBatch;
import graphics.RenderInstance;
import robotGame.Robot;
import utils.Direction;
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
	
	/**
	 * 
	 * @param direction The direction the tile acts in.
	 */
	public BeltTile(Point index, Direction direction) 
	{
		super(index);
		this.direction = direction;
		this.tag = "tileBelt";
	}
	
	@Override
	public void init() 
	{
		// No implementation
	}
	
	@Override
	public void act() 
	{
		//currentRobot.move();
	}

	@Override
	public void onRobotEnter(Robot robot) 
	{
		
	}
	
	@Override
	public void onRobotLeave(Robot robot)
	{
		
	}

	@Override
	public void draw(RenderBatch b) 
	{
		b.draw(new RenderInstance()
			.withTexture("tileBelt" + direction)
			.withDestinationRegion(new Region(this.position, new Vector2(TILE_SIZE), true))
			.withDepth(1f)
			.withLayer(1)
			);		
	}

}
