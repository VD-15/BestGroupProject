package Game;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Game
 * 
 * @author Jedd Morgan
 * @version 25/03/2019
 */
public class GameManager {

	private static Location[][] boardArray;
	private static Robot[] robots;

	public GameManager() {

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
		int i;    
		while((i=reader.read()) != -1) {
			switch ((char)i) {
			case '.': 
				//Normal Tile
				break;
			case '+':
				//Clockwise gear
				break;
			case '-': 
				//CClockwise gear
				break;
			case 'x':
				//Pit
				break;
			case 'v': 
				//South Belt
				break;
			case '>': 
				//East Belt
				break;
			case '<': 
				//West Belt
				break;
			case '^': 
				//North Belt
				break;
			default: 
				if (Character.isLetter((char)i)) {
					//Start tile
				}
				if (Character.isDigit((char)i)) {
					//Flag tile
				}
			}

			System.out.print((char)i);    
			reader.close();


		}
	}
}
