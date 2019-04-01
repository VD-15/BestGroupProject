package Game;

import Graphics.Color;
import Graphics.RenderBatch;
import Utils.Region;
import Utils.Vector2;

/**
 * Pit Tile
 * 
 * @author Jedd Morgan
 * @version 25/03/2019
 */
public class PitTile extends Location
{
	public PitTile(Vector2 placement)
	{
		super();
		this.location = placement;
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
		b.draw("tilePit", new Region(this.location, new Vector2(64, 64), true), Color.WHITE(), 1f);
	}

	@Override
	public void update(double time) {
		
	}

}
