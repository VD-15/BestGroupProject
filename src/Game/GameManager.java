package Game;

import java.io.File;

/**
 * Game
 * 
 * @author Jedd Morgan
 * @version 25/03/2019
 */
public class GameManager {

	private static Location[][] boardArray;
	private static Robot[] robots;
	
	public GameManager(File file) {
		//TODO implementation
		//Read from board parse text file
		generateBoard(file);
		
		
	}
	
	public static Location getLocation(int x, int y) {
		//TODO check that x and y are with in bounds of array
		return boardArray[x][y];
	}
	
	/**
	 * 
	 * @return returns whether the board was successful
	 */
	private boolean generateBoard(File file) {
		//TODO Check version of board
		
		
		//
		
		return true;
	}
}
