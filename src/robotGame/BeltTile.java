package robotGame;

import graphics.RenderBatch;
import graphics.RenderInstance;
import utils.Region;
import utils.Vector2;

/**
 * Belt Tile
 * 
 * @author Jedd Morgan, Vanessa Kostadinova
 * @version 01/04/2019
 */
public class BeltTile extends Location 
{
	private Direction direction;
	
	/**
	 * 
	 * @param placement The location where the tile is drawn.
	 * @param direction The direction the tile acts in.
	 */
	public BeltTile(Vector2 placement, Direction direction) 
	{
		super();
		this.position = placement;
		this.direction = direction;
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
	public void onLanded(Robot robot) 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void draw(RenderBatch b) 
	{
		b.draw(new RenderInstance()
			.withTexture("tileBelt" + direction)
			.withDestinationRegion(new Region(this.position, new Vector2(64), true))
			.withDepth(1f)
			);		
	}

}
