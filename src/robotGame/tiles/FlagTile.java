package robotGame.tiles;

import robotGame.Robot;
import utils.Point;

/**
 * Flag Tile
 * 
 * @author Jedd Morgan
 * @author Vanessa Kostadinova
 * @version 01/06/2019
 */
public class FlagTile extends BoardTile 
{
	/** Number of flag */
	private int flagNumber;

	/**
	 * Creates flag tile.
	 * @param index
	 * @param flagNumber
	 */
	public FlagTile(Point index, int flagNumber) 
	{
		// Sets variables.
		super(index);
		this.flagNumber = flagNumber;
		this.tag = "flagTile";
		this.sprite = "tileFlag" + this.flagNumber;
	}
	
	/**
	 * Adds the flag to the robot.
	 */	
	@Override
	public void onRobotEnter(Robot robot) 
	{
		// Runs method of robot stood on tile.
		robot.addFlag(flagNumber);
	}
}
