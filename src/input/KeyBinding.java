package input;

/**
 * Facilitates binding up to two keys to a given input string.
 * @author Vee
 */
public class KeyBinding
{
	/**
	 * The primary keycode that matches this KeyBinding
	 */
	public int primaryCode;
	
	/**
	 * The secondary keycode that matches this KeyBinding
	 */
	public int secondaryCode;
	
	/**
	 * whether or not the primary key is pressed
	 */
	public boolean primaryDown;
	
	/**
	 * whether or not the secondary key is pressed
	 */
	public boolean secondaryDown;
	
	/**
	 * Creates a KeyBinding
	 * @param primaryCode The primary key code to listen for 
	 */
	public KeyBinding(int primaryCode)
	{
		this.primaryCode = primaryCode;
		this.secondaryCode = -1;
	}
	
	/**
	 * Creates a KeyBinding
	 * @param primaryCode The primary key code to listen for
	 * @param secondaryCode The secondary key code to listen for
	 */
	public KeyBinding(int primaryCode, int secondaryCode)
	{
		this.primaryCode = primaryCode;
		this.secondaryCode = secondaryCode;
	}
	
	/**
	 * Check if this keybinding is active
	 * @return
	 */
	public Boolean isDown()
	{
		return primaryDown || secondaryDown;
	}
	
	/**
	 * To be called whenever a key is pressed
	 * @param s the key code that was pressed
	 */
	public void keyPressed(int s)
	{		
		if (s == primaryCode)
		{
			primaryDown = true;
		}
		else if (s == secondaryCode)
		{
			secondaryDown = true;
		}
	}

	/**
	 * To be called whenever a key is released
	 * @param s the key code that was released
	 */
	public void keyReleased(int s)
	{
		if (s == primaryCode)
		{
			primaryDown = false;
		}
		else if (s == secondaryCode)
		{
			secondaryDown = false;
		}
	}
}
