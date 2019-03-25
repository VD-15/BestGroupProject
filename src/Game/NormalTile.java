package Game;

import Graphics.RenderBatch;

/**
 * Normal Tile. Blank tile that does nothing
 * 
 * @author Jedd Morgan
 * @version 25/03/2019
 */
public class NormalTile extends Location {

	@Override
	public void init() {
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
	public void draw(RenderBatch b) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(double time) {
		// TODO Auto-generated method stub
		
	}



}
