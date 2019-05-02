package robotGame;

import java.util.LinkedList;
import java.util.Queue;

import core.GameObject;
import graphics.IDrawable;
import graphics.RenderBatch;
import graphics.RenderInstance;
import robotGame.tiles.BoardTile;
import utils.LogSeverity;
import utils.Logger;
import utils.Point;
import utils.Region;
import utils.Vector2;

/**
 * Robot
 * 
 * @author Jedd Morgan
 * @author Vanessa Kostadinova
 * @version 30/05/2019
 */
public class Robot extends GameObject implements IDrawable
{
	/** Indices for coordinates on board*/
	private Point index;
	/** Index that the robot starts on*/
	private final Point startIndex;
	/** Current direction the robot is facing */
	private Direction facingDirection;
	/** Player number */
	private int number;
	/** Player default health*/
	private static final int DEFAULT_HEALTH = 5;
	/** Player health*/
	private int health;
	/** Number of flags collected*/
	private int flags;
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
		// Sets variables
		super();
		this.position = new Vector2(index.x * 64, index.y * 64);
		this.index = index;
		this.startIndex = index.clone();
		this.facingDirection = DEFAULT_DIRECTION;
		this.number = number;
		this.tag = "robot";
		this.health = DEFAULT_HEALTH;
		this.flags = 0;
	}

	/**
	 * Initialise
	 */
	@Override
	public void init()
	{
		Board.getTile(index).onRobotEnter(this, DEFAULT_DIRECTION);
		actions = new LinkedList<Instruction>();
	}

	/**
	 * Returns index.
	 * @return
	 */
	public Point getIndex() {
		return index;
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
		//actions.add(Instruction.FORWARD);
		// Pop the action from the queue.
		Instruction i = actions.poll();
		rotate(i.getRotation());
		move(facingDirection, i.getTranslation());
	}


	/**
	 * Adds a angle to the direction
	 * @param angle in half pi radian's (90° right = +1) accepts any integer value
	 * @return new direction after angle transform
	 */
	public void rotate(int rotation) {
		Direction oldD =  facingDirection;
		facingDirection = facingDirection.add(rotation);

		if (facingDirection != oldD)
			Logger.log(this, LogSeverity.INFO, "Robot" + number + " direction was " + oldD + " is now " + facingDirection + " after adding rotation of " + rotation);
	}


	/**
	 * @param direction to move robot in
	 */
	public void move(Direction direction) {
		move(direction, 1);
	}

	/**
	 * @param direction to move robot in
	 */
	public void move(Direction direction, int ammount) {
		
		Point pIndex = index.clone();
		
		Board.getTile(index).onRobotLeave(this);

		switch (direction)
		{
		case NORTH:
			this.index.y -= ammount;
			break;
		case SOUTH:
			this.index.y += ammount;
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
	
		setPosition(index);

		if (!pIndex.equals(index))
			Logger.log(this, LogSeverity.INFO, "Moving Robot" + number + " in direction " + direction + ammount + " from (" + pIndex.x + "," + pIndex.y + ") to (" + index.x + "," + index.y + ")" );

		BoardTile b = Board.getTile(index);
		
		if (b == null) {
			resetLocation();
			return;
		}
		
		b.onRobotEnter(this, direction);

	}
	
	private void setPosition(Point p) {
		index = new Point(p.x, p.y);
		position = new Vector2(p.x * 64, p.y * 64);
	}

	/**
	 * Reset the Location of Robot back to its starting position
	 */
	public void resetLocation()
	{
		BoardTile tile = Board.getTile(index);
        if(tile != null) tile.onRobotLeave(this);
		setPosition(startIndex);
		facingDirection = DEFAULT_DIRECTION;
		Logger.log(this, LogSeverity.INFO, "Reseting Robot" + number + " to start location (" + index.x + "," + index.y + ")" );
		health = DEFAULT_HEALTH;
		
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
				.build()
				);
	}

	/**
	 * Adds flag to the flags the robot has.
	 * @param flagNumber
	 */
	public void addFlag(int flagNumber) {
		// Checks if previous flags have been attained. If so current flag is taken.
		if((flags + 1) == flagNumber) {
			flags ++;
			Logger.log(this, LogSeverity.INFO, "Robot" + number + " has collected flag tile" + flagNumber);
		}
	}
	
	/**
	 * Returns number of flags gotten.
	 * @return flags
	 */
	public int getFlag() 
	{
		return flags;
	}
	
	/**
	 * Returns the robot's number
	 * @return robot's number
	 */
	public int getNumber() {
		return number;
	}
	
	/**
	 * Performs damage calculations on robot.
	 */
	public void addDamage() {
		// Reduces health.
		health --;;
		Logger.log(this, LogSeverity.INFO, "Robot" + number + " has been damaged " + "health is now " + health);
		
	}
	
	/**
	 * Returns current health of robot.
	 * @return health
	 */
	public int getHealth() {
		return health;
	}
	
	@Override
	public void destroy() {
		// No implementation required
	}

}
