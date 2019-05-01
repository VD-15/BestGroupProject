package robotGame.tiles;

import core.GameObject;
import graphics.IDrawable;
import graphics.RenderBatch;
import graphics.RenderInstance;
import robotGame.Robot;
import utils.Point;
import utils.Region;
import utils.Vector2;

/**
 * Normal Tile.
 * 
 * @author Jedd Morgan
 * @author Vanessa Kostadinova
 * @version 30/05/2019
 */
public class BoardTile extends GameObject implements IDrawable
{
	/** {@link robotGame.Robot Robot} instance stored in tile.*/
	protected Robot currentRobot;
	
	protected static final int TILE_SIZE = 64;

	public BoardTile(Point index)
	{
		super();
		this.position = new Vector2(index.x * TILE_SIZE, index.y * TILE_SIZE);
		this.tag = "tile";
	}
	
	/**
	 * Returns instance of {@link robotGame.Robot Robot} stored in tile.
	 * @return
	 */
	public Robot getRobot() {
		return currentRobot;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public void init()
	{
		// No implementation needed.
	}
	
	/**
	 * Acts on the robot at the start of a turn.
	 */
	public void act()
	{
		// No implementation needed.
	}

	/**
	 * Stores an instance of the robot which has entered this tile.
	 * @param robot {@link robotGame.Robot Robot} which has entered the tile.
	 */
	public void onRobotEnter(Robot robot)
	{
		currentRobot = robot;
	}
	
	/**
	 * Clears instance of robot when it leaves.
	 * @param robot {@link robotGame.Robot Robot} which has left the tile.
	 */
	public void onRobotLeave(Robot robot)
	{
		currentRobot = null;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void draw(RenderBatch b)
	{
		b.draw(new RenderInstance()
			.withTexture("tileNormal")
			.withDestinationRegion(new Region(this.position, new Vector2(TILE_SIZE), true))
			.withDepth(1f)
			.withLayer(1)
			.build()
			);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void destroy()
	{
		// No implementation required.
		
	}
}
