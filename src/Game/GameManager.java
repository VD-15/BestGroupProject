package Game;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import Utils.LogSeverity;
import Utils.Logger;
import Utils.Vector2;

/**
 * Game
 * 
 * @author Jedd Morgan
 * @version 05/04/2019
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
	public Robot[] getRobots() {
		return robots;
	}

	/**
	 * 
	 * @param file
	 * @return Format header of the board or program file
	 * @throws IOException
	 */

	public int getFormat(File file) throws IOException {
		FileReader reader = new FileReader(file);   

		int i, f = 0;
		String format = "";
		while((i=reader.read()) != 10) {
			format += (char) i;
		}
		format = format.replace("format ", "");
		if(String.valueOf(format) != null) {
			f = Integer.parseInt(format);
		}

		reader.close();
		return f;
	}

	/*
	public int getFormat(File file) throws IOException {

		BufferedReader reader = new BufferedReader(new FileReader(file));
		String line = reader.readLine();

		int format = 0;

		line = line.replace("Format ", "");
		if(String.valueOf(line) != null) {
			format = Integer.parseInt(line);
		}

		reader.close();
		return format;
	}
	 */

	/**
	 * 
	 * @param file
	 * @return returns the length of the second line in the file
	 * @throws IOException
	 */
	public int getLineLentgh(File file) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(file));
		reader.readLine(); //Ignore the header line
		String line = reader.readLine();

		reader.close();
		return line.length();
	}



	/**
	 * generates boardArray
	 * @throws IOException 
	 */
	public void generateBoard(File file) throws IOException {

		//Load File
		BufferedReader reader = new BufferedReader(new FileReader(file));
		
		if (getFormat(file) == 1) {

			reader.readLine(); //Ignore the header line


			int x = 0 , y = 0;
			Vector2 origin = new Vector2(TILE_SIZE / 1,TILE_SIZE / 1);
			

			for (String line = reader.readLine(); line != null; line = reader.readLine(), y++) {
				x = 0;
				for(char c : line.toCharArray()) {
					Location newTile = null;
					
					Vector2 pos = new Vector2(x * TILE_SIZE, y * TILE_SIZE).add(origin);
					switch (c) {
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
						newTile = new BeltTile(pos, Direction.SOUTH);
						break;
					case '>': 
						//East Belt
						newTile = new BeltTile(pos, Direction.EAST);
						break;
					case '<': 
						//West Belt
						newTile = new BeltTile(pos, Direction.WEST);
						break;
					case '^': 
						//North Belt
						newTile = new BeltTile(pos, Direction.NORTH);
						break;
					default:
						if (Character.isLetter(c)) {
							//Start tile
							newTile = new NormalTile(pos);

							//FIXME Not the best implementation using NUMBER_OF_ROBOTS
							if ((c - 'a') <= NUMBER_OF_ROBOTS) {
								robots[c - 'a'] = new Robot(newTile, pos, x, y);
							}


						}
						else if (Character.isDigit(c)) {
							//Flag tile
							newTile = new FlagTile(pos, (Character.getNumericValue(c)));
						} else {
							//Error tile
							Logger.log(this, LogSeverity.WARNING, "Read an invalid character whilst trying to generate board from file");
						}
					}
					
					boardArray[x][y] = newTile;
					x++;
				}
			}
			
		}
	}
}
