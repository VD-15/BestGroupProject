package robotGame;

import java.util.Queue;

import core.GameObject;
import core.IUpdatable;
import graphics.IDrawable;
import graphics.RenderBatch;
import graphics.RenderInstance;
import utils.Direction;
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
	/** Indices for coordinates on board*/
	private Point index;
	/** Index that the robot starts on*/
	private final Point startIndex;
	/** Current direction the robot is facing */
	private Direction facingDirection;
	/** Player number */
	private int number;
	
	/** queue of actions to be committed */
	private Queue<Instruction> actions;

	
	/** The default Directions for all robots*/
	private static final Direction DEFAULT_DIRECTION = Direction.SOUTH;
	
	
	/**
	 * Robot Constructor
	 * @param index starting location
	 */
	public Robot(Point index, int number)
	{
		super();
		this.position = new Vector2(index.x * 64, index.y * 64); //TODO 64 should not be hard coded
		this.index = index;
		this.startIndex = index;
		this.facingDirection = DEFAULT_DIRECTION;
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
	 * 
	 */
	public void getInstructions(Instruction[] instructions) //FIXME should be named setInstructions?
	{
		// Pushes instructions form an array into the actions queue
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
		// Pop the action from the queue.
		Instruction i = actions.poll();
		
		changeDirection(i.getRotation());
		move(facingDirection, i.getTranslation());
	}
	
	
	/**
	 * Adds a angle to the direction
	 * @param angle in half pi radians (90� left = +1) accepts any integer value
	 * @return new direction after angle transform
	 */
	public void changeDirection(int rotation) {
		Direction oldD =  facingDirection;
		facingDirection = facingDirection.add(rotation);
		Logger.log(this, LogSeverity.INFO, "Robot" + number + " direction was " + oldD + " is now " + facingDirection + " after adding rotation of " + rotation);
	}
	

	/**
	 * 
	 * @param direction to move robot in
	 */
	public void move(Direction direction) {
		move(direction, 1);
	}
	
	/**
	 * @param direction to move robot in
	 */
	public void move(Direction direction, int ammount) {
		//FIXME not the best implementation robot shouldn't decide what the Direction's transform is
		switch (direction)
		{
			case NORTH:
				this.index.y += ammount;
				break;
			case SOUTH:
				this.index.y -= ammount;
				break;
			case EAST:
				this.index.x += ammount;
				break;
			case WEST:
				this.index.x -= ammount;
				break;
			default:
				Logger.log(this, LogSeverity.ERROR, "Robot had invalid direction. Resetting to north.");
				facingDirection = Direction.NORTH;
				this.index.y += ammount;
				break;
		}
	}
	
	/**
	 * Reset the Location of Robot back to its starting position
	 */
	public void resetLocation()
	{
		index = startIndex;
		facingDirection = DEFAULT_DIRECTION;
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
