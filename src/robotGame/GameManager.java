package robotGame;

import core.Game;
import core.GameObject;
import robotGame.tiles.BeltTile;
import robotGame.tiles.FlagTile;
import robotGame.tiles.GearTile;
import robotGame.tiles.PitTile;
import utils.LogSeverity;
import utils.Logger;
import utils.Vector2;

/**
 * This class has now been replaced by the Board Class
 * 
 * @author Jedd Morgan
 * @version 3/04/2019
 */

/*
public class GameManager
{
	private static Location[][] boardArray;
	private static Robot[] robots;
	
	private static final int TILE_SIZE = 64;
	private static final int ROBOT_COUNT = 4;
	
	public GameManager()
	{
		robots = new Robot[ROBOT_COUNT];
		boardArray = new Location[1][1];
		boardArray[0][0] = new NormalTile(new Vector2());
	}
	
	public static Location getLocation(int x, int y)
	{
		return boardArray[x][y];
	}
	
	public Location[][] getArray()
	{
		return boardArray;
	}
	
	public void init()
	{
		for (GameObject[] array : boardArray)
		{
			for (GameObject g : array)
			{
				Game.instantiate(g);
			}
		}
		
		for (GameObject g : robots)
		{
			Game.instantiate(g);
		}
	}
	
	/**
	 * Initializes the board from the given board data file
	 * 
	 * @param board an array of strings read from a .brd file
	 
	public void loadBoardFromPlainText(String[] board)
	{
		if (!board[0].startsWith("format "))
		{
			Logger.log(this, LogSeverity.ERROR, "Failed to read board data: Could not determine format.");
			return;
		}
		
		int format = -1;
		
		{
			// Split the string with format as the delimiter.
			// This will give us two strings, one being empty and another being the format.
			String[] formats = board[0].split("format ");
			
			if (formats[1].length() == 0)
			{
				Logger.log(this, LogSeverity.ERROR, "Failed to read board data: Could not determine format.");
				return;
			}
			
			format = Integer.valueOf(formats[1]);
		}
		
		switch (format)
		{
			case 1:
				generateBoardFormat1(board);
				break;
			default:
				Logger.log(this, LogSeverity.ERROR, "Invalid board format: {" + format + "}");
				break;
		}
	}
	
	private void generateBoardFormat1(String[] board)
	{
		if (board.length < 2)
		{
			Logger.log(this, LogSeverity.ERROR, "Board file was empty.");
			return;
		}
		
		int height = board.length;
		int width = board[1].length();
		boardArray = new Location[width][height - 1];
		
		for (int y = 0; y < height - 1; y++)
		{
			if (board[y + 1].length() != width)
			{
				Logger.log(this, LogSeverity.ERROR, "Board file has rows of inconsistent length!");
			}
			
			for (int x = 0; x < width; x++)
			{
				Location newTile;
				Vector2 pos = new Vector2(x * TILE_SIZE, y * TILE_SIZE);
				char c = board[y + 1].charAt(x);
				
				if (Character.isLetter(c))
				{
					// Start tile
					newTile = new NormalTile(pos);
					
					char robot = (char) (c - 'a');
					if (robot <= ROBOT_COUNT)
					{
						robots[robot] = new Robot(newTile, pos, x, y);
					}
				}
				else if (Character.isDigit(c))
				{
					// Flag tile
					newTile = new FlagTile(pos, (Character.getNumericValue(c)));
				}
				else
				{					
					switch (c)
					{
						case '.':
							// Normal Tile
							newTile = new NormalTile(pos);
							break;
						case '+':
							// Clockwise gear
							newTile = new GearTile(pos, true);
							break;
						case '-':
							// CClockwise gear
							newTile = new GearTile(pos, false);
							break;
						case 'x':
							// Pit
							newTile = new PitTile(pos);
							break;
						case 'v':
							// South Belt
							newTile = new BeltTile(pos, Direction.SOUTH);
							break;
						case '>':
							// East Belt
							newTile = new BeltTile(pos, Direction.EAST);
							break;
						case '<':
							// West Belt
							newTile = new BeltTile(pos, Direction.WEST);
							break;
						case '^':
							// North Belt
							newTile = new BeltTile(pos, Direction.NORTH);
							break;
						default:
							// Error tile
							Logger.log(this, LogSeverity.ERROR,	"Encountered invalid character when parsing board file: {" + c + "}");
							newTile = new NormalTile(pos);
					}
				}
				
				boardArray[x][y] = newTile;
			}
		}
	}
}
*/
