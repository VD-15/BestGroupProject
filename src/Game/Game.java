package Game;

import java.net.URL;

/**
 * Game
 * 
 * @author Jedd Morgan
 * @version 25/03/2019
 */
public class Game {

	private static Location[][] boardArray;
	private static Robot[] robots;
	
	public Game(URL file) {
		//TODO implementation
		//Read from board parse text file
	}
	
	public static Location getLocation(int x, int y) {
		//TODO check that x and y are with in bounds of array
		return boardArray[x][y];
	}
	
}
