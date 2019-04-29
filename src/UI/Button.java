package UI;

import com.jogamp.newt.event.MouseEvent;

import graphics.Color;
import graphics.IDrawable;
import graphics.RenderBatch;
import graphics.RenderInstance;
import utils.LogSeverity;
import utils.Logger;
import utils.Point;
import utils.Region;
import utils.Vector2;

public class Button extends UIObject implements IDrawable
{
	private static float BUTTON_HEIGHT = 64;
	private float width;
	private boolean isActive;
	private boolean isClicked;
	
	public Button()
	{
		super();
		this.width = 200;
		this.isActive = false;
	}
	
	protected void onClick()
	{
		
	}
	
	protected void setWidth(float w)
	{
		if (w < 30f)
		{
			Logger.log(this, LogSeverity.ERROR, "Button width must be greater than or equal to 30!");
		}
		else
		{
			width = w;
		}
	}
	
	@Override
	public void mouseMoved(MouseEvent e)
	{
		Point p = new Point(e.getX(), e.getY());
		
		this.isActive = new Region(this.position, new Vector2(this.width, BUTTON_HEIGHT), false).containsPoint(p);
	}
	
	@Override
	public void mousePressed(MouseEvent e)
	{
		if (e.getButton() == MouseEvent.BUTTON1)
		{
			if (this.isActive)
			{
				this.isClicked = true;
				this.onClick();
			}
		}
	}
	
	@Override
	public void mouseReleased(MouseEvent e)
	{
		if (e.getButton() == MouseEvent.BUTTON1)
		{
			this.isClicked = false;
		}
	}
	
	@Override
	public void draw(RenderBatch b)
	{
		Color highlight = Color.WHITE();
		
		if (isActive)
		{
			if (isClicked)
			{
				highlight = new Color(0.5f, 0.5f, 0.5f);
			}
			else
			{
				highlight = new Color(1.5f, 1.5f, 1.5f);
			}
		}
		
		b.draw(new RenderInstance()
				.withTexture("buttonEdge")
				.withLayer(2)
				.withDepth(16f)
				.withColor(highlight)
				.withDestinationRegion(
						new Region(
								this.position.add(new Vector2(0, 0)), 
								new Vector2(15, BUTTON_HEIGHT), 
								false)
						)
				);
		
		b.draw(new RenderInstance()
				.withTexture("buttonCenter")
				.withLayer(2)
				.withDepth(16f)
				.withColor(highlight)
				.withDestinationRegion(
						new Region(
								this.position.add(new Vector2(15, 0)), 
								new Vector2(width - 30, BUTTON_HEIGHT), 
								false)
						)
				);
		
		b.draw(new RenderInstance()
				.withTexture("buttonEdge")
				.withLayer(2).withDepth(16f)
				.withColor(highlight)
				.withDestinationRegion(
						new Region(
								this.position.add(new Vector2(width, 0)), 
								new Vector2(-15, BUTTON_HEIGHT), 
								false)
						)
				);
	}
}
