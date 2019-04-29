package UI;

import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.KeyListener;
import com.jogamp.newt.event.MouseEvent;
import com.jogamp.newt.event.MouseListener;

import core.Game;
import core.GameObject;

public class UIObject extends GameObject implements MouseListener, KeyListener
{
	public UIObject()
	{
		
	}
	
	@Override
	public void keyPressed(KeyEvent arg0)
	{

	}

	@Override
	public void keyReleased(KeyEvent arg0)
	{

	}

	@Override
	public void mouseClicked(MouseEvent arg0)
	{

	}

	@Override
	public void mouseDragged(MouseEvent arg0)
	{

	}

	@Override
	public void mouseEntered(MouseEvent arg0)
	{

	}

	@Override
	public void mouseExited(MouseEvent arg0)
	{

	}

	@Override
	public void mouseMoved(MouseEvent arg0)
	{

	}

	@Override
	public void mousePressed(MouseEvent arg0)
	{

	}

	@Override
	public void mouseReleased(MouseEvent arg0)
	{

	}

	@Override
	public void mouseWheelMoved(MouseEvent arg0)
	{

	}

	@Override
	public void init()
	{
		Game.getWindow().window.addKeyListener(this);
		Game.getWindow().window.addMouseListener(this);
	}

	@Override
	public void destroy()
	{
		Game.getWindow().window.removeKeyListener(this);
		Game.getWindow().window.removeMouseListener(this);
	}
	
}
