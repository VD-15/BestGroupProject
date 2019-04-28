package core;

import java.util.ArrayList;
import java.util.LinkedList;

import graphics.IDrawable;
import graphics.RenderBatch;
import robotGame.GameManager;
import robotGame.MainCamera;
import utils.LogSeverity;
import utils.Logger;

public class Game
{
	private static LinkedList<GameObject> objects = new LinkedList<GameObject>();;
	private static LinkedList<GameObject> toAdd = new LinkedList<GameObject>();
	private static LinkedList<GameObject> toRemove = new LinkedList<GameObject>();
	
	private static GameWindow window;
	
	private static double lastNano = 0d;
	private static boolean isRunning = false;
	
	public static void init(GameWindow g)
	{		
		window = g;
		
		//Don't touch this unless you hate yourself
		Logger.setLogSeverity(LogSeverity.INFO);
		
		Game.instantiate(new GameManager());
		Game.instantiate(new MainCamera());
		
		lastNano = System.nanoTime();
		isRunning = true;
	}
	
	public static void update()
	{
		if (window.getKeyboard().isKeyDown("gameQuit"))
		{
			isRunning = false;
			return;
		}
		
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
		
		while (!toRemove.isEmpty())
		{
			objects.remove(toRemove.poll());
		}		
		
		while (!toAdd.isEmpty())
		{
			GameObject g = toAdd.poll();
			objects.offer(g);
			g.init();
		}
		
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
		toAdd.offer(g);
	}
	
	public static void destroy(GameObject g)
	{
		toRemove.offer(g);
	}
	
	public static GameWindow getWindow()
	{
		return window;
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
	
	public static boolean isRunning()
	{
		return isRunning;
	}
}
