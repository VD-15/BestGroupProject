package robotGame.tiles;

import robotGame.Direction;
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
	 * Constructs flag tile.
	 * @param index {@link utils.Point Point} where the {@link robotGame.tiles.BoardTile tile} is indexed.
	 * @param flagNumber The number of the flag.
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
	public void onRobotEnter(Robot robot, Direction direction) 
	{
		// Runs method of robot stood on tile.
		robot.addFlag(flagNumber);
	}
}
