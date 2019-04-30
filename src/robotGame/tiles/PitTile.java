package robotGame.tiles;

import core.Game;
import graphics.RenderBatch;
import graphics.RenderInstance;
import robotGame.Robot;
import utils.Point;
import utils.Region;
import utils.Vector2;

/**
 * Pit Tile
 * 
 * @author Jedd Morgan, Vanessa Kostadinova
 * @version 24/05/2019
 */
public class PitTile extends BoardTile
{
	public PitTile(Point index)
	{
		super(index);
		this.tag = "tilePit";
	}
	
	/**
	 * 
	 */
	@Override
	public void onRobotEnter(Robot robot)
	{
		robot.resetLocation();
	}
	
	@Override
	public void draw(RenderBatch b)
	{
		b.draw(new RenderInstance()
				.withTexture("tilePit")
				.withDestinationRegion(new Region(this.position, new Vector2(TILE_SIZE), true))
				.withDepth(1f)
				.withLayer(1)
				.build()
				);
	}
}
