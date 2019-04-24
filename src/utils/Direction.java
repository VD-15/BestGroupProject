package utils;

/**
 * Absolute Directions
 * @author Jedd Morgan
 * @version 24/04/2019
 */
public enum Direction {
	EAST("East", 'N'),
	NORTH("North", 'N'),
	WEST("West", 'N'),
	SOUTH("South", 'N');
	
	
	private final String name;
    private final char character;
	
    
	/**
	 * 
	 * @param name name of direction
	 * @param character Character representation of the direction
	 */
    Direction(String name, char character) {
		this.name = name;
		this.character = character;
	}
	
    
    /**
	 * 
	 * @return String representation of Direction
	 */
	public String getName() {
		return name;
	}
	
	
	/**
	 * 
	 * @return character representation of Direction
	 */
	public char getCharacter() {
		return character;
	}
	
	/**
	 * Adds a angle to the direction
	 * @param angle in half pi radians (90° left = +1) accepts any integer value
	 * @return new direction after angle transform
	 */
	public Direction add(int angle) {
		return Direction.values()[(ordinal() + angle) % Direction.values().length];
	}


	
	
	
}
