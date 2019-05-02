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
import robotGame.tiles.LaserEmitter;
import utils.LogSeverity;
import utils.Logger;
import utils.Point;
import UI.Button;

/**
 * GameManager
 * Instantiates board, and controls rounds
 * 
 * @author Jedd Morgan
 * @author Owen Craig
 * @version 02/05/2019
 */
public class GameManager extends GameObject implements IUpdatable {

	private Board board;

	/** Time elapsed between Round*/
	double rDeltaT = 0;
	/** Time in seconds between each Turn*/
	private static final double TURN_TIME = 1;

	private static final int roundLength = 5;
	
	private static boolean fromFile;
	
	private int roundNumber;
	private int turnNumber = 0;
	private int playerNumber;
	private int currentPlayer;
	private boolean roundReady = false;

	private Queue<Robot> robots;

	private HashMap<Integer, LinkedList<Instruction[]>> players;
	private ArrayList<Point> startingLocations;
	
	private final String programFile;
	private final String boardFile;

	public GameManager(String boardFile, String programFile, boolean fromFile, int playerNumber) 
	{
		this.tag = "gameManager";
		this.boardFile = boardFile;
		this.programFile = programFile;
		this.fromFile = fromFile;
		this.playerNumber = playerNumber; 
		players = new HashMap<Integer, LinkedList<Instruction[]>>();
		robots = new LinkedList<Robot>();
		
	}
	
	public GameManager() 
	{
		this("testBoard3", "2players-2rounds", true, 2);
	}
	
	@Override
	public void init() 
	{
		//creating a new board
		board = new Board(boardFile);
		Game.instantiate(board);
		startingLocations = board.getStartingLocations();

		if(fromFile == true)
		{
			//getting player data
			formatInstructions(ContentManager.getTextByName(this.programFile));
		}
		else
		{
			for(int i = 0; i < playerNumber; i++)
			{
				Instruction[] t = new Instruction[this.roundLength];
				LinkedList<Instruction[]> temp = new LinkedList<Instruction[]>();
				temp.add(t);
				players.put(i, temp);
			}
			currentPlayer = 1;
		}
		try
		{
			
			//for the acceptable number of players
			//i.e. the max unless the board is too small
			for (int i = 0; i < Math.min(playerNumber, startingLocations.size()); i++)
			{
				//creating the robots
				Robot r = new Robot(startingLocations.get(i), i + 1);
				Game.instantiate(r);
				robots.offer(r);
			}
		}
		catch(Exception e)
		{
			Logger.log(this, LogSeverity.ERROR, "File data could not be loaded sucessfully");
		}

	}

	
	public static boolean getfromFile()
	{
		return fromFile;
	}
	public void run()
	{
		if(fromFile == false)
		{
			if(roundReady == true)
			{
				roundNumber = 1;
				currentPlayer = 1;
				setRound();
				roundReady = false;
			}
			else
			{
				Logger.log(this, LogSeverity.WARNING, "players have not been fully programmed.");
			}
		}
		else 
		{
			setRound();
		}
			
	}
	
	
	

	
	
	/**
	 * 
	 * @param text the program file as an array of strings
	 * @return a HashMap containing instructions for each round for each robot.
	 */
	private void formatInstructions(String[] text)
	{
		//checks for a format line 
		if (!text[0].startsWith("format "))
		{
			Logger.log(this, LogSeverity.ERROR, "Could not discern format from instruction data.");
		}
		//sets the formatVersion from the file
		int formatVersion = Integer.valueOf(text[0].split("format ")[1]);

		//switch determined by formatVersion
		//runs a method for each format type
		switch (formatVersion)
		{
		case 1:
			roundNumber = text.length - 2;
			players = loadInstructionsFormat1(text);
			break;
			//default error for an invalid formatVersion
		default:
			Logger.log(this, LogSeverity.ERROR, "Instruction data had invalid version: {" + formatVersion + "}");
			break;
		}
	}

	/**
	 * 
	 * @param text the program file as an array of strings
	 * @return a HashMap containing instructions for each round for each robot.
	 */
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
		String[] playerNumber = text[1].split(" ");
	
