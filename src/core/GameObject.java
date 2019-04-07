package core;

import java.util.ArrayList;

import utils.Vector2;

public abstract class GameObject
{
	protected Vector2 position;
	protected String tag;
	protected GameObject parent;
	protected ArrayList<GameObject> children;
	
	public GameObject()
	{
		this.position = new Vector2();
		this.tag = "";
	}
	
	public GameObject(Vector2 location, String tag)
	{
		this.position = location;
		this.tag = tag;
	}
	
	public abstract void init();
}
