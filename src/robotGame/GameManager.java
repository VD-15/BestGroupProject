package robotGame;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

import core.ContentManager;
import core.Game;
import core.GameObject;
import core.IUpdatable;
import robotGame.tiles.BoardTile;
import utils.LogSeverity;
import utils.Logger;
import utils.Point;

/**
 * GameManager
 * Instantiates board, and controls rounds
 * 
 * @author Jedd Morgan
 * @author Owen Craig
 * @version 27/04/2019
 */
public class GameManager extends GameObject implements IUpdatable {

	private Board board;
	
	/** Time elapsed between Round*/
	double rDeltaT = 0;
	/** Time in seconds between each Turn*/
	private static final double TURN_TIME = 1; 
	/** Time in seconds between each Round*/
	private static final double ROUND_TIME = 5;
	
	private Queue<Robot> robots;
	
	private HashMap<Integer, ArrayList<Instruction[]>> players;
	private ArrayList<Point> startingLocations;

	public GameManager() {
		players = new HashMap<Integer, ArrayList<Instruction[]>>();
		robots = new LinkedList<Robot>();
	}
	@Override
	public void init() 
	{
		board = new Board();
		Game.instantiate(board);
		//TEMP!! commented out as causes errors
		//players = formatInstructions(ContentManager.getTextByName("4players"));
		startingLocations = board.getStartingLocations();
		//TEMP!! commented out and replaced with 4 so that it works for testing 
		//for (int i = 0; i < players.size(); i++)
		for (int i = 0; i < 4; i++)
		{
			Robot r = new Robot(startingLocations.get(i), i + 1);
			Game.instantiate(r);
			robots.offer(r);
		}
		
	}

	
	public HashMap<Integer, ArrayList<Instruction[]>> formatInstructions(String[] text )
	{	
		
		HashMap<Integer, ArrayList<Instruction[]>> players = new HashMap<Integer, ArrayList<Instruction[]>>();
		for (int i = 2; i < text.length; i++)
		{
			String[] line = text[i].split(" ");
			for (int l = 0; l < line.length; l++)
			{
				Instruction[] round = new Instruction[line[l].length()];
				for (int j = 0; j < line[l].length();j++)
				{
					char c = line[l].charAt(j);
					switch (c)
					{
					case 'F':
						round[j] = Instruction.FORWARD;
						break;
					case 'B':
						round[j] = Instruction.BACKWARD;
						break;
					case 'R':
						round[j] = Instruction.RIGHT;
						break;
					case 'L':
						round[j] = Instruction.LEFT;
						break;
					case 'U':
						round[j] = Instruction.UTURN;
						break;
					case 'W':
						round[j] = Instruction.WAIT;
						break;
					default:
						Logger.log(this, LogSeverity.ERROR, "Encountered an invalid character while reading program file: {" + c + "}");
						break;
					}
				}
				ArrayList<Instruction[]> temp = players.get(l);
				temp.add(round);
				players.put(l, temp);

			}
			
		}
		return players;
	}
	
	
	
	
	/**
	 * Executes a round, round is made up of turns one for each player and one for the board
	 */
	private void round() {
		for(int i = 0; i < robots.size(); i++) {
			//Each Player Turn
			Robot r = robots.poll();
			r.act();
			
			robots.add(r);
		}
		robots.add(robots.poll());
		
		//
		for(BoardTile tile : board.getBoardTiles()) {
			tile.act();
		}
		
		Logger.log(this, LogSeverity.INFO, "Done a turn");
		
	}
	
	
	@Override
	public void update(double time) {
		rDeltaT += time;
		
		if (rDeltaT > TURN_TIME) {
			rDeltaT = 0;
			
			round();
			
			
		}
	}
}
