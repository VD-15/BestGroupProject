package UI;

import com.jogamp.newt.event.MouseEvent;

import core.ContentManager;
import graphics.Color;
import graphics.IDrawable;
import graphics.RenderBatch;
import graphics.RenderInstance;
import graphics.StringRenderInstance;
import utils.LogSeverity;
import utils.Logger;
import utils.Point;
import utils.Region;
import utils.Vector2;

public class Button extends UIObject implements IDrawable
{
	private static float BUTTON_HEIGHT = 64f;
	private static float BUTTON_MIN_WIDTH = 30f;
	private float width;
	protected boolean isActive;
	protected boolean isEnabled;
	private boolean isClicked;
	private String text;
	protected Color highlight;
	
	public Button()
	{
		super();
		this.width = 200;
		this.isActive = false;
		this.isEnabled = true;
		this.text = "";
		this.highlight = Color.WHITE();
	}
	
	protected void onClick()
	{
		
	}
	
	protected void setText(String text)
	{
		this.text = text;
		this.setWidth(ContentManager.getFontByName("fontSmall").getStringSize(text).x + 20);
	}
	
	protected void setWidth(float w)
	{
		if (w < BUTTON_MIN_WIDTH)
		{
			Logger.log(this, LogSeverity.ERROR, "Button width must be greater than or equal to " + BUTTON_MIN_WIDTH + "!");
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
			if (this.isActive && this.isEnabled)
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
		this.highlight = Color.WHITE();
		
		if (this.isEnabled)
		{
			if (this.isActive)
			{
				if (isClicked)
				{
					this.highlight = new Color(0.5f, 0.5f, 0.5f);
				}
				else
				{
					this.highlight = new Color(1.5f, 1.5f, 1.5f);
				}
			}
		}
		else
		{
			this.highlight = new Color(0.75f, 0.125f, 0.125f);
		}
		
		b.draw(new RenderInstance()
				.withTexture("buttonEdge")
				.withLayer(2)
				.withDepth(16f)
				.withColor(this.highlight)
				.withDestinationRegion(
						new Region(
								this.position.add(new Vector2(0, 0)), 
								new Vector2(15, BUTTON_HEIGHT), 
								false)
						)
				.build()
				);
		
		b.draw(new RenderInstance()
				.withTexture("buttonCenter")
				.withLayer(2)
				.withDepth(16f)
				.withColor(this.highlight)
				.withDestinationRegion(
						new Region(
								this.position.add(new Vector2(15, 0)), 
								new Vector2(width - 30, BUTTON_HEIGHT), 
								false)
						)
				.build()
				);
		
		b.draw(new RenderInstance()
				.withTexture("buttonEdge")
				.withLayer(2)
				.withDepth(16f)
				.withColor(this.highlight)
				.withDestinationRegion(
						new Region(
								this.position.add(new Vector2(width, 0)), 
								new Vector2(-15, BUTTON_HEIGHT), 
								false)
						)
				.build()
				);
				
		b.drawString(new StringRenderInstance()
				.withFont("fontSmall")
				.withText(this.text)
				.withColor(this.highlight)
				.withLocation(this.position.add(new Vector2(10, 7)))
				.build()
				);
	}
}
