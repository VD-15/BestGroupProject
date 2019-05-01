package robotGame.tiles;

import robotGame.Direction;
import utils.Point;

/**
 * Laser Receiver
 * 
 * @author Vanessa Kostadinova
 * @version 01/06/2019
 */
public class LaserReceiver extends BoardTile {
	
	private final Direction direction;

	public LaserReceiver(Point index, Direction direction) {
		// Sets variables
		super(index);
		this.tag = "LaserReceiver";
		this.sprite = "tileLaserReceiver";
		this.direction = direction;
	}

	public Direction getDirection() {
		return direction;
	}
}
