package Game;

import Graphics.RenderBatch;
import Graphics.RenderInstance;
import Utils.Region;
import Utils.Vector2;

/**
 * Normal Tile. Blank tile that does nothing
 * 
 * @author Jedd Morgan, Vanessa Kostadinova
 * @version 01/04/2019
 */
public class NormalTile extends Location
{

	public NormalTile(Vector2 placement)
	{
		super();
		this.position = placement;
	}
	
	@Override
	public void init()
	{
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * {@inheritDoc}
	 * No implementation required
	*/
	@Override
	public void act() { }

	/**
	 * {@inheritDoc}
	 * No implementation required
	*/	
	@Override
	public void onLanded(Robot robot) { }

	@Override
	public void draw(RenderBatch b)
	{
		b.draw(new RenderInstance()
			.withTexture("tileNormal")
			.withDestinationRegion(new Region(this.position, new Vector2(64), true))
			.withDepth(1f)
			);
	}
}
