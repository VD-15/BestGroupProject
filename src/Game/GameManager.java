package Game;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import Utils.LogSeverity;
import Utils.Logger;
import Utils.Vector2;

/**
 * Game
 * 
 * @author Jedd Morgan
 * @version 3/04/2019
 */
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
	
	/**
	 * generates boardArray
	 * 
	 * @throws Exception if the board contains invalid characters
	 */
	public void generateBoardFromPlainText(String[] board)
	{
		if (!board[0].startsWith("format "))
		{
			Logger.log(this, LogSeverity.ERROR, "Failed to read board data: Could not determine format.");
			return;
		}
		
		int format = Integer.valueOf(board[0].split("format ")[0]);
		Logger.log(this, LogSeverity.INFO, String.valueOf(format));
		return;
		FileReader reader = new FileReader(file);
		int i, x = 0, y = 0;
		
		Vector2 origin = new Vector2(TILE_SIZE / 1, TILE_SIZE / 1);
		
		while ((i = reader.read()) != -1)
		{
			Location newTile = null;
			Vector2 pos = new Vector2(x * TILE_SIZE, y * TILE_SIZE).add(origin);
			
			switch ((char) i)
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
				case '\n':
					// End of line
					x = 0;
					y++;
					continue;
				default:
					if (Character.isLetter((char) i))
					{
						// Start tile
						newTile = new NormalTile(pos);
						
						// FIXME Not the best implementation using NUMBER_OF_ROBOTS
						if (((char) i - 'a') <= NUMBER_OF_ROBOTS)
						{
							robots[(char) i - 'a'] = new Robot(newTile, pos, x, y);
						}
					}
					else if (Character.isDigit((char) i))
					{
						// Flag tile
						newTile = new FlagTile(pos, (Character.getNumericValue((char) i)));
					}
					else
					{
						// Error tile
						Logger.log(this, LogSeverity.ERROR,	"Encountered invalid character when parsing board file: '" + (char)i + "'");
						throw new IOException("Encountered invalid character when parsing board file: " + (char)i);
					}
					
			}
			boardArray[x][y] = newTile;
			x++;
			
		}
		
		reader.close();
	}
}
