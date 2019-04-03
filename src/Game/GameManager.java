package Game;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import Utils.Vector2;

/**
 * Game
 * 
 * @author Jedd Morgan
 * @version 3/04/2019
 */
public class GameManager {

	private static Location[][] boardArray;
	private static Robot[] robots;
	
	private static final int TILE_SIZE = 64;
	private static final int NUMBER_OF_ROBOTS = 4;
	
	public GameManager() {
		robots = new Robot[NUMBER_OF_ROBOTS];
		//FIXME
		boardArray = new Location[9][5];
	}

	public static Location getLocation(int x, int y) {
		//TODO check that x and y are with in bounds of array
		return boardArray[x][y];
	}
	
	public Location[][] getArray() {
		return boardArray;
	}
	
	
	/**
	 * generates boardArray
	 * @throws IOException 
	 */
	public void generateBoard(File file) throws IOException {
		
		//TODO read first line to get format
		//TODO work out how errors are handled (exception?)
		
		FileReader reader = new FileReader(file);    
		int i, x = 0 , y = 0;
		
		Vector2 origin = new Vector2(TILE_SIZE / 1,TILE_SIZE / 1);
		
		while((i=reader.read()) != -1) {
			Location newTile = null;
			
			Vector2 pos = new Vector2(x * TILE_SIZE, y * TILE_SIZE).add(origin);
			switch ((char)i) {
			case '.': 
				//Normal Tile
				newTile = new NormalTile(pos);
				break;
			case '+':
				//Clockwise gear
				newTile = new GearTile(pos, true);
				break;
			case '-': 
				//CClockwise gear
				newTile = new GearTile(pos, false);
				break;
			case 'x':
				//Pit
				newTile = new PitTile(pos);
				break;
			case 'v': 
				//South Belt
				newTile = new BeltTile(pos, Direction.South);
				break;
			case '>': 
				//East Belt
				newTile = new BeltTile(pos, Direction.East);
				break;
			case '<': 
				//West Belt
				newTile = new BeltTile(pos, Direction.West);
				break;
			case '^': 
				//North Belt
				newTile = new BeltTile(pos, Direction.North);
				break;
			case '\n': 
				//End of line
				x = 0;
				y++;
				continue;
			default: 
				if (Character.isLetter((char)i)) {
					//Start tile
					newTile = new NormalTile(pos);
					
					//FIXME Not the best implementation using NUMBER_OF_ROBOTS
					if (((char)i - 'a') <= NUMBER_OF_ROBOTS) {
						robots[(char)i - 'a'] = new Robot(newTile);
					}
					
					
				}
				else if (Character.isDigit((char)i)) {
					//Flag tile
					newTile = new FlagTile(pos, (Character.getNumericValue((char)i)));
				} else {
					//Error tile
					//TODO decide on how to handle error tiles
					System.out.println("Reeee");
				}
				
			}
			boardArray[x][y] = newTile;
			x++;
			
		}
		
		reader.close();
	}
}
