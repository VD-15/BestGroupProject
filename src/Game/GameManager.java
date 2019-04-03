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
 * @version 25/03/2019
 */
public class GameManager {

	private static Location[][] boardArray;
	private static Robot[] robots;
	
	private static final int TILE_SIZE = 30;
	private static final int NUMBER_OF_ROBOTS = 4;
	
	public GameManager() {
		robots = new Robot[NUMBER_OF_ROBOTS];
	}

	public static Location getLocation(int x, int y) {
		//TODO check that x and y are with in bounds of array
		return boardArray[x][y];
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
		
		Vector2 origin = new Vector2(0,0);
		
		while((i=reader.read()) != -1) {
			x++;
			Location newTile;
			
			switch ((char)i) {
			case '.': 
				//Normal Tile
				newTile = new NormalTile(new Vector2(x * TILE_SIZE, y * TILE_SIZE).add(origin));
				break;
			case '+':
				//Clockwise gear
				newTile = new GearTile(new Vector2(x * TILE_SIZE, y * TILE_SIZE).add(origin), true);
				break;
			case '-': 
				//CClockwise gear
				newTile = new GearTile(new Vector2(x * TILE_SIZE, y * TILE_SIZE).add(origin), false);
				break;
			case 'x':
				//Pit
				newTile = new PitTile(new Vector2(x * TILE_SIZE, y * TILE_SIZE).add(origin));
				break;
			case 'v': 
				//South Belt
				newTile = new BeltTile(new Vector2(x * TILE_SIZE, y * TILE_SIZE).add(origin), Direction.South);
				break;
			case '>': 
				//East Belt
				newTile = new BeltTile(new Vector2(x * TILE_SIZE, y * TILE_SIZE).add(origin), Direction.East);
				break;
			case '<': 
				//West Belt
				newTile = new BeltTile(new Vector2(x * TILE_SIZE, y * TILE_SIZE).add(origin), Direction.West);
				break;
			case '^': 
				//North Belt
				newTile = new BeltTile(new Vector2(x * TILE_SIZE, y * TILE_SIZE).add(origin), Direction.North);
				break;
			case '\n': 
				//End of line
				x = 0;
				y++;
				break;
			default: 
				if (Character.isLetter((char)i)) {
					//Start tile
					newTile = new NormalTile(new Vector2(x * TILE_SIZE, y * TILE_SIZE).add(origin));
					
					//FIXME Not the best implementation using NUMBER_OF_ROBOTS
					if (((char)i - 'a') <= NUMBER_OF_ROBOTS) {
						robots[(char)i - 'a'] = new Robot(newTile);
					}
					
					
				}
				else if (Character.isDigit((char)i)) {
					//Flag tile
				} else {
					//Error tile
					
				}
			}

			System.out.print((char)i);    
			reader.close();


		}
	}
}
