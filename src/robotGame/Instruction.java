package robotGame;

/**
 * Instruction enumeration for programming {@link Robot}
 * 
 * @author Jedd Morgan
 * @version 25/03/2019
 */
public enum Instruction 
{
	FORWARD	(1, 0), 	//Forward 1 Tile
	BACKWARD(-1, 0), 	//Backwards 1 Tile
	RIGHT	(0,1), 		//Turn 90° right
	LEFT	(0,-1), 	//Turn 90° left
	UTURN	(0,2), 		//Turn 180°
	WAIT	(0,0); 		//Wait
	
	
	private final int translation, rotation;

	/**
	 * 
	 * @param translation how much the instruction should translate the GameObject
	 * @param rotation how much the instruction should rotate the GameObject
	 */
	Instruction(int translation, int rotation) {
		this.translation = translation;
		this.rotation = rotation;
	}

	public int getTranslation() {
		return translation;
	}

	public int getRotation() {
		return rotation;
	}
	
	@Override
	public String toString()
	{
		//Make sure these strings have the first letter capitalised, or buttons aren't going to work otherwise!
		switch (this)
		{
			case FORWARD:
				return "Forward";
			case BACKWARD:
				return "Backward";
			case RIGHT:
				return "Right";
			case LEFT:
				return "Left";
			case UTURN:
				return "Uturn";
			case WAIT:
				return "Wait";
			default:
				return "invalid instruction";
		}
	}
}
