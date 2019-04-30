package robotGame;

import core.Camera;
import core.Game;
import core.IUpdatable;
import input.Keyboard;
import input.Mouse;
import utils.LogSeverity;
import utils.Logger;
import utils.Region;
import utils.Vector2;

public class MainCamera extends Camera implements IUpdatable
{
	private static double CAMERA_SPEED = 256.0;
	
	public MainCamera()
	{
		super(1);
	}
	
	@Override
	public void init()
	{
		this.viewportSize = Game.getWindow().getViewport();
		this.position = new Vector2(232,200);
	}
	
	@Override
	public void update(double time)
	{
		Vector2 moveVector = new Vector2();
		
		Keyboard keyboard = Game.getWindow().getKeyboard();
		
		if (keyboard.isKeyDown("cameraUp")) moveVector.translate(Vector2.UP());
			
		if (keyboard.isKeyDown("cameraDown")) moveVector.translate(Vector2.DOWN());
		
		if (keyboard.isKeyDown("cameraLeft")) moveVector.translate(Vector2.LEFT());
		
		if (keyboard.isKeyDown("cameraRight")) moveVector.translate(Vector2.RIGHT());
		
		if (moveVector.length() > 1f) moveVector.normalize();
		
		moveVector.scale((float)(CAMERA_SPEED * time));
		
		this.position.translate(moveVector);
		
		//zoom = (float)Math.pow(1.1f, -(mouse.getScrollDelta() / 1f));
	}
	
	@Override
	public Region getViewport()
	{
		return new Region(this.position, this.viewportSize, true);
	}
}
