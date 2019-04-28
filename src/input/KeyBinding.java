package input;

import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.KeyListener;

import utils.LogSeverity;
import utils.Logger;

public class KeyBinding
{
	public int primaryCode;
	public int secondaryCode;
	public boolean primaryDown;
	public boolean secondaryDown;
	
	public KeyBinding(int primaryCode)
	{
		this.primaryCode = primaryCode;
		this.secondaryCode = -1;
	}
	
	public KeyBinding(int primaryCode, int secondaryCode)
	{
		this.primaryCode = primaryCode;
		this.secondaryCode = secondaryCode;
	}
	
	public Boolean isDown()
	{
		return primaryDown || secondaryDown;
	}

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
