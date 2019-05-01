package robotGame.tiles;

import utils.Point;

/**
 * Laser Receiver
 * 
 * @author Vanessa Kostadinova
 * @version 01/06/2019
 */
public class LaserReceiver extends BoardTile {

	public LaserReceiver(Point index) {
		// Sets variables
		super(index);
		this.tag = "LaserReceiver";
		this.sprite = "tileLaserReceiver";
	}
}
