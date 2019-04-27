package utils;

/**
 * Absolute Directions
 * @author Jedd Morgan
 * @version 24/04/2019
 */
public enum Direction {
	EAST("East", 'E'),
	NORTH("North", 'N'),
	WEST("West", 'W'),
	SOUTH("South", 'S');
	
	
	private final String name;
    private final char character;
	
	/**
	 * 
	 * @param name - the String representation
	 * @param character -  The character representation
	 */
    Direction(String name, char character) {
		this.name = name;
		this.character = character;
	}
	
    
    /**
	 * Returns the string representation of the Direction.
	 * @return name
	 */
	public String getName() {
		return name;
	}
	
	
	/**
	 * Returns the character representation of Direction.
	 * @return character
	 */
	public char getCharacter() {
		return character;
	}
	
	/**
	 * Adds a rotation to the Direction<br>
	 * angle is how many turns to the left from the Direction
	 * Accepts any integer value
	 * 
	 * @param angle in half pi radians (90° left = +1)  
	 * @return new Direction after angle transform
	 */
	public Direction add(int angle) {
		return Direction.values()[(ordinal() + angle) % Direction.values().length];
	}


	
	
	
}
