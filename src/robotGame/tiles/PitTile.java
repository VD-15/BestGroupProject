package robotGame.tiles;

import core.Game;
import graphics.RenderBatch;
import graphics.RenderInstance;
import robotGame.Robot;
import utils.LogSeverity;
import utils.Logger;
import utils.Point;
import utils.Region;
import utils.Vector2;

/**
 * Pit Tile
 * 
 * @author Jedd Morgan
 * @author Vanessa Kostadinova
 * @version 24/05/2019
 */
public class PitTile extends BoardTile
{
	/**
	 * Constructs pit tile.
	 * @param index
	 */
	public PitTile(Point index)
	{
		super(index);
		this.tag = "tilePit";
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onRobotEnter(Robot robot)
	{
		robot.resetLocation();
	}
	
	/**
	 * {@inheritDoc}
	 */
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
