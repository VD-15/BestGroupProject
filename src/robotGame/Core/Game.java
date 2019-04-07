package robotGame.Core;

import java.util.ArrayList;
import java.util.HashSet;

import graphics.IDrawable;
import graphics.RenderBatch;
import robotGame.GameManager;
import utils.LogSeverity;
import utils.Logger;

public class Game
{
	private static ArrayList<GameObject> objects = new ArrayList<GameObject>();;
	private static HashSet<GameObject> toAdd = new HashSet<GameObject>();
	private static HashSet<GameObject> toRemove = new HashSet<GameObject>();
	private static double lastNano = 0d;
		
	public static void init()
	{		
		//Don't touch this unless you hate yourself
		Logger.setLogSeverity(LogSeverity.INFO);
		
		GameManager manager = new GameManager();
		manager.loadBoardFromPlainText(ContentManager.getTextByName("testBoard"));
		manager.init();
		
		lastNano = System.nanoTime();
	}
	
	public static void update()
	{
		double newNano = System.nanoTime();
		double delta = (newNano - lastNano) / 1000000000f;
		
		for (GameObject g : objects)
		{
			if (g instanceof IUpdatable)
			{
				IUpdatable i = (IUpdatable)g;
				i.update(delta);
			}
		}
		
		for (GameObject g : toRemove)
		{
			objects.remove(g);
		}
		
		
		for (GameObject g : toAdd)
		{
			objects.add(g);
			g.init();
		}
		
		toRemove = new HashSet<GameObject>();
		toAdd = new HashSet<GameObject>();
		
		lastNano = newNano;
	}
	
	public static void draw(RenderBatch b)
	{
		for (GameObject g : objects)
		{
			if (g instanceof IDrawable)
			{
				IDrawable i = (IDrawable)g;
				i.draw(b);
			}
		}
	}
	
	public static void instantiate(GameObject g)
	{
		toAdd.add(g);
	}
	
	public static void destroy(GameObject g)
	{
		toRemove.add(g);
	}
	
	public static ArrayList<GameObject> getGameObjectsByTag(String tag)
	{
		ArrayList<GameObject> matches = new ArrayList<GameObject>();
		
		for (GameObject g : objects)
		{
			if (g.tag.equals(tag))
			{
				matches.add(g);
			}
		}
		
		return matches;
	}
}
