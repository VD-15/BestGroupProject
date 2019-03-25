package Game;

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

}
