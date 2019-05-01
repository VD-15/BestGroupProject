package robotGame.tiles;

import core.GameObject;
import graphics.IDrawable;
import graphics.RenderBatch;
import graphics.RenderInstance;
import robotGame.Direction;
import robotGame.Robot;
import utils.Point;
import utils.Region;
import utils.Vector2;

/**
 * Normal Tile.
 * 
 * @author Jedd Morgan
 * @author Vanessa Kostadinova
 * @version 01/06/2019
 */
public class BoardTile extends GameObject implements IDrawable
{
	/** {@link robotGame.Robot Robot} instance stored in tile.*/
	protected Robot currentRobot;
	/** Default tile size for every tile*/
	protected final int TILE_SIZE = 64;
	
	protected String sprite;

	public BoardTile(Point index)
	{
		// Sets variables.
		super();
		this.position = new Vector2(index.x * TILE_SIZE, index.y * TILE_SIZE);
		this.tag = "tile";
		sprite = "tileNormal";
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
	 * @param direction Direction that the robot has moved in from
	 */
	public void onRobotEnter(Robot robot, Direction direction)
	{
		// Sets currentRobot variable to robot which has entered.
		currentRobot = robot;
	}
	
	/**
	 * Clears instance of robot when it leaves.
	 * @param robot {@link robotGame.Robot Robot} which has left the tile.
	 */
	public void onRobotLeave(Robot robot)
	{
		// Cleared currentRobot variable on robot exit.
		currentRobot = null;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void draw(RenderBatch b)
	{
		b.draw(new RenderInstance()
			// Sets texture to variable sprite.
			.withTexture(sprite)
			// Sets position
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
