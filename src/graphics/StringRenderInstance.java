package graphics;

import java.util.ArrayList;

import UI.Font;
import core.ContentManager;
import utils.LogSeverity;
import utils.Logger;
import utils.Region;
import utils.Vector2;

public class StringRenderInstance
{
	public String fontName;
	public String text;
	public Vector2 location;
	public Color color;
	
	public StringRenderInstance()
	{
		this.fontName = null;
		this.text = null;
		this.location = null;
		this.color = null;
	}
	
	public StringRenderInstance withFont(String fontName)
	{
		this.fontName = fontName;
		return this;
	}
	
	public StringRenderInstance withText(String text)
	{
		this.text = text;
		return this;
	}
	
	public StringRenderInstance withLocation(Vector2 location)
	{
		this.location = location.clone();
		return this;
	}
	
	public StringRenderInstance withColor(Color color)
	{
		this.color = color.clone();
		return this;
	}
	
	public StringRenderInstance build()
	{
		if (this.fontName == null)
		{
			Logger.log(this, LogSeverity.ERROR, "StringRenderInstance font was null. You actually NEED to supply this.");
			this.fontName = "";
		}
		
		if (this.text == null)
		{
			Logger.log(this, LogSeverity.WARNING, "StringRenderInstance text null. This won't draw anything.");
			this.text = "";
		}
		
		if (this.location == null)
		{
			this.location = new Vector2();
		}
		
		if (this.color == null)
		{
			this.color = Color.WHITE();
		}

		return this;
	}
	
	public ArrayList<RenderInstance> toRenderInstanceArray()
	{
		ArrayList<RenderInstance> instances = new ArrayList<RenderInstance>();
		Font f = ContentManager.getFontByName(this.fontName);
		
		for (int i = 0; i < this.text.length(); i++)
		{
			instances.add(new RenderInstance()
					.withTexture(this.fontName)
					.withDestinationRegion(new Region(i * f.getLetterSize().x + this.location.x, this.location.y, f.getLetterSize().x, f.getLetterSize().y))
					.withSourceRegion(f.getLetterRegion(this.text.charAt(i)))
					.withColor(this.color)
					.withDepth(32f)
					.withLayer(2)
					.build()
					);
		}
		
		return instances;
	}
}
