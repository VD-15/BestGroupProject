package robotGame.tiles;

import core.Game;
import graphics.RenderBatch;
import graphics.RenderInstance;
import robotGame.Robot;
import utils.Direction;
import utils.LogSeverity;
import utils.Logger;
import utils.Point;
import utils.Region;
import utils.Vector2;

/**
 * Belt Tile
 * 
 * @author Jedd Morgan, Vanessa Kostadinova
 * @version 24/05/2019
 */
public class BeltTile extends BoardTile 
{
	private Direction direction;
	private int rotation;

	/**
	 * Creates a new belt
	 * @param index The point on a grid.
	 * @param direction The direction the tile acts in.
	 * @param rotation For corner belts use 1 for left and -1 for right. For normal belt tiles 0.
	 */
	public BeltTile(Point index, Direction direction, int rotation)
	{
		super(index);
		this.direction = direction;
		this.rotation = rotation;
		this.tag = "BeltTile";
	}

	/**
	 * {@inheritDoc}
	 * Rotates robot then moves it.
	 */
	@Override
	public void act() 
	{
		if (currentRobot != null) {
			currentRobot.rotate(rotation);
			currentRobot.move(direction);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void draw(RenderBatch b) 
	{
		String name = "tileBelt" + direction.getName();

		switch (rotation) {
		case 1: //CC belt
			name += "CC";
			break;
		case -1: //C Belt
			name += "C";
			break;
		case 0: break; //Belt
		default:
			name = "tileError";
			Logger.log(this, LogSeverity.WARNING, "No sprite for belt with rotation" + rotation);
			break;
		}

		b.draw(new RenderInstance()
				.withTexture(name)
				.withDestinationRegion(new Region(this.position, new Vector2(TILE_SIZE), true))
				.withDepth(1f)
				.withLayer(1)
				);
	}

}