		//For each round
		for (int y = INSTRUCTION_LINE_STARTS; y < text.length; y++)
		{

			String[] roundInstructions = text[y].split(" ");
			
			//VALIDATE roundInsructions is the expected size
			if (roundInstructions.length != playerNumber.length)
			{
				Logger.log(this, LogSeverity.ERROR, "Instruction file contains inconsistant round data");
				return null;
			}


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

				char previous = ' '; 
				
				//for each instruction
				for (int j = 0; j < roundInstructions[playerNum].length();j++)
				{
					char c = roundInstructions[playerNum].charAt(j);
					
					if(c == previous) 
					{
						Logger.log(this, LogSeverity.ERROR, "Identical instructions cannot be used consecutively");
						return null;
					}
					else previous = c;
					
					//adds the appropriate instruction to playerInstructions based on the instruction value
					playerInstructions[j] = translateInstruction(c);
					
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

	/**
	 * 
	 * @param button the button that has been pressed
	 */
	public void programInstructions(String button)
	{

		Instruction[] currentRoundData = players.get(currentPlayer-1).get(0);
		char c = button.charAt(0);
		
		for(int i= 0; i < currentRoundData.length;i++)
		{
			if(i == currentRoundData.length-1)
			{
				if(currentPlayer == playerNumber)
				{
					if(currentRoundData[i] == null)
					{
						currentRoundData[i] = translateInstruction(c);
						roundReady = true;
						
						break;
					}
					else
					{
						Logger.log(this, LogSeverity.WARNING, "All player instructions have been programmed. Click the run button to play the round");
					}
				}
				else
				{
					if(currentRoundData[i] == null)
					{
						currentRoundData[i] = translateInstruction(c);
						break;
					}
					else
					{
						currentPlayer++;
						currentRoundData = players.get(currentPlayer-1).get(0);
						i = 0;
					}
				}
			}
			else
			{
				if(currentRoundData[i] == null)
				{
					currentRoundData[i] = translateInstruction(c);
					break;
				}
			}
			
		}
	}
	/**
	 * 
	 * @param c the instruction character to be translated
	 * @return the translated instruction 
	 */
	public Instruction translateInstruction(char c)
	{
		//switch determined by the instruction value
		switch (c)
		{
		case 'F':
			return Instruction.FORWARD;

		case 'B':
			return Instruction.BACKWARD;

		case 'R':
			return Instruction.RIGHT;

		case 'L':
			return Instruction.LEFT;

		case 'U':
			return Instruction.UTURN;

		case 'W':
			return Instruction.WAIT;

			//default error for invalid instruction values
		default:
			Logger.log(this, LogSeverity.ERROR, "Encountered an invalid instruction character: {" + c + "}");
			return null;

		}
	}
	
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
		if(roundNumber != 0)
		{
			for (int i=0;i < robots.size();i++)
			{
				Robot r = robots.poll();
				r.setInstructions(players.get(r.getNumber()-1).poll());
				robots.offer(r);
				Instruction[] temp = new Instruction[roundLength];
				players.get(r.getNumber()-1).offer(temp);
			}
			turnNumber = 1;
			roundNumber--;
		}
		else 
		{
			Logger.log(this, LogSeverity.WARNING, "No more round data");
		}
	}

	
	public void victory(Robot r)
	{
		Logger.log(this, LogSeverity.INFO, "Player "+ r.getNumber() +"has won!");
		
		for(int i = 0; i < robots.size(); i++)
		{
			Robot j = robots.poll();
			j.resetLocation();
			robots.offer(j);
		}
	}
	
	/**
	 * Executes a single turn
	 */
	private void turn()
	{
			//each robot acts
			for(int i = 0; i < robots.size(); i++)
			{
				Robot r = robots.poll();
				r.act();
				robots.offer(r);
				if(r.getFlag() == 4)
				{
					victory(r);
				}
			}
			
			//changes the turn order for the next turn
			robots.offer(robots.poll());
	
			//every tile with a robot present acts
			for(int i = 0; i < robots.size(); i++) 
			{
				Robot r = robots.poll();
				Board.getTile(r.getIndex()).act();
				robots.offer(r);
			}
			//for each tile, if the tile is a laserEmitter have it act
			//laserEmitters act every turn
			for(BoardTile tile : board.getBoardTiles()) 
			{
				if(tile instanceof LaserEmitter)
				{
					tile.act();
				}
			}
		turnNumber++;
	}


}
