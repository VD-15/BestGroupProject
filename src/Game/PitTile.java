package Game;

import Graphics.RenderBatch;

/**
 * Pit Tile
 * 
 * @author Jedd Morgan
 * @version 25/03/2019
 */
public class PitTile extends Location {

	@Override
	public void init() {
		// TODO Auto-generated method stub
		
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(double time) {
		// TODO Auto-generated method stub
		
	}

}
