package Game;

import Graphics.RenderBatch;
import Graphics.RenderInstance;
import Utils.Region;
import Utils.Vector2;

/**
 * Flag Tile
 * 
 * @author Jedd Morgan, Vanessa Kostadinova
 * @version 01/04/2019
 */
public class FlagTile extends Location 
{
	
	private int flagNumber;

	public FlagTile(Vector2 placement, int flagNumber) 
	{
		super();
		this.position = placement; 
		this.flagNumber = flagNumber;
	}
	
	@Override
	public void init() 
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void act() 
	{
		//No implementation required
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
			.withTexture("tileFlag" + this.flagNumber)
			.withDestinationRegion(new Region(this.position, new Vector2(64), true))
			.withDepth(1f)
			);
	}

}
