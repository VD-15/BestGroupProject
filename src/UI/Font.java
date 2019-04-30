package UI;

import graphics.Texture;
import utils.Point;
import utils.Region;
import utils.Vector2;

public class Font
{
	private Texture texture;
	private Point letterSize;
	
	public Font(Texture texture, Point letterSize)
	{
		this.texture = texture;
		this.letterSize = letterSize;
	}
	
	public Texture getTexture() { return texture; }
	public Point getLetterSize() {return letterSize; }
	
	public Vector2 getStringSize(String s)
	{
		return new Vector2(s.length() * letterSize.x, letterSize.y);
	}
	
	public Region getLetterRegion(char c)
	{
		int offset = (c - ' ');
		
		return new Region(offset * letterSize.x, 0, letterSize.x, letterSize.y);
	}
}
