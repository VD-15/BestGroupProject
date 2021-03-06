package robotGame;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

import core.ContentManager;
import core.Game;
import core.GameObject;
import core.GameWindow;
import core.IUpdatable;
import core.Program;
import robotGame.CustomUI.InstructionViewer;
import robotGame.CustomUI.PlayerLabel;
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
	
	
	/** the number of rounds*/
	private int roundNumber;
	
	/** the current turn number */
	private int turnNumber = 0;
	
	/** the current working player */
	private int currentPlayer;
	
	/** a boolean representing whether a round is programmed or now */
	private boolean roundReady = false;
	
	/** The last instruction submitted */
	private String clickedInstruction;
	
	/** the instruction before last */
	private String previousInstruction; 

	/** a Queue of the robots */
	private Queue<Robot> robots;

	/** a HashMap linking the robot numbers to their instruction sets */
	private HashMap<Integer, LinkedList<Instruction[]>> players;
	
	/** an arrayList of the starting locations because of the robots */
	private ArrayList<Point> startingLocations;
	
	/** boolean representing whether a programFile is being loaded from file
	 * or Instructions are being programmed by players
	 */
	private static boolean fromFile;
	
	/** the number of players */
	private int playerNumber;
	
	/** the name of the programFile to be loaded */
	private String programFile;
	
	/** the name of the boardFile to be loaded */
	private String boardFile;

	/**
	 * creates a new GameManager
	 * @param args the arguments given by the user to indicate which version of the game is being run
	 */
	public GameManager(String[] args) 
	{
		
		if(args.length == 0)
		{
			Logger.log(this, LogSeverity.ERROR, "there were no arguments. Please specify the boardFile followed by programfile or player number");
			Game.stop();
			this.boardFile = "empty-0x0.brd";
			this.programFile = "empty-file.prg";
			this.fromFile = true;
		}
		else
		{
			this.tag = "gameManager";
			this.boardFile = args[0];
			int p = -1;
			try 
			{
				p = Integer.parseInt(args[1]);
			}
			catch(Exception e) 
			{
				
			}

			if(p > -1)
			{
				this.fromFile = false;
				this.playerNumber = p;
			}
			else
			{
				this.programFile = args[1];
				this.fromFile = true;
			}
			
			players = new HashMap<Integer, LinkedList<Instruction[]>>();
			robots = new LinkedList<Robot>();
			
		}
		
		
		
		
	}
	
	/**
	 * a default constructer for testing purposes
	 */
	public GameManager()
	{
		this(Program.arguments);
	}
	
	@Override
	public void init() 
	{
		//creating a new board
		ContentManager.loadText("boards/"+ this.boardFile, this.boardFile);
		board = new Board(this.boardFile);
		Game.instantiate(board);
		startingLocations = board.getStartingLocations();

		if(fromFile == true)
		{
			//getting player data
			ContentManager.loadText("programs/" + this.programFile, this.programFile);
			formatInstructions(ContentManager.getTextByName(this.programFile));
		}
		else
		{
			Button b = (Button) Game.getGameObjectsByTag("buttonGo").get(0);
			b.disable();
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
			for (int i = 0; i < Math.min(players.size(), startingLocations.size()); i++)
			{
				//creating the robots
				Robot r = new Robot(startingLocations.get(i), i + 1);
				Game.instantiate(r);
				robots.offer(r);
				//displayNames();
			}
			playerNumber = robots.size();
		}
		catch(Exception e)
		{
			Logger.log(this, LogSeverity.ERROR, "File data could not be loaded sucessfully");
		}

	}

	
	
	/**
	 * accessor method for fromFile
	 * @return returns fromFile
	 */
	public static boolean getFromFile()
	{
		return fromFile;
	}
	
	/**
	 * sets a round up to be run. if the game is not loading a programFile, its will check
	 * to see if all the palyers have been fully programmed first
	 */
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
	 * formats the programFile for the GameManager to use
	 * @param text the program file as an array of strings
	 */
	private void formatInstructions(String[] text)
	{
		if(text.length == 0)
		{
			Logger.log(this, LogSeverity.ERROR, "empty board file");
		}
		else
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
	}

	/**
	 * formats the programFile according to format 1
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
	 * deletes the last instruction to be programmed
	 */
	public void deleteInstruction()
	{
		Instruction[] currentRoundData = players.get(currentPlayer-1).get(0);
		
		for(int i = 0; i < currentRoundData.length; i++ )
		{
			if(currentRoundData[0] == null)
			{
				Logger.log(this, LogSeverity.ERROR, "There are no instructions to delete");
				break;
			}
			if(i == currentRoundData.length - 1 || currentRoundData[i+1] == null) 
			{
				currentRoundData[i] = null;
				Logger.log(this, LogSeverity.INFO, "Instruction deleted");
				Button b = (Button) Game.getGameObjectsByTag("instructionButton"+clickedInstruction).get(0);
				b.enable();
				if(previousInstruction != null)
				{
					Button p = (Button) Game.getGameObjectsByTag("instructionButton"+previousInstruction).get(0);
					p.disable();
				}
				
				clickedInstruction = previousInstruction;
				InstructionViewer  iv = (InstructionViewer) Game.getGameObjectsByTag("InstructionViewer" + String.valueOf(currentPlayer)).get(0);
				iv.removeBack();
				Button g = (Button) Game.getGameObjectsByTag("buttonGo").get(0);
				g.disable();
				roundReady = false;
				break;
			}
		}
		
	}

	/**
	 * programs a new instruction to the current player
	 * @param button the button that has been pressed
	 */
	public void programInstructions(String button)
	{
		if(clickedInstruction != null)
		{
			previousInstruction = clickedInstruction;
			Button b = (Button) Game.getGameObjectsByTag("instructionButton"+clickedInstruction).get(0);
			b.enable();
		}
		clickedInstruction = button;
		Button b = (Button) Game.getGameObjectsByTag("instructionButton"+button).get(0);
		b.disable();
		
		Instruction[] currentRoundData = players.get(currentPlayer-1).get(0);
		char c = button.charAt(0);
		Instruction newInstruction;

		for(int i= 0; i < currentRoundData.length;i++)
		{
			InstructionViewer  iv = (InstructionViewer) Game.getGameObjectsByTag("InstructionViewer" + String.valueOf(currentPlayer)).get(0);
			
			if(i == currentRoundData.length-1)
			{
				if(currentPlayer == playerNumber)
				{
					if(currentRoundData[i] == null)
					{
						newInstruction = translateInstruction(c);
						currentRoundData[i] = newInstruction;
						iv.pushInstruction(newInstruction);
						roundReady = true;
						Button g = (Button) Game.getGameObjectsByTag("buttonGo").get(0);
						g.enable();
						b.enable();
						break;
					}
					else
					{
						Logger.log(this, LogSeverity.WARNING, "All player instructions have been programmed. Click the run button to play the round");
						b.enable();
					}
				}
				else
				{
					if(currentRoundData[i] == null)
					{
						newInstruction = translateInstruction(c);
						currentRoundData[i] = newInstruction;
						iv.pushInstruction(newInstruction);
						b.enable();
						break;
					}
					else
					{
						currentPlayer++;
						currentRoundData = players.get(currentPlayer-1).get(0);
						newInstruction = translateInstruction(c);
						currentRoundData[0] = newInstruction;
						iv = (InstructionViewer) Game.getGameObjectsByTag("InstructionViewer" + String.valueOf(currentPlayer)).get(0);
						iv.pushInstruction(newInstruction);
						PlayerLabel p = (PlayerLabel) Game.getGameObjectsByTag("playerLabel").get(0);
						p.setPlayerNumber(currentPlayer);
						previousInstruction = null;
						break;
					}
				}
			}
			else
			{
				if(currentRoundData[i] == null)
				{
					newInstruction = translateInstruction(c);
					currentRoundData[i] = newInstruction;
					iv.pushInstruction(newInstruction);
					break;
				}
			}
		}
	}
	
	/**
	 * translates a character into an Instruction
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
	
	/**
	 * updates rDeltaT every frame. If rDeltaT becomes greater than TURN_TIME
	 * and a round have been set, the turn method will be run
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
		if(roundNumber != 0)
		{
			Button g = (Button) Game.getGameObjectsByTag("buttonGo").get(0);
			g.disable();
			
			for (int i=0;i < robots.size();i++)
			{
				Robot r = robots.poll();
				Instruction[] temp = players.get(r.getNumber()-1).poll();
				if(fromFile == true)
				{
					InstructionViewer  iv = (InstructionViewer) Game.getGameObjectsByTag("InstructionViewer" + r.getNumber()).get(0);
					for(Instruction currentInstruction: temp)
					{
						iv.pushInstruction(currentInstruction);
					}
				}
				
				r.setInstructions(temp);
				robots.offer(r);
				temp = new Instruction[roundLength];
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

	/**
	 * logs a robots victory and resets player locations
	 * @param r the winning robot
	 */
	public void victory(Robot r)
	{
		Logger.log(this, LogSeverity.INFO, "Player "+ r.getNumber() +"has won!");
		PlayerLabel p = (PlayerLabel) Game.getGameObjectsByTag("playerLabel").get(0);
		p.winner(r.getNumber());;
		
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
				
				InstructionViewer  iv = (InstructionViewer) Game.getGameObjectsByTag("InstructionViewer" + r.getNumber()).get(0);
				iv.removeFront();
				
				if(r.getFlag() == Board.getNumberOfFlags())
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
			if(turnNumber == roundLength )
			{
				if(fromFile == true)
				{
					Button g = (Button) Game.getGameObjectsByTag("buttonGo").get(0);
					g.enable();
				}
				PlayerLabel p = (PlayerLabel) Game.getGameObjectsByTag("playerLabel").get(0);
				p.setPlayerNumber(1);
				
			}
		turnNumber++;
	}


}