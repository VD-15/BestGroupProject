package robotGame.tiles;

import core.Game;
import core.GameObject;
import graphics.IDrawable;
import graphics.RenderBatch;
import graphics.RenderInstance;
import robotGame.Robot;
import utils.Direction;
import utils.Point;
import utils.Region;
import utils.Vector2;

/**
 * Normal Tile. Tile that does nothing
 * 
 * @author Jedd Morgan, Vanessa Kostadinova
 * @version 01/04/2019
 */
public class BoardTile extends GameObject implements IDrawable
{
	
	protected static final int TILE_SIZE = 64;

	public BoardTile(Point index)
	{
		super();
		this.position = new Vector2(index.x * TILE_SIZE, index.y * TILE_SIZE);
		this.tag = "tile";
	}
	
	@Override
	public void init()
	{
		
	}
	
	public void act()
	{
		
	}

	public void onRobotEnter(Robot robot)
	{
		
	}
	
	public void onRobotLeave(Robot robot)
	{
		
	}

	@Override
	public void draw(RenderBatch b)
	{
		b.draw(new RenderInstance()
			.withTexture("tileNormal")
			.withDestinationRegion(new Region(this.position, new Vector2(TILE_SIZE), true))
			.withDepth(1f)
			.withLayer(1)
			);
	}
}
