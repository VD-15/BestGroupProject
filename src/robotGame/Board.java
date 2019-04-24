package robotGame;

import java.util.ArrayList;
import java.util.LinkedList;

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
	
	private static BoardTile[][] boardArray;
	private static Robot[] robots;
	
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
	
	public static BoardTile getTile(Point p) {
		//FIXME bounds
		return boardArray[p.x][p.y];
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
		
		boardArray = new BoardTile[width][height];
		ArrayList<Robot> robotList = new ArrayList<Robot>();
		
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
						boardArray[p.x][p.y] = new BoardTile(p);
						break;
					case '0':
					case '1':
					case '2':
					case '3':
					case '4':
						// Flag
						boardArray[p.x][p.y] = new FlagTile(p, Integer.valueOf(c - '0'));
						break;
					case 'a':
					case 'b':
					case 'c':
					case 'd':
						// Robot
						robotList.add(new Robot(p, Integer.valueOf(c - 'a')));
						boardArray[p.x][p.y] = new BoardTile(p);
						break;
					case '-':
						// Counter-Clockwise Gear Tile
						boardArray[p.x][p.y] = new GearTile(p, false);
						break;
					case '+':
						// Clockwise Gear Tile
						boardArray[p.x][p.y] = new GearTile(p, true);
						break;
					case 'x':
						// Pit Tile
						boardArray[p.x][p.y] = new PitTile(p);
						break;
					case '^':
						// West Belt
						boardArray[p.x][p.y] = new BeltTile(p, Direction.NORTH, 0);
						break;
					case 'v':
						// South Belt
						boardArray[p.x][p.y] = new BeltTile(p, Direction.SOUTH, 0);
						break;
					case '>':
						// East Belt
						boardArray[p.x][p.y] = new BeltTile(p, Direction.EAST, 0);
						break;
					case '<':
						// West Belt
						boardArray[p.x][p.y] = new BeltTile(p, Direction.WEST, 0);
						break;
					case 'N':
						// NorthC Belt
						boardArray[p.x][p.y] = new BeltTile(p, Direction.NORTH, -1);
						break;
					case 'E':
						// EastC Belt
						boardArray[p.x][p.y] = new BeltTile(p, Direction.EAST, -1);
						break;
					case 'S':
						// SouthC Belt
						boardArray[p.x][p.y] = new BeltTile(p, Direction.SOUTH, -1);
						break;
					case 'W':
						// WestC Belt
						boardArray[p.x][p.y] = new BeltTile(p, Direction.WEST, -1);
						break;
					case 'n':
						// NorthCC Belt
						boardArray[p.x][p.y] = new BeltTile(p, Direction.NORTH, 1);
						break;
					case 'e':
						// EastCC Belt
						boardArray[p.x][p.y] = new BeltTile(p, Direction.EAST, 1);
						break;
					case 's':
						// SouthCC Belt
						boardArray[p.x][p.y] = new BeltTile(p, Direction.SOUTH, 1);
						break;
					case 'w':
						// WestCC Belt
						boardArray[p.x][p.y] = new BeltTile(p, Direction.WEST, 1);
						break;
					default:
						// Unknown tile
						Logger.log(this, LogSeverity.ERROR, "Encountered an invalid character while reading board file: {" + c + "}");
						break;
				}
			}
		}
		
		
		robots = new Robot[robotList.size()];
		int i = 0;
		for(Robot r : robotList) {
			robots[i] = r;
			i++;
		}
		
		
		for (GameObject[] a : boardArray)
			for (GameObject o : a) {
				Game.instantiate(o);
			}
		
		for (GameObject o : robots) {
			Game.instantiate(o);
		}
	}
	
	@Override
	public void update(double time)
	{
		
	}
}
