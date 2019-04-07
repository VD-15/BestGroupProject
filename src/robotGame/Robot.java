package robotGame;

import java.util.Queue;

import core.Game;
import core.GameObject;
import core.IUpdatable;
import graphics.IDrawable;
import graphics.RenderBatch;
import graphics.RenderInstance;
import utils.LogSeverity;
import utils.Logger;
import utils.Point;
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
	/** Maximum number of actions allowed in the queue */
	private static final int MAX_ACTIONS = 5;
	
	private Point index;
	private int number;

	private final Point startIndex;
	
	/** queue of actions to be committed */
	private Queue<Instruction> actions;
	
	/** Absolute direction the game object is facing */
	private Direction facingDirection;
	
	/**
	 * Robot Constructor
	 * @param position
	 * @param index starting location
	 */
	public Robot(Point index, int number)
	{
		super();
		this.position = new Vector2(index.x * 64, index.y * 64);
		this.index = index;
		this.startIndex = index;
		this.facingDirection = Direction.NORTH;
		this.number = number;
		this.tag = "robot";
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
	 * Commit an action from the action queue
	 */
	public void act()
	{
		// pop the action from the queue.
		Instruction i = actions.poll();
		
		switch (i)
		{
			case FORWARD:
				moveForward();
				break;
			case BACKWARD:
				moveBackward();
				break;
			case LEFT:
				turnLeft();
				break;
			case RIGHT:
				turnRight();
				break;
			case UTURN:
				uTurn();
				break;
			case WAIT:
			default:
				break;
		}
	}
	
	private void uTurn()
	{
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
			default:
				Logger.log(this, LogSeverity.ERROR, "Robot had invalid direction. Resetting to north.");
				facingDirection = Direction.NORTH;
				break;
		}
	}

	private void turnRight()
	{
		switch (facingDirection)
		{
			case NORTH:
				facingDirection = Direction.EAST;
				break;
			case SOUTH:
				facingDirection = Direction.WEST;
				break;
			case EAST:
				facingDirection = Direction.SOUTH;
				break;
			case WEST:
				facingDirection = Direction.NORTH;
				break;
			default:
				Logger.log(this, LogSeverity.ERROR, "Robot had invalid direction. Resetting to north.");
				facingDirection = Direction.NORTH;
				break;
		}
	}

	private void turnLeft()
	{
		switch (facingDirection)
		{
			case NORTH:
				facingDirection = Direction.WEST;
				break;
			case SOUTH:
				facingDirection = Direction.EAST;
				break;
			case EAST:
				facingDirection = Direction.NORTH;
				break;
			case WEST:
				facingDirection = Direction.SOUTH;
				break;
			default:
				Logger.log(this, LogSeverity.ERROR, "Robot had invalid direction. Resetting to north.");
				facingDirection = Direction.NORTH;
				break;
		}
	}

	private void moveBackward()
	{
		switch (facingDirection)
		{
			case NORTH:
				this.index.y--;
				break;
			case SOUTH:
				this.index.y++;
				break;
			case EAST:
				this.index.x--;
				break;
			case WEST:
				this.index.x++;
				break;
			default:
				Logger.log(this, LogSeverity.ERROR, "Robot had invalid direction. Resetting to north.");
				facingDirection = Direction.NORTH;
				this.index.y--;
				break;
		}
	}

	private void moveForward()
	{
		switch (facingDirection)
		{
			case NORTH:
				this.index.y++;
				break;
			case SOUTH:
				this.index.y--;
				break;
			case EAST:
				this.index.x++;
				break;
			case WEST:
				this.index.x--;
				break;
			default:
				Logger.log(this, LogSeverity.ERROR, "Robot had invalid direction. Resetting to north.");
				facingDirection = Direction.NORTH;
				this.index.y++;
				break;
		}
	}

	/**
	 * Reset the Location of Robot back to its starting position
	 */
	public void resetLocation()
	{
		index = startIndex;
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
			.withLayer(1)
		);
	}
	
}
