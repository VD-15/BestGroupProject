package robotGame;

import java.util.ArrayList;

import core.ContentManager;
import core.Game;
import core.GameObject;
import robotGame.tiles.*;
import utils.LogSeverity;
import utils.Logger;
import utils.Point;

/**
 * Board Generates Board Tiles
 * 
 * @author Jedd Morgan
 * @author Owen Craig
 */
public class Board extends GameObject
{
	/** Name of boardFile to be loaded */
	private final String boardFile;

	private static BoardTile[][] boardArray;

	/** Array of starting points for robots */
	private ArrayList<Point> startingLocations;

	/**
	 * Creates a new board
	 * 
	 * @param boardFile the name of the board file that is to be loaded
	 * 
	 */
	public Board(String boardFile)
	{
		super();
		this.tag = "board";
		this.boardFile = boardFile;

		startingLocations = new ArrayList<Point>();
		loadBoardFromText(ContentManager.getTextByName(boardFile));
	}

	/** Creates a default testBoard */
	public Board()
	{
		this("testBoard3");
	}

	@Override
	public void init()
	{

		for (GameObject o : getBoardTiles())
		{
			Game.instantiate(o);
		}
	}

	public static BoardTile getTile(Point p)
	{
		if(p.x >= boardArray.length || p.x < 0) {
			return null;
		}
		if(p.y >= boardArray[p.x].length || p.y < 0) {
			return null;
		}

		return boardArray[p.x][p.y];
	}

	/**
	 * Attempts to load board from text. Checks header to determine format
	 * 
	 * @param text contents of a board text file
	 */
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

	/**
	 * Generates boardArray and startingLocations from loaded board file using
	 * format1
	 * 
	 * @param text contents of a format1 text file
	 */
	private void loadBoardFormat1(String[] text)
	{

		// Validates the number of rows
		if (text.length < 2)
		{
			Logger.log(this, LogSeverity.ERROR, "Board data has no rows!");
			return;
		}

		int height = text.length - 1;
		int width = text[1].length();

		boardArray = new BoardTile[width][height];
		Point[] startingLocations = new Point[4];

		// Loops through each row
		for (int y = 0; y < height; y++)
		{
			// Checks for valid length
			if (text[y + 1].length() != width)
			{
				Logger.log(this, LogSeverity.WARNING, "Board data has inconsistent row lengths. Problems may occur");
			}

			// Loops through each char
			for (int x = 0; x < text[y + 1].length(); x++)
			{
				// For each char in text

				char c = text[y + 1].charAt(x);
				Point p = new Point(x, y);

				switch (c)
				{
				case '.':
					// Normal Tile
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
					//Commented out because I want only one robot for testing
				case 'A':
				case 'B':
				case 'C':
				case 'D':
					// Robot
					startingLocations[c - 'A'] = p;
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
				case '[':
					// WestCC Belt
					boardArray[p.x][p.y] = new LaserEmitter(p, Direction.EAST);
					break;
				case ']':
					// WestCC Belt
					boardArray[p.x][p.y] = new LaserReceiver(p, Direction.WEST);
					break;
				case '(':
					// WestCC Belt
					boardArray[p.x][p.y] = new LaserEmitter(p, Direction.SOUTH);
					break;
				case ')':
					// WestCC Belt
					boardArray[p.x][p.y] = new LaserReceiver(p, Direction.NORTH);
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
					boardArray[p.x][p.y] = new BeltTile(p, Direction.NORTH, 1);
					break;
				case 'E':
					// EastC Belt
					boardArray[p.x][p.y] = new BeltTile(p, Direction.EAST, 1);
					break;
				case 'S':
					// SouthC Belt
					boardArray[p.x][p.y] = new BeltTile(p, Direction.SOUTH, 1);
					break;
				case 'W':
					// WestC Belt
					boardArray[p.x][p.y] = new BeltTile(p, Direction.WEST, 1);
					break;
				case 'n':
					// NorthCC Belt
					boardArray[p.x][p.y] = new BeltTile(p, Direction.NORTH, -1);
					break;
				case 'e':
					// EastCC Belt
					boardArray[p.x][p.y] = new BeltTile(p, Direction.EAST, -1);
					break;
				case 's':
					// SouthCC Belt
					boardArray[p.x][p.y] = new BeltTile(p, Direction.SOUTH, -1);
					break;
				case 'w':
					// WestCC Belt
					boardArray[p.x][p.y] = new BeltTile(p, Direction.WEST, -1);
					break;
				default:
					// Unknown tile
					boardArray[p.x][p.y] = new BoardTile(p);
					Logger.log(this, LogSeverity.ERROR, "Encountered an invalid character while reading board file: {" + c + "}");
					break;
				}
			}
		}

		for (Point p : startingLocations)
		{
			if (p != null)
			{
				this.startingLocations.add(p);

				// TEMP!!
				// Game.instantiate(new Robot(p, 1));
			}
		}

	}

	/**
	 * 
	 * @return returns a ArrayList of all BoardTiles in boardArray
	 */
	public ArrayList<BoardTile> getBoardTiles()
	{

		ArrayList<BoardTile> tiles = new ArrayList<BoardTile>();
		if (boardArray != null) {
			for (BoardTile[] a : boardArray)
			{
				for (BoardTile t : a)
				{
					if (t != null) tiles.add(t);
				}
			}
		}

		return tiles;
	}

	public ArrayList<Point> getStartingLocations()
	{
		return startingLocations;
	}

	@Override
	public void destroy()
	{
		// TODO Auto-generated method stub

	}
}
