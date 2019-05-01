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
	
	private final Direction direction;

	public LaserReceiver(Point index, Direction direction) {
		// Sets variables
		super(index);
		this.tag = "LaserReceiver";
		this.sprite = "tileLaserReceiver";
		this.direction = direction;
	}

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
