package core;

import utils.Vector2;

/**
 * Base class for all objects in the game.
 * @author Vee
 *
 */
public abstract class GameObject
{
	/**
	 * Position of the object in world space
	 */
	protected Vector2 position;
	
	/**
	 * tag of the object that is used to identify it.
	 */
	protected String tag;
	
	/**
	 * Creates a new GameObject
	 */
	public GameObject()
	{
		this.position = new Vector2();
		this.tag = "";
	}
	
	/**
	 * Creates a new GameObject
	 * @param location the position of the GameObject in world space
	 * @param tag the tag of the GameObject
	 */
	public GameObject(Vector2 location, String tag)
	{
		this.position = location;
		this.tag = tag;
	}
	
	public abstract void init();
	public abstract void destroy();
}
