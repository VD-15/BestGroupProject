package robotGame.tiles;

import graphics.RenderBatch;
import graphics.RenderInstance;
import utils.Point;
import utils.Region;
import utils.Vector2;

/**
 * Laser Receiver
 * 
 * @author Vanessa Kostadinova
 * @version 30/05/2019
 */
public class LaserReceiver extends BoardTile {

	public LaserReceiver(Point index) {
		super(index);
		this.tag = "LaserReceiver";
	}
	
	@Override
	public void draw(RenderBatch b)
	{
		b.draw(new RenderInstance()
				.withTexture("tileLaserReceiver")
				.withDestinationRegion(new Region(this.position, new Vector2(TILE_SIZE), true))
				.withDepth(1f)
				.withLayer(1)
				.build()
				);
	}

}
