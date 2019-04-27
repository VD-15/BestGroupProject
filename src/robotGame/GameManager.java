package robotGame;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Queue;

import core.Game;
import core.GameObject;
import core.IUpdatable;
import robotGame.tiles.BoardTile;
import utils.LogSeverity;
import utils.Logger;

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
	
	/** Order */
	private Queue<Robot> robots;

	@Override
	public void init() {
		board = new Board();
		Game.instantiate(board);
		
		
	}

	
	public void formatInstructions(String[] text )
	{	
		HashMap<Integer, ArrayList<Instruction[]>> players = new HashMap<Integer, ArrayList<Instruction[]>>();
		for (int i = 1; i < text.length; i++)
		{
			String[] line = text[i].split(" ");
			if (i == 1)
			{
				int playernumber = line.length;
			}
			else 
			{
				for (i = 0; i < line.length; i++)
				{
					Instruction[] instructions = new Instruction[line[i].length()];
					for (int j = 0; j < line[i].length();j++)
					{
						char c = line[i].charAt(j);
						switch (c)
						{
						case 'F':
							instructions[i] = Instruction.FORWARD;
							break;
						case 'B':
							instructions[i] = Instruction.BACKWARD;
							break;
						case 'R':
							instructions[i] = Instruction.RIGHT;
							break;
						case 'L':
							instructions[i] = Instruction.LEFT;
							break;
						case 'U':
							instructions[i] = Instruction.UTURN;
							break;
						case 'W':
							instructions[i] = Instruction.WAIT;
							break;
						default:
							Logger.log(this, LogSeverity.ERROR, "Encountered an invalid character while reading board file: {" + c + "}");
							break;
						}
					}
					ArrayList<Instruction[]> temp = players.get(i);
					temp.add(instructions);
					players.put(i, temp);

				}
			}
		}
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
			
			//turn();
			
			
		}
	}
}
