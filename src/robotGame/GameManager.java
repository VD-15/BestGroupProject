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
package Game;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import Game.Core.GameObject;
import Utils.LogSeverity;
import Utils.Logger;
import Utils.Vector2;

/**
 * Game
 * 
 * @author Jedd Morgan
 * @version 08/04/2019
 */
/*
public class GameManager {

	private static Location[][] boardArray;
	private static Robot[] robots;

	private static final int TILE_SIZE = 64;
	private static final int NUMBER_OF_ROBOTS = 4;

	public GameManager() {
		robots = new Robot[NUMBER_OF_ROBOTS];
		
	}

	public static Location getLocation(int x, int y) {
		//TODO check that x and y are with in bounds of array
		return boardArray[x][y];
	}

	public ArrayList<GameObject> getGameObjects() {
		ArrayList<GameObject> gameObjects = new ArrayList<GameObject>();
		
		for (GameObject[] a : boardArray)
			for (GameObject o : a) {
				gameObjects.add(o);
			}
		
		for (GameObject o : robots) {
			gameObjects.add(o);
		}
		
		return gameObjects;
	}

	public void generateBoard(File file) throws IOException {
		FileParser p = new FileParser(file);
		
		boardArray = new Location[p.getLineWidth()][p.getLineCount()];

		if (p.getFormat() == 1) {
			int x = 0 , y = 0;
			Vector2 origin = new Vector2(TILE_SIZE / 1,TILE_SIZE / 1);

			String[] contents = p.getContents();

			for (String line : contents) {
				x = 0;
				for(char c : contents[y].toCharArray()) {
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
							if ((c - 'A') <= NUMBER_OF_ROBOTS) {
								robots[c - 'A'] = new Robot(newTile, pos, x, y);
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
				y++;
			}
		}
	}

}
*/
