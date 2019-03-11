package Game.Core;

import java.util.ArrayList;

import Utils.Vector2;

public abstract class GameObject
{
	protected Vector2 location;
	protected String tag;
	protected GameObject parent;
	protected ArrayList<GameObject> children;
	
	public GameObject()
	{
		this.location = new Vector2();
		this.tag = "";
	}
	
	public GameObject(Vector2 location, String tag)
	{
		this.location = location;
		this.tag = tag;
	}
	
	public abstract void init();
}
