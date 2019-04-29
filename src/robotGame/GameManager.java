package robotGame;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

import core.ContentManager;
import core.Game;
import core.GameObject;
import core.IUpdatable;
import robotGame.tiles.BeltTile;
import robotGame.tiles.BoardTile;
import robotGame.tiles.FlagTile;
import robotGame.tiles.GearTile;
import robotGame.tiles.PitTile;
import utils.Direction;
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

		players = formatInstructions(ContentManager.getTextByName("4players"));
		startingLocations = board.getStartingLocations();
		
		for (int i = 0; i < Math.min(players.size(), startingLocations.size()); i++)
		{
			Robot r = new Robot(startingLocations.get(i), i + 1);
			Game.instantiate(r);
			robots.offer(r);
		}

	}

	private HashMap<Integer, ArrayList<Instruction[]>> formatInstructions(String[] text)
	{
		if (!text[0].startsWith("format "))
		{
			Logger.log(this, LogSeverity.ERROR, "Could not discern format from instruction data.");
			return null;
		}

		int formatVersion = Integer.valueOf(text[0].split("format ")[1]);

		switch (formatVersion)
		{
		case 1:
			return loadInstructionsFormat1(text);
		default:
			Logger.log(this, LogSeverity.ERROR, "Instruction data had invalid version: {" + formatVersion + "}");
		}
		return null;
	}

	private HashMap<Integer, ArrayList<Instruction[]>> loadInstructionsFormat1(String[] text )
	{	
		final int INSTRUCTION_LINE_STARTS = 2; //Instructions start on line (starting from 0)
		final int EXPECTED_NUM_INSTRUCTIONS = 5; //Expected number of instructions for each player per round
		
		//VALIDATE instruction file contains instructions
		if (text.length < INSTRUCTION_LINE_STARTS + 1)
		{
			Logger.log(this, LogSeverity.ERROR, "Instruction file does not contain any instructions!");
			return null;
		}
		
		HashMap<Integer, ArrayList<Instruction[]>> players = new HashMap<Integer, ArrayList<Instruction[]>>();
		
		//For each round
		for (int y = INSTRUCTION_LINE_STARTS; y < text.length; y++)
		{
			
			String[] roundInstructions = text[y].split(" ");
			
			
			
			//For each player
			for (int playerNum = 0; playerNum < roundInstructions.length; playerNum++)
			{
				
				Instruction[] playerInstructions = new Instruction[roundInstructions[playerNum].length()];
				
				//VALIDATE playerRound is of expected size
				if (roundInstructions[playerNum].length() != EXPECTED_NUM_INSTRUCTIONS)
				{
					Logger.log(this, LogSeverity.ERROR, "Instruction file contains inconsistant number of instructions");
					return null;
				}
				
				
				for (int j = 0; j < roundInstructions[playerNum].length();j++)
				{
					char c = roundInstructions[playerNum].charAt(j);
					switch (c)
					{
					case 'F':
						playerInstructions[j] = Instruction.FORWARD;
						break;
					case 'B':
						playerInstructions[j] = Instruction.BACKWARD;
						break;
					case 'R':
						playerInstructions[j] = Instruction.RIGHT;
						break;
					case 'L':
						playerInstructions[j] = Instruction.LEFT;
						break;
					case 'U':
						playerInstructions[j] = Instruction.UTURN;
						break;
					case 'W':
						playerInstructions[j] = Instruction.WAIT;
						break;
					default:
						Logger.log(this, LogSeverity.ERROR, "Encountered an invalid character while reading program file: {" + c + "}");
						break;
					}
				}
				
				
				ArrayList<Instruction[]> temp = players.get(playerNum);
				if (temp == null) temp = new ArrayList<Instruction[]>();
				temp.add(playerInstructions);
				players.put(playerNum, temp);

			}

		}
		return players;
	}


	/*
	 * MESSAGE TO OWEN!! VERRY IMPORTANT DON'T MISS THIS!! ITS VERY IMPORTANT THAT THIS MESSAGE IS READ IN ITS ENTIREITY LEST THEIR BE SOME SORT OF CONFUSION ATTAINING TO THE NATURE OF THIS METHOD
	 * What needs to be done is fairly simple but we can't really progress with this turn thing without
	 * V's input system which is very close to completion ( or depending on V's ability to power work through the night is complete but would be on the master branch )
	 * I suggest you wait for the completion of said input system, and me to merge the two branches so that we can continue
	 * 
	 * When that has happened, We basically want to not start running instructions until a start button is pressed
	 * The way we check for that is unclear at the moment and requires V to decide how button events will be handled
	 * Once the round has started we execute robot instructions with the delay of TURN_TIME
	 * 
	 * Then we wait until the round start button is pressed again. 
	 * This will allow for future expandability when we use the input system to its entirety to get user input through the keyboard listener
	 * Rather than through the program file.
	 * Very nice ;)
	 */
	@Override
	public void update(double time) {
		rDeltaT += time;

		if (rDeltaT > TURN_TIME) {
			rDeltaT = 0;

			//round();


		}
	}



	/**
	 * Executes a round, round is made up of turns one for each player and one for the board
	 */
	/*
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
	 */
}
