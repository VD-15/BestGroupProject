package Game;

import Game.Core.IUpdatable;
import Graphics.Color;
import Graphics.IDrawable;
import Graphics.RenderBatch;
import Utils.Region;
import Utils.Vector2;

/**
 * Gear Tile. 
 * 
 * @author Jedd Morgan
 * @version 25/03/2019
 */
public class GearTile extends Location {
	
	private Robot currentRobot;
	private boolean clockwise;

	public GearTile(Vector2 placement, boolean type) {
		super();
		this.location = placement;
		clockwise = type;
	}
	
	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}

	/**
	 * {@inheritDoc}:
	 * Rotates the robot 90°
	 * */
	@Override
	public void act() {
		// currentRobot.rotate();
	}
	
	@Override
	public void onLanded(Robot robot) {
		currentRobot = robot;
	}

	@Override
	public void update(double time) {
		
	}

	@Override
	public void draw(RenderBatch b) {
		String gearType;
		
		if(clockwise) {
			gearType = "tileGearC";
		}
		else {
			gearType = "tileGearCC";
		}
		
		b.draw(gearType, new Region(this.location, new Vector2(64, 64), true), Color.WHITE(), 1f);
	}

	
}
