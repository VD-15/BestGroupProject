package input;

import com.jogamp.newt.event.MouseEvent;
import com.jogamp.newt.event.MouseListener;

import utils.LogSeverity;
import utils.Logger;
import utils.Point;

public class Mouse implements MouseListener
{
	private boolean leftButtonDown;
	private boolean rightButtonDown;
	private boolean middleButtonDown;
	private Point mousePos;
	private float scrollDelta;
	
	public Mouse()
	{
		leftButtonDown = false;
		rightButtonDown = false;
		middleButtonDown = false;
		mousePos = new Point();
		scrollDelta = 0f;
	}
	
	public boolean isLeftButtonDown() { return leftButtonDown; }
	public boolean isRightButtonDown() { return rightButtonDown; }
	public boolean isMiddleButtonDown() { return middleButtonDown; }
	
	public Point getMousePos() { return mousePos; }
	public float getScrollDelta() { return scrollDelta; }
	
	@Override
	public void mouseClicked(MouseEvent e)
	{
		
	}

	@Override
	public void mouseDragged(MouseEvent e)
	{
		
	}

	@Override
	public void mouseEntered(MouseEvent e)
	{
		
	}

	@Override
	public void mouseExited(MouseEvent e)
	{

	}

	@Override
	public void mouseMoved(MouseEvent e)
	{
		mousePos = new Point(e.getX(), e.getY());
	}

	@Override
	public void mousePressed(MouseEvent e)
	{
		switch (e.getButton())
		{
			case MouseEvent.BUTTON1:
				leftButtonDown = true;
				Logger.log(this, LogSeverity.INFO, "Left mouse button down");
				break;
			case MouseEvent.BUTTON2:
				middleButtonDown = true;
				Logger.log(this, LogSeverity.INFO, "Middle mouse button down");
				break;
			case MouseEvent.BUTTON3:
				rightButtonDown = true;
				Logger.log(this, LogSeverity.INFO, "Right mouse button down");
				break;
		}
	}

	@Override
	public void mouseReleased(MouseEvent e)
	{
		switch (e.getButton())
		{
			case MouseEvent.BUTTON1:
				leftButtonDown = false;
				break;
			case MouseEvent.BUTTON2:
				rightButtonDown = false;
				break;
			case MouseEvent.BUTTON3:
				middleButtonDown = false;
				break;
				
		}
	}

	@Override
	public void mouseWheelMoved(MouseEvent e)
	{
		scrollDelta += e.getRotation()[1] * e.getRotationScale();
	}
	
}
