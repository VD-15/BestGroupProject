package robotGame.tiles;

import graphics.RenderBatch;
import graphics.RenderInstance;
import robotGame.Direction;
import utils.Point;
import utils.Region;
import utils.Vector2;

/**
 * Belt Tile
 * 
 * @author Jedd Morgan
 * @author Vanessa Kostadinova
 * @version 01/06/2019
 */
public class BeltTile extends BoardTile 
{
	/** Direction tile is facing and acting in.*/
	private final Direction direction;
	/** Rotation tile performs.*/
	private final int rotation;
	
	/**
	 * Creates a new belt
	 * @param index The point on a grid.
	 * @param direction The direction the tile acts in.
	 * @param rotation For corner belts use 1 for right and -1 for left. For normal belt tiles 0.
	 */
	public BeltTile(Point index, Direction direction, int rotation)
	{
		// Sets variables.
		super(index);
		this.direction = direction;
		this.tag = "BeltTile";
		this.sprite = "tileBelt";
		this.rotation = rotation;
		
		// Changes sprite based on rotation.
		if(rotation == 1) {
			sprite += "C";
		} else if (rotation == -1) {
			sprite += "CC";
		}
	}

	/**
	 * {@inheritDoc}<br>
	 * Rotates robot then moves it.
	 */
	@Override
	public void act()
	{
		// If there is a robot on tile rotation and movement are applied.
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
		b.draw(new RenderInstance()
				.withTexture(sprite)
				.withDestinationRegion(new Region(this.position, new Vector2(TILE_SIZE), true))
				.withDepth(1f)
				.withLayer(1)
				.withRotation(direction.getAngle())
				.withRotationOrigin(this.position)
				.build()
				);
	}

}
