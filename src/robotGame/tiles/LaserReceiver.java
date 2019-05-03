package robotGame.tiles;

import graphics.RenderBatch;
import graphics.RenderInstance;
import robotGame.Direction;
import utils.Point;
import utils.Region;
import utils.Vector2;

/**
 * Laser Receiver
 * 
 * @author Vanessa Kostadinova
 * @version 01/06/2019
 */
public class LaserReceiver extends BoardTile {
	
	/** Direction the laser receiver faces.*/
	private final Direction direction;

	/**
	 * Constructs Laser Receiver.
	 * @param index {@link utils.Point Point} where the {@link robotGame.tiles.BoardTile tile} is indexed.
	 * @param direction The direction the receiver faces, can only be WEST or NORTH.
	 */
	public LaserReceiver(Point index, Direction direction) {
		// Sets variables
		super(index);
		this.tag = "LaserReceiver";
		this.sprite = "tileLaserReceiver";
		
		if(direction != Direction.WEST && direction != Direction.NORTH) {
			this.direction = Direction.EAST;
		} else {
			this.direction = direction;
		}
	}

	/**
	 * Returns the direction the receiver is facing.
	 * @return direction
	 */
	public Direction getDirection() {
		return direction;
	}
	
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
