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
 * @author Jedd Morgan
 * @author Vanessa Kostadinova
 * @version 24/05/2019
 */
public class FlagTile extends BoardTile 
{
	private int flagNumber;

	public FlagTile(Point index, int flagNumber) 
	{
		super(index);
		this.flagNumber = flagNumber;
		this.tag = "flagTile";
	}
	
	/**
	 * Adds the flag to the robot.
	 */	
	@Override
	public void onRobotEnter(Robot robot) 
	{
		robot.addFlag(flagNumber);
	}

	@Override
	public void draw(RenderBatch b) 
	{
		b.draw(new RenderInstance()
			.withTexture("tileFlag" + this.flagNumber)
			.withDestinationRegion(new Region(this.position, new Vector2(TILE_SIZE), true))
			.withDepth(1f)
			.withLayer(1)
			.build()
		);
	}
}
