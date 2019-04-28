package input;

import java.util.HashMap;

import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.KeyListener;

import utils.LogSeverity;
import utils.Logger;

public class Keyboard implements KeyListener
{	
	private HashMap<String, KeyBinding> keyBindings;
	
	public Keyboard()
	{
		keyBindings = new HashMap<String, KeyBinding>();
	}
	
	public void addBinding(String name, KeyBinding b)
	{
		keyBindings.put(name, b);
	}
	
	public void removeBinding(String name)
	{
		keyBindings.remove(name);
	}
	
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
