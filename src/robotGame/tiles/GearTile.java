package robotGame.tiles;

import utils.Point;

/**
 * Gear Tile. 
 * 
 * @author Jedd Morgan
 * @author Vanessa Kostadinova
 * @version 01/06/2019
 */
public class GearTile extends BoardTile 
{
	/** Stores the rotation quantity, 1 for left, -1 for right.*/
	private int rotation;

	/**
	 * Constructs a Gear tile.
	 * @param index {@link utils.Point Point} where the {@link robotGame.tiles.BoardTile tile} is indexed.
	 * @param clockwise Boolean which determines what direction the gear acts in, true for clockwise, false for counterclockwise.
	 */
	public GearTile(Point index, boolean isClockwise) 
	{
		// Sets variables.
		super(index);
		this.sprite = "tileGear";
		
		// Changes sprite based on rotation.
		if(isClockwise) {
			this.rotation = 1;
			this.sprite += "C";
		} else {
			this.rotation = -1;
			this.sprite += "CC";
		}
	}
	
	/**
     * {@inheritDoc}<br>
     * Rotates the robot 90° according to its {@link robotGame.tiles.GearTile#rotation rotation} value.
     * */
    @Override
    public void act()
    {
    	// If robot is on tile rotation is applied.
        if(currentRobot != null) {
        currentRobot.rotate(rotation);
        }
    }
	
}
