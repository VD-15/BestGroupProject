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
 * @version 29/04/2019
 */
public class GameManager extends GameObject implements IUpdatable {

	private Board board;

	/** Time elapsed between Round*/
	double rDeltaT = 0;
	/** Time in seconds between each Turn*/
	private static final double TURN_TIME = 1;

	private static final int roundLength = 5;
	private int turnNumber = 0;

	private Queue<Robot> robots;

	private HashMap<Integer, LinkedList<Instruction[]>> players;
	private ArrayList<Point> startingLocations;
	
	private final String programFile;
	private final String boardFile;

	public GameManager(String boardFile, String programFile) 
	{
		this.tag = "gameManager";
		this.programFile = programFile;
		this.boardFile = boardFile;
		
		players = new HashMap<Integer, LinkedList<Instruction[]>>();
		robots = new LinkedList<Robot>();
		
	}
	
	public GameManager() 
	{
		this("testBoard3", "4players");
	}
	

	@Override
	public void init() 
	{
		//creating a new board
		board = new Board(boardFile);
		Game.instantiate(board);

		//getting player data
		players = formatInstructions(ContentManager.getTextByName(this.programFile));
		startingLocations = board.getStartingLocations();

		//for the acceptable number of players
		//i.e. the max unless the board is too small
		for (int i = 0; i < Math.min(players.size(), startingLocations.size()); i++)
		{
			//creating the robots
			Robot r = new Robot(startingLocations.get(i), i + 1);
			Game.instantiate(r);
			robots.offer(r);
		}

	}


	private HashMap<Integer, LinkedList<Instruction[]>> formatInstructions(String[] text)
	{
		//checks for a format line 
		if (!text[0].startsWith("format "))
		{
			Logger.log(this, LogSeverity.ERROR, "Could not discern format from instruction data.");
			return null;
		}
		//sets the formatVersion from the file
		int formatVersion = Integer.valueOf(text[0].split("format ")[1]);

		//switch determined by formatVersion
		//runs a method for each format type
		switch (formatVersion)
		{
		case 1:
			return loadInstructionsFormat1(text);
			//default error for an invalid formatVersion
		default:
			Logger.log(this, LogSeverity.ERROR, "Instruction data had invalid version: {" + formatVersion + "}");
		}
		return null;
	}

	private HashMap<Integer, LinkedList<Instruction[]>> loadInstructionsFormat1(String[] text )
	{	
		final int INSTRUCTION_LINE_STARTS = 2; //Instructions start on line (starting from 0)

		//VALIDATE instruction file contains instructions
		if (text.length < INSTRUCTION_LINE_STARTS + 1)
		{
			Logger.log(this, LogSeverity.ERROR, "Instruction file does not contain any instructions!");
			return null;
		}

		HashMap<Integer, LinkedList<Instruction[]>> players = new HashMap<Integer, LinkedList<Instruction[]>>();

		//For each round
		for (int y = INSTRUCTION_LINE_STARTS; y < text.length; y++)
		{

			String[] roundInstructions = text[y].split(" ");



			//For each player
			for (int playerNum = 0; playerNum < roundInstructions.length; playerNum++)
			{

				Instruction[] playerInstructions = new Instruction[roundInstructions[playerNum].length()];

				//VALIDATE playerRound is of expected size
				if (roundInstructions[playerNum].length() != roundLength)
				{
					Logger.log(this, LogSeverity.ERROR, "Instruction file contains inconsistant number of instructions");
					return null;
				}

				//for each instruction
				for (int j = 0; j < roundInstructions[playerNum].length();j++)
				{
					char c = roundInstructions[playerNum].charAt(j);
					//switch determined by the instruction value
					//adds the appropriate instruction to playerInstructions based on the instruction value
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
						//default error for invalid instruction values
					default:
						Logger.log(this, LogSeverity.ERROR, "Encountered an invalid character while reading program file: {" + c + "}");
						break;
					}
				}

				//updates the instruction arrays of each player
				LinkedList<Instruction[]> temp = players.get(playerNum);
				if (temp == null) temp = new LinkedList<Instruction[]>();
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
	public void update(double time) 
	{
		rDeltaT += time;

		if(rDeltaT > TURN_TIME)
		{
			if(turnNumber != 0 && turnNumber <= roundLength)
			{
				turn();
			}
			rDeltaT = 0;
		}
	}

	@Override
	public void destroy() 
	{
		// TODO Auto-generated method stub

	}

	
	
	/**
	 * Executes a round, round is made up of turns one for each player and one for the board
	 */
	public void setRound() 
	{
		for (int i=0;i < robots.size();i++)
		{
			Robot r = robots.poll();
			r.setInstructions(players.get(i).poll());
			robots.offer(r);
		}
		turnNumber = 1;
	}

	private void turn()
	{
		for(int i = 0; i < robots.size(); i++)
		{
			Robot r = robots.poll();
			r.act();
			robots.offer(r);
		}

		for(int i = 0; i < robots.size(); i++) 
		{
			//Each Player Turn
			Robot r = robots.poll();
			Board.getTile(r.getIndex()).act();

			robots.offer(r);
		}
		turnNumber++;
	}


}
