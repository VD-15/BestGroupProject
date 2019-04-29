package robotGame.tiles;

import core.Game;
import core.GameObject;
import graphics.IDrawable;
import graphics.RenderBatch;
import graphics.RenderInstance;
import robotGame.Robot;
import utils.Point;
import utils.Region;
import utils.Vector2;

/**
 * Normal Tile. Tile that does nothing
 * 
 * @author Jedd Morgan, Vanessa Kostadinova
 * @version 24/05/2019
 */
public class BoardTile extends GameObject implements IDrawable
{
	protected Robot currentRobot;
	
	protected static final int TILE_SIZE = 64;

	public BoardTile(Point index)
	{
		super();
		this.position = new Vector2(index.x * TILE_SIZE, index.y * TILE_SIZE);
		this.tag = "tile";
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

	public void onRobotEnter(Robot robot)
	{
		currentRobot = robot;
	}
	
	public void onRobotLeave(Robot robot)
	{
		currentRobot = null;
	}

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

	@Override
	public void destroy()
	{
		// TODO Auto-generated method stub
		
	}
}
