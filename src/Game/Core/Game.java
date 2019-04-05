package Game.Core;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

import Game.GameManager;
import Game.Location;
import Graphics.IDrawable;
import Graphics.RenderBatch;
import Utils.LogSeverity;
import Utils.Logger;

public class Game
{
	private static Game INSTANCE = null;
	
	private ArrayList<GameObject> objects;
	private HashSet<GameObject> toAdd;
	private HashSet<GameObject> toRemove;
	private double lastNano;
	
	public Game()
	{
		if (Game.INSTANCE != null)
		{
			Logger.log(this, LogSeverity.WARNING, "Multiple games running, application may not function correctly.");
		}
		else
		{
			Game.INSTANCE = this;
		}
		
		this.objects = new ArrayList<GameObject>();
		this.lastNano = 0d;
	}
	
	public void init()
	{
		GameManager g = new GameManager();
		try {
			
			g.generateBoard(new File("Resources/boards/testboard.brd"));
			
			for (GameObject[] a : g.getArray())
				for (GameObject o : a) {
					this.objects.add(o);
				}
			
			for (GameObject o : g.getRobots()) {
				this.objects.add(o);
			}
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Logger.log(this, LogSeverity.WARNING, "Read an invalid character whilst trying to generate board from file");
		}
		
		this.lastNano = System.nanoTime();
	}
	
	public void update()
	{
		double newNano = System.nanoTime();
		double delta = (newNano - this.lastNano) / 1000000000f;
		
		for (GameObject g : objects)
		{
			if (g instanceof IUpdatable)
			{
				IUpdatable i = (IUpdatable)g;
				i.update(delta);
			}
		}
		
		this.lastNano = newNano;
	}
	
	public void draw(RenderBatch b)
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
	
	public void destroy()
	{
		Game.INSTANCE = null;
	}
	
	public static Game getGame()
	{
		return INSTANCE;
	}
	
	public void instantiate(GameObject g)
	{
		toAdd.add(g);
	}
	
	public void destroy(GameObject g)
	{
		toRemove.add(g);
	}
	
	public ArrayList<GameObject> getGameObjectsByTag(String tag)
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
