package robotGame.tiles;

import robotGame.Direction;
import robotGame.Robot;
import utils.Point;

/**
 * Pit Tile
 * 
 * @author Jedd Morgan
 * @author Vanessa Kostadinova
 * @version 01/06/2019
 */
public class PitTile extends BoardTile
{
	/**
	 * Constructs pit tile.
	 * @param index
	 */
	public PitTile(Point index)
	{
		// Sets variables
		super(index);
		this.tag = "tilePit";
		this.sprite = "tilePit";
	}
	
	/**
	 * {@inheritDoc}
	 * Resets the location of the robot upon entry.
	 */
	@Override
	public void onRobotEnter(Robot robot, Direction direction)
	{
		robot.resetLocation();
	}
}
