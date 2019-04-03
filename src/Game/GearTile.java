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
 * @author Jedd Morgan, Vanessa Kostadinova
 * @version 01/04/2019
 */
public class GearTile extends Location {
	
	private Robot currentRobot;
	private boolean clockwise;

	/**
	 * 
	 * @param placement The place where the tile is drawn.
	 * @param clockwise True = clockwise. False = counterclockwise.
	 */
	public GearTile(Vector2 placement, boolean clockwise) {
		super();
		this.location = placement;
		this.clockwise = clockwise;
	}
	
	@Override
	public void init() {
		
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
