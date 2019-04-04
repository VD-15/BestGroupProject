package Game;

import java.io.File;
import java.net.URL;
import java.util.Queue;

import Game.Core.GameObject;
import Game.Core.IUpdatable;
import Graphics.IDrawable;
import Graphics.RenderBatch;
import Utils.LogSeverity;
import Utils.Logger;

/**
 * Robot
 * 
 * @author Jedd Morgan
 * @version 03/04/2019
 */
public class Robot extends GameObject implements IDrawable, IUpdatable 
{

	/** x index in the {@link GameManager#boardArray} */
	private int x = 0;
	/** y index in the {@link GameManager#boardArray} */
	private int y = 0;
	
	Location location;
	Location startLocation;
	
	/** queue of actions to be committed*/
	private Queue<Instruction> actions;
	/** Maximum number of actions allowed in the queue*/
	private static final int MAX_NO_ACTIONS = 5;
	
	
	/** Absolute direction the game object is facing*/
	private Direction facingDirection;
	
	
	/**
	 * Robot Constructor
	 * @param location starting location
	 * */
	public Robot(Location location) 
	{
		this.location = location;
		this.startLocation = location; 
		facingDirection = Direction.SOUTH;
	}
	
	/**
	 * Initialise
	 * */
	@Override
	public void init() 
	{
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * Read Instructions from file
	 * @param File to text file containing instructions
	 * @return boolean for whether the file parsed successfully
	 */
	public void getInstructions(Instruction[] instructions)
	{
		//pushes instructions form an array into the actions queue
		for(Instruction i : instructions)
		{
			actions.offer(i);
		}
	}
	
	/**
	 * Robot will commit an action from actions queue
	 */
	public void commitAction() 
	{
		//pop the action from the queue.
		Instruction i = actions.poll();
		//if the action is Uturn
		if (i == Instruction.UTURN)
		{
			//the current facing direction is found and changed to its inverse.
			switch(facingDirection)
			{
			case NORTH : facingDirection = Direction.SOUTH; break;
			case SOUTH : facingDirection = Direction.NORTH; break;
			case EAST : facingDirection = Direction.WEST; break;
			case WEST : facingDirection = Direction.EAST; break; 
			//if the default case is reached an error is logged.
			default : Logger.log(this, LogSeverity.ERROR, "Invalid direction");;
			}
		}
		else
		{
			//x or y values representing the robots new position are changed depending on the action taken.
			switch(i)
			{
			case FORWARD : this.y++; break;
			case BACKWARD : this.y--; break;
			case RIGHT : this.x++; break;
			case LEFT : this.x--; break;
			case WAIT :break;
			//if the default case is reached an error is logged
			default : Logger.log(this, LogSeverity.ERROR, "Invalid direction");
			}
			//the robots location is updated based on the location returned by the game manager.
			//getLocation is given the updated x and y values to find teh new location.
			location = GameManager.getLocation(x, y);
			
		}
	}
	
	/**
	 * Reset the Location of Robot back to its starting position
	 */
	public void resetLocation() 
	{
		//Robot is destroyed (eg. from a PitTile) and its Location should be reset to it's starting Location
		location = startLocation;
	}

	@Override
	public void update(double time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void draw(RenderBatch b) {
		// TODO Auto-generated method stub
		
	}

	
}
