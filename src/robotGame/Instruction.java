package robotGame;

/**
 * Instruction enumeration for programming {@link Robot}
 * 
 * @author Jedd Morgan
 * @version 25/03/2019
 */
public enum Instruction 
{
	FORWARD, //Forward 1 Tile
	BACKWARD, //Backwards 1 Tile
	RIGHT, //Turn 90° right
	LEFT, //Turn 90° left
	UTURN, //Turn 180°
	WAIT, //Wait
	
	//FIXME Might be better to have this as a class,
	//containing an int for translation and something for angle,
	//That way the function of the instruction would be specific to the Instruction class
	//rather than the robot class and would allow for more expandability later on
	
	//// ##### You know you can just make a function for that, right? #####
}
