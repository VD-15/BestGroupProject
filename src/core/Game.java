package core;

import java.util.ArrayList;
import java.util.LinkedList;

import graphics.IDrawable;
import graphics.RenderBatch;
import robotGame.GameManager;
import robotGame.Instruction;
import robotGame.MainCamera;
import robotGame.UICamera;
import robotGame.CustomUI.DeleteInstructionButton;
import robotGame.CustomUI.GoButton;
import robotGame.CustomUI.InstructionButton;
import robotGame.CustomUI.PlayerLabel;
import robotGame.CustomUI.SidePanel;
import utils.LogSeverity;
import utils.Logger;
import utils.Vector2;

/**
 * Facilitates the running of the game
 * @author Vee
 *
 */
public class Game
{
	/**
	 * GameObjects currently in the game
	 */
	private static LinkedList<GameObject> objects = new LinkedList<GameObject>();
	
	/**
	 * GameObjects to be added to the game
	 */
	private static LinkedList<GameObject> toAdd = new LinkedList<GameObject>();
	
	/**
	 * GameObjects to remove from the game
	 */
	private static LinkedList<GameObject> toRemove = new LinkedList<GameObject>();
	
	/**
	 * The window the game is being hosted in
	 */
	private static GameWindow window;
	
	/**
	 * Stores the last read of System.nanoTime() to
	 * allow for smooth movement of various objects
	 * regardless of frame rate
	 */
	private static double lastNano = 0d;
	
	/**
	 * Whether or not the game is still running.
	 * Setting this to false will terminate the
	 * application
	 */
	private static boolean isRunning = false;
	
	/**
	 * Initialises the game in the given window
	 * @param g
	 */
	public static void init(GameWindow g)
	{
		if (isRunning)
		{
			Logger.log(Game.class, LogSeverity.ERROR, "Tried to instantiate a game when one was already running.");
		}
		
		window = g;
		
		//Don't touch this unless you hate yourself
		Logger.setLogSeverity(LogSeverity.INFO);
		
		Game.instantiate(new GameManager());
		Game.instantiate(new MainCamera());
		Game.instantiate(new UICamera());
		Game.instantiate(new SidePanel());
		Game.instantiate(new PlayerLabel());
		Game.instantiate(new GoButton());
		Game.instantiate(new InstructionButton(new Vector2(10, 148), Instruction.FORWARD));
		Game.instantiate(new InstructionButton(new Vector2(10, 222), Instruction.BACKWARD));
		Game.instantiate(new InstructionButton(new Vector2(84, 148), Instruction.LEFT));
		Game.instantiate(new InstructionButton(new Vector2(158, 148), Instruction.RIGHT));
		Game.instantiate(new InstructionButton(new Vector2(84, 222), Instruction.UTURN));
		Game.instantiate(new InstructionButton(new Vector2(158, 222), Instruction.WAIT));
		Game.instantiate(new DeleteInstructionButton(new Vector2(158, 626)));
		
		lastNano = System.nanoTime();
		isRunning = true;
	}
	
	/**
	 * Updates the game's logic
	 */
	public static void update()
	{
		//Check if the game should update
		if (window.getKeyboard().isKeyDown("gameQuit"))
		{
			isRunning = false;
			return;
		}
		
		//Calculate time since last update
		double newNano = System.nanoTime();
		double delta = (newNano - lastNano) / 1000000000f;
		
		//Update every object
		for (GameObject g : objects)
		{
			if (g instanceof IUpdatable)
			{
				IUpdatable i = (IUpdatable)g;
				i.update(delta);
			}
		}
		
		//Remove old objects from game
		while (!toRemove.isEmpty())
		{
			GameObject g = toRemove.poll();
			g.destroy();
			objects.remove(g);
		}		
		
		//Add new objects to game
		while (!toAdd.isEmpty())
		{
			GameObject g = toAdd.poll();
			objects.offer(g);
			g.init();
		}
		
		lastNano = newNano;
	}
	
	/**
	 * Renders the game to the given RenderBatch
	 * @param b The renderbatch to draw the game to
	 */
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
	
	/**
	 * Instantiates a new GameObject to the game. 
	 * Use this instead of adding to toAdd or gameObjects
	 * @param g
	 */
	public static void instantiate(GameObject g)
	{
		toAdd.offer(g);
	}
	
	/**
	 * Removes a gameobject from the game.
	 * Use this instead of adding to toRemove or removing from gameObjects
	 * @param g
	 */
	public static void destroy(GameObject g)
	{
		toRemove.offer(g);
	}
	
	/**
	 * Gets the window this game is hosted in
	 * @return
	 */
	public static GameWindow getWindow()
	{
		return window;
	}
	
	/**
	 * Retrieves a list of GameObjects that match a given tag
	 * @param tag The tag to search for
	 * @return an ArrayList of GameObjects currently in the game with a matching tag
	 */
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
	
	/**
	 * Gets whether or not the game is running.
	 * @return whether or not the game is running
	 */
	public static boolean isRunning()
	{
		return isRunning;
	}
}
