package Game;

/**
 * Instruction enumeration for programming {@link Robot}
 * 
 * @author Jedd Morgan
 * @version 25/03/2019
 */
public enum Instruction {
	F, //Forward 1 Tile
	B, //Backwards 1 Tile
	R, //Turn 90° right
	L, //Turn 90° left
	U, //Turn 180°
	W, //Wait
	
	//FIXME Might be better to have this as a class,
	//containing an int for translation and something for angle,
	//That way the function of the instruction would be specific to the Instruction class
	//rather than the robot class and would allow for more expandability later on
	
}
