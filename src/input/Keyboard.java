package input;

import java.util.HashMap;

import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.KeyListener;

import utils.LogSeverity;
import utils.Logger;

/**
 * Represents a virtual keyboard and facilitates binding of keys 
 * @author Vee
 */
public class Keyboard implements KeyListener
{	
	/**
	 * Stores all KeyBindings this keyboard is listening for
	 */
	private HashMap<String, KeyBinding> keyBindings;
	
	/**
	 * Creates a Keyboard
	 */
	public Keyboard()
	{
		keyBindings = new HashMap<String, KeyBinding>();
	}
	
	/**
	 * Adds a KeyBinding for this keyboard to listen to
	 * @param name Name of the KeyBinding to add
	 * @param b the KeyBinding to listen for
	 */
	public void addBinding(String name, KeyBinding b)
	{
		keyBindings.put(name, b);
	}
	
	/**
	 * Removes a KeyBinding from this Keyboard
	 * @param name the name of the KeyBinding
	 */
	public void removeBinding(String name)
	{
		keyBindings.remove(name);
	}
	
	/**
	 * Checks if a KeyBinding is active or not
	 * @param bindingName the name of the KeyBinding
	 * @return whether the KeyBinding is active(pressed) or not
	 */
	public Boolean isKeyDown(String bindingName)
	{
		if (keyBindings.containsKey(bindingName))
		{
			return keyBindings.get(bindingName).isDown();
		}
		else
		{
			Logger.log(this, LogSeverity.ERROR, "No keybinding found for {" + bindingName + "}");
			return false;
		}
	}
	
	@Override
	public void keyPressed(KeyEvent e)
	{
		if (e.isAutoRepeat()) return;
		
		Logger.log(this, LogSeverity.INFO, "Key pressed:\t{" + e.getKeyChar() + "} " +  e.getKeyCode());
		
		for (KeyBinding l : keyBindings.values())
		{
			l.keyPressed(e.getKeyCode());
		}
	}

	@Override
	public void keyReleased(KeyEvent e)
	{
		if (e.isAutoRepeat()) return;
		
		Logger.log(this, LogSeverity.VERBOSE, "Key released:\t{" + e.getKeyChar() + "} " + e.getKeyCode());
		
		for (KeyBinding l : keyBindings.values())
		{
			l.keyReleased(e.getKeyCode());
		}
	}
}
