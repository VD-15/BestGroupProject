package robotGame.tiles;

import graphics.RenderBatch;
import graphics.RenderInstance;
import robotGame.Direction;
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
	private final int rotation;
	private RenderInstance renderInstance;


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

	public void init() {
		{
			String name = "tileBelt";

			if(rotation == -1) {
				name += "C";
			} else if (rotation == 1) {
				name += "CC";
			}

			renderInstance = new RenderInstance()
					.withTexture(name)
					.withDestinationRegion(new Region(this.position, new Vector2(TILE_SIZE), true))
					.withDepth(1f)
					.withLayer(1)
					.withRotation(direction.getAngle())
					.withRotationOrigin(this.position)
					.build();
		}
		
	}
	/**
	 * {@inheritDoc}<br>
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

	@Override
	public void draw(RenderBatch b) 
	{
		b.draw(renderInstance);
	}

}
