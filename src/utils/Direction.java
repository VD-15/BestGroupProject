package utils;

/**
 * Absolute Directions
 * @author Jedd Morgan
 * @version 28/04/2019
 */
public enum Direction {
	NORTH("North", 'N'),
	EAST("East", 'E'),
	SOUTH("South", 'S'),
	WEST("West", 'W');
	
	
	
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
	 * rotations is how many turns to the right from the Direction
	 * Accepts any integer value
	 * 
	 * @param rotations - number of rotations to be applied ( 90° Right = +1 )
	 * @return new Direction after rotation
	 */
	public Direction add(int rotations) {
		return Direction.values()[Math.floorMod((ordinal() + rotations), Direction.values().length)];
	}

	/**
	 * Calculates the absolute direction angle in radians
	 * @return angle in radians
	 */
	public float getAngle() {
			return (float) (ordinal() / 2f * Math.PI);
	}
	
}
