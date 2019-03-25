package Game;

import java.net.URL;
import java.util.Queue;

import Game.Core.GameObject;
import Game.Core.IUpdatable;
import Graphics.IDrawable;
import Graphics.RenderBatch;

/**
 * Robot
 * 
 * @author Jedd Morgan
 * @version 25/03/2019
 */
public class Robot extends GameObject implements IDrawable, IUpdatable {

	/** x index in the {@link Game#boardArray} */
	private int x;
	/** y index in the {@link Game#boardArray} */
	private int y;
	
	/** queue of actions to be committed*/
	private Queue<Instruction> actions;
	/** Maximum number of actions allowed in the queue*/
	private static final int MAX_NO_ACTIONS = 5;
	
	
	/** Absolute direction the game object is facing*/
	private Direction facingDirection;
	
	
	/**
	 * Robot Constructor
	 * */
	public Robot() {
		facingDirection = Direction.South;
	}
	
	/**
	 * Initialise
	 * */
	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * Read Instructions from file
	 * @param URL to text file containing instructions
	 * @return boolean for whether the file parsed successfully
	 */
	public boolean readInstructionsFromFile(URL file) {
		//TODO Implementation required: Parse instructions from file, check they are valid and push them to queue
		//boolean return may not be necessary?
		return false;
	}
	
	/**
	 * Robot will commit an action from actions queue
	 */
	public void commitAction() {
		//TODO Implementation required: pop from queue and act
	}
	
	/**
	 * Reset the Location of Robot back to its starting position
	 */
	public void resetLocation() {
		//TODO Implementation required: Robot is destroyed (eg. from a PitTile) and its Location should be reset to it's starting Location
		//May need to store a reference to it's starting Location?
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
