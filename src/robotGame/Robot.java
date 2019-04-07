package robotGame;

import java.util.Queue;

import graphics.IDrawable;
import graphics.RenderBatch;
import graphics.RenderInstance;
import robotGame.Core.GameObject;
import robotGame.Core.IUpdatable;
import utils.LogSeverity;
import utils.Logger;
import utils.Region;
import utils.Vector2;

/**
 * Robot
 * 
 * @author Jedd Morgan
 * @version 03/04/2019
 */
public class Robot extends GameObject implements IDrawable, IUpdatable
{
	
	/** x index in the {@link GameManager#boardArray} */
	private int xIndex = 0;
	/** y index in the {@link GameManager#boardArray} */
	private int yIndex = 0;
	
	private Location location;
	private Location startLocation;
	
	/** queue of actions to be committed */
	private Queue<Instruction> actions;
	
	/** Maximum number of actions allowed in the queue */
	private static final int MAX_ACTIONS = 5;
	
	/** Absolute direction the game object is facing */
	private Direction facingDirection;
	
	/**
	 * Robot Constructor
	 * 
	 * @param location starting location
	 */
	public Robot(Location location, Vector2 position, int xIndex, int yIndex)
	{
		super();
		this.location = location;
		this.position = position;
		this.xIndex = xIndex;
		this.yIndex = yIndex;
		this.startLocation = location;
		this.facingDirection = Direction.SOUTH;
	}
	
	/**
	 * Initialise
	 */
	@Override
	public void init()
	{
		
	}
	
	/**
	 * Read Instructions from file
	 * 
	 * @param File to text file containing instructions
	 * @return boolean for whether the file parsed successfully
	 */
	public void getInstructions(Instruction[] instructions)
	{
		// pushes instructions form an array into the actions queue
		for (Instruction i : instructions)
		{
			actions.offer(i);
		}
	}
	
	/**
	 * Robot will commit an action from actions queue
	 */
	public void commitAction()
	{
		// pop the action from the queue.
		Instruction i = actions.poll();
		
		// if the action is Uturn
		if (i == Instruction.UTURN)
		{
			// the current facing direction is found and changed to its inverse.
			switch (facingDirection)
			{
				case NORTH:
					facingDirection = Direction.SOUTH;
					break;
				case SOUTH:
					facingDirection = Direction.NORTH;
					break;
				case EAST:
					facingDirection = Direction.WEST;
					break;
				case WEST:
					facingDirection = Direction.EAST;
					break;
				// if the default case is reached an error is logged.
				default:
					Logger.log(this, LogSeverity.ERROR, "Invalid direction");
					break;
			}
		} 
		else
		{
			// x or y values representing the robots new position are changed depending on
			// the action taken.
			switch (i)
			{
				case FORWARD:
					this.yIndex++;
					break;
				case BACKWARD:
					this.yIndex--;
					break;
				case RIGHT:
					this.xIndex++;
					break;
				case LEFT:
					this.xIndex--;
					break;
				case WAIT:
					break;
				default:
					Logger.log(this, LogSeverity.ERROR, "Invalid direction");
			}
			
			// The robot's location is updated based on the location returned by the game manager.
			// getLocation is given the updated x and y values to find teh new location.
			location = GameManager.getLocation(xIndex, yIndex);
		}
	}
	
	/**
	 * Reset the Location of Robot back to its starting position
	 */
	public void resetLocation()
	{
		// Robot is destroyed (eg. from a PitTile) and its Location should be reset to it's starting Location
		location = startLocation;
	}
	
	@Override
	public void update(double time)
	{
		
	}
	
	@Override
	public void draw(RenderBatch b)
	{
		b.draw(new RenderInstance()
				.withTexture("robot" + facingDirection)
				.withDestinationRegion(new Region(this.position, new Vector2(64), true))
				.withDepth(2f)
				);
	}
	
}
