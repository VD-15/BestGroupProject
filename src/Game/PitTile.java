package Game;

import Graphics.Color;
import Graphics.RenderBatch;
import Utils.Region;
import Utils.Vector2;

/**
 * Pit Tile
 * 
 * @author Jedd Morgan, Vanessa Kostadinova
 * @version 01/04/2019
 */
public class PitTile extends Location
{
	public PitTile(Vector2 placement)
	{
		super();
		this.position = placement;
	}
	
	
	@Override
	public void init() {
		
	}
	
	@Override
	public void act() {
		//No implementation required
	}

	@Override
	public void onLanded(Robot robot) {
		robot.resetLocation();
		
	}

	@Override
	public void draw(RenderBatch b) {
		b.draw("tilePit", new Region(this.position, new Vector2(64, 64), true), Color.WHITE(), 1f);
	}
}
