package robotGame;

import core.ContentManager;
import core.Game;
import core.GameObject;
import core.IUpdatable;
import robotGame.tiles.*;
import utils.Direction;
import utils.LogSeverity;
import utils.Logger;
import utils.Point;

public class Board extends GameObject implements IUpdatable
{
	private int width;
	private int height;
	
	public Board()
	{
		super();
		this.tag = "board";
		this.width = 0;
		this.height = 0;
	}
	
	@Override
	public void init()
	{
		loadBoardFromText(ContentManager.getTextByName("testBoard"));
	}
	
	public int getWidth()
	{
		return width;
	}
	
	public int getHeight()
	{
		return height;
	}
	
	private void loadBoardFromText(String[] text)
	{
		if (!text[0].startsWith("format "))
		{
			Logger.log(this, LogSeverity.ERROR, "Could not discern format from board data.");
			return;
		}
		
		int formatVersion = Integer.valueOf(text[0].split("format ")[1]);
		
		switch (formatVersion)
		{
			case 1:
				loadBoardFormat1(text);
				return;
			default:
				Logger.log(this, LogSeverity.ERROR, "Board data had invalid version: {" + formatVersion + "}");
		}
	}
	
	private void loadBoardFormat1(String[] text)
	{
		if (text.length < 2)
		{
			Logger.log(this, LogSeverity.ERROR, "Board data has no rows!");
			return;
		}
		
		int height = text.length - 1;
		int width = text[1].length();
		
		for (int y = 0; y < height; y++)
		{
			if (text[y + 1].length() != width)
			{
				Logger.log(this, LogSeverity.WARNING, "Board data has inconsistent row lengths. Reading further may fail.");
			}
			
			for (int x = 0; x < width; x++)
			{
				char c = text[y + 1].charAt(x);
				Point p = new Point(x, y);
				
				switch (c)
				{
					case '.':
						//Normal Tile
						Game.instantiate(new BoardTile(new Point(x, y)));
						break;
					case '0':
					case '1':
					case '2':
					case '3':
					case '4':
						// Flag
						Game.instantiate(new FlagTile(p, Integer.valueOf(c - '0')));
						break;
					case 'a':
					case 'b':
					case 'c':
					case 'd':
						// Robot
						Game.instantiate(new Robot(p, Integer.valueOf(c - 'a')));
						Game.instantiate(new BoardTile(new Point(x, y)));
						break;
					case '-':
						// Counter-Clockwise Gear Tile
						Game.instantiate(new GearTile(p, false));
						break;
					case '+':
						// Clockwise Gear Tile
						Game.instantiate(new GearTile(p, true));
						break;
					case 'x':
						// Pit Tile
						Game.instantiate(new PitTile(p));
						break;
					case 'v':
						// South Belt
						Game.instantiate(new BeltTile(p, Direction.SOUTH));
						break;
					case '>':
						// East Belt
						Game.instantiate(new BeltTile(p, Direction.EAST));
						break;
					case '<':
						// West Belt
						Game.instantiate(new BeltTile(p, Direction.WEST));
						break;
					case '^':
						// North Belt
						Game.instantiate(new BeltTile(p, Direction.NORTH));
						break;
					default:
						// Unknown tile
						Logger.log(this, LogSeverity.ERROR, "Encountered an invalid character while reading board file: {" + c + "}");
						break;
				}
			}
		}
	}
	
	@Override
	public void update(double time)
	{
		
	}
}
