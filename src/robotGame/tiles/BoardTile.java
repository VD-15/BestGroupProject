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
 * Normal Tile. Blank tile that does nothing
 * 
 * @author Jedd Morgan, Vanessa Kostadinova
 * @version 01/04/2019
 */
public class BoardTile extends GameObject implements IDrawable
{

	public BoardTile(Point index)
	{
		super();
		this.position = new Vector2(index.x * 64, index.y * 64);
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
			.withDestinationRegion(new Region(this.position, new Vector2(64), true))
			.withDepth(1f)
			.withLayer(1)
			);
	}
}
