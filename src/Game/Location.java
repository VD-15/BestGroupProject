package Game;

import Game.Core.GameObject;
import Utils.Vector2;

/**
 * Abstract Location
 * 
 * @author Jedd Morgan
 * @version 25/03/2019
 */
public abstract class Location extends GameObject{
	
	
	/** Tile action, called at end of game turn*/
	public abstract void act();
	
	/** On Land action, called on the new tile when a {@link Robot} changes {@link Location} 
	 * @param robot the {@link Robot} that has landed on the {@link Location} 
	 * */
	public abstract void onLanded(Robot robot);
	
}
