package robotGame.tiles;

import core.Game;
import graphics.RenderBatch;
import graphics.RenderInstance;
import robotGame.Robot;
import utils.Point;
import utils.Region;
import utils.Vector2;

/**
 * Flag Tile
 * 
 * @author Jedd Morgan, Vanessa Kostadinova
 * @version 01/04/2019
 */
public class FlagTile extends BoardTile 
{
	
	private int flagNumber;

	public FlagTile(Point index, int flagNumber) 
	{
		super(index);
		this.flagNumber = flagNumber;
		this.tag = "tileFlag";
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
			.withTexture("tileFlag" + this.flagNumber)
			.withDestinationRegion(new Region(this.position, new Vector2(TILE_SIZE), true))
			.withDepth(1f)
			.withLayer(1)
			);
	}

}
