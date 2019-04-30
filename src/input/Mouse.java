package input;

import com.jogamp.newt.event.MouseEvent;
import com.jogamp.newt.event.MouseListener;

import utils.LogSeverity;
import utils.Logger;
import utils.Point;

/**
 * Represents a virtual mouse that tracks the position of the cursor and any pressed buttons
 * @author Vee
 */
public class Mouse implements MouseListener
{
	/**
	 * Whether the left mouse button is pressed
	 */
	private boolean leftButtonDown;
	
	/**
	 * Whether the right mouse button is pressed
	 */
	private boolean rightButtonDown;
	
	/**
	 * Whether the middle mouse button is pressed, normally the scroll wheel, but is independent of scrolling
	 */
	private boolean middleButtonDown;
	
	/**
	 * The position of the mouse cursor
	 */
	private Point mousePos;
	
	/**
	 * How many scroll units the mouse has scrolled since this object was created
	 */
	private float scrollDelta;
	
	/**
	 * Creates a Mouse
	 */
	public Mouse()
	{
		leftButtonDown = false;
		rightButtonDown = false;
		middleButtonDown = false;
		mousePos = new Point();
		scrollDelta = 0f;
	}
	
	/**
	 * Get whether the left mouse button is pressed
	 * @return Whether the left mouse button is pressed
	 */
	public boolean isLeftButtonDown() { return leftButtonDown; }
	
	/**
	 * Get whether the right mouse button is pressed
	 * @return Whether the right mouse button is pressed
	 */
	public boolean isRightButtonDown() { return rightButtonDown; }
	
	/**
	 * Get whether the middle mouse button is pressed, normally the scroll wheel, but is independent of scrolling
	 * @return Whether the middle mouse button is pressed
	 */
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
		mousePos = new Point(e.getX(), e.getY());
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
