package robotGame;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import core.GameObject;
import core.IUpdatable;
import graphics.Color;
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
 * @version 27/04/2019
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
	private static final Direction DEFAULT_DIRECTION = Direction.NORTH;


	/**
	 * Robot Constructor
	 * @param index - starting location
	 * @param number - number of the robot for textures and logging
	 */
	public Robot(Point index, int number)
	{
		super();
		this.position = new Vector2(index.x * 64, index.y * 64);
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
		Board.getTile(index).onRobotEnter(this);
		actions = new LinkedList<Instruction>();
	}

	/**
	 * 
	 */
	public void setInstructions(Instruction[] instructions)
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
		//for Testing purposes
		//actions.add(Instruction.RIGHT);
		// Pop the action from the queue.
		Instruction i = actions.poll();
		rotate(i.getRotation());
		move(facingDirection, i.getTranslation());
	}


	/**
	 * Adds a angle to the direction
	 * @param angle in half pi radians (90° right = +1) accepts any integer value
	 * @return new direction after angle transform
	 */
	public void rotate(int rotation) {
		Direction oldD =  facingDirection;
		facingDirection = facingDirection.add(rotation);

		if (facingDirection != oldD)
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

		Point pIndex = index;
		Board.getTile(index).onRobotLeave(this);

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
			Logger.log(this, LogSeverity.ERROR, "Robot" + number + " had invalid direction. Resetting to" + DEFAULT_DIRECTION);
			facingDirection = DEFAULT_DIRECTION;
			this.index.y += ammount;
			break;
		}

		Board.getTile(index).onRobotEnter(this);


		position = new Vector2(index.x * 64, index.y * 64);
		if (pIndex.x != index.x || pIndex.y != index.y)
			Logger.log(this, LogSeverity.INFO, "Moving Robot" + number + " from (" + pIndex.x + "," + pIndex.y + ") to (" + index.x + "," + index.y + ")" );
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
				.withTexture("robot" + number)
				.withDestinationRegion(new Region(this.position, new Vector2(64), true))
				.withDepth(2f)
				.withLayer(1)
				.withRotation(facingDirection.getAngle())
				.withRotationOrigin(this.position)
				);
	}

	public void addFlag(int flagNumber) {
		// TODO Work out how we are handling flag tiles!
		
	}

}
