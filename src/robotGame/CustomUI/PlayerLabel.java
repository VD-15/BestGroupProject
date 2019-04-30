package robotGame.CustomUI;

import UI.Label;
import utils.Vector2;

public class PlayerLabel extends Label
{
	public PlayerLabel()
	{
		super();
		this.position = new Vector2(10, 0);
		this.text = "PLAYER 1:";
	}
}
