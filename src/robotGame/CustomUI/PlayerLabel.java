package robotGame.CustomUI;

import UI.Label;
import graphics.Color;
import utils.Vector2;

/**
 * A label to show what player is currently active
 * @author Vee
 */
public class PlayerLabel extends Label
{
	/**
	 * The player that is shown as being active
	 */
	private int playerNumber;
	
	/**
	 * Creates a player label
	 * @param playerNumber the player number that is currently active
	 */
	public PlayerLabel(int playerNumber)
	{
		super();
		this.position = new Vector2(10, 0);
		this.tag = "playerLabel";
		
		this.setPlayerNumber(playerNumber);
		
	}
	
	//Sets what player is currently active
	public void setPlayerNumber(int playerNumber)
	{
		this.playerNumber = playerNumber;
		this.text = "PLAYER " + String.valueOf(playerNumber) + ":";
		
		switch (this.playerNumber)
		{
			case 1:
				this.color = new Color(0.925f, 0.063f, 0.063f); // red
				break;
			case 2:
				this.color = new Color(0.055f, 0.416f, 0.875f); // blue
				break;
			case 3:
				this.color = new Color(0.886f, 0.545f, 0.024f); // yellow
				break;
			case 4:
				this.color = new Color(0.035f, 0.667f, 0.090f); // green
				break;
			default:
				this.color = Color.WHITE();
				break;
		}
	}
	
	public void winner(int playerNumber)
	{
		this.playerNumber = playerNumber;
		this.text = "PLAYER " + String.valueOf(playerNumber) + "WINS!";
		
	}
}
