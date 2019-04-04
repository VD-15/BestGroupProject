package Game;

import Game.Core.GameObject;
import Graphics.IDrawable;

/**
 * Abstract Location
 * 
 * @author Jedd Morgan
 * @version 25/03/2019
 */
public abstract class Location extends GameObject implements IDrawable 
{
	
	protected Robot currentRobot;
	
	/** Tile action, called at end of game turn*/
	public abstract void act();
	
	/** On Land action, called on the new tile when a Robot changes Location
	 * @param robot the Robot that has landed on the Location
	 * */
	public abstract void onLanded(Robot robot);
	
}
