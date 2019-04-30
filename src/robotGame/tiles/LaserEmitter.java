package robotGame.tiles;

import graphics.RenderBatch;
import graphics.RenderInstance;
import robotGame.Board;
import robotGame.Direction;
import robotGame.Robot;
import utils.Point;
import utils.Region;
import utils.Vector2;
/**
 * Laser Emitter
 * 
 * @author Vanessa Kostadinova
 * @version 30/05/2019
 */
public class LaserEmitter extends BoardTile {

	/** Damage points dealt by the laser*/
	private final int damage;
	/** Direction laser is facing*/
	private final Direction direction;
	/** @see utils.Point of the tile*/
	private final Point index;

	/**
	 * 
	 * @param index
	 * @param direction
	 */
	public LaserEmitter(Point index, Direction direction) {
		super(index);
		this.damage = 25;

		if(direction != Direction.EAST || direction != Direction.SOUTH) {
			this.direction = Direction.EAST;
		} else {
			this.direction = direction;
		}
		this.index = index;
		this.tag = "LaserEmitter";
	}

	/**
	 * Searches tiles for end of board or receiver tile.
	 */
	public void act()
	{
		boolean horizontal;
		int coord;
		if(direction == Direction.EAST) {
			coord = index.x;
			horizontal = true;
		} else {
			coord = index.y;
			horizontal = false;
		}

		boolean viable = true;

		while(viable) {

			Point boardPoint;

			if(horizontal) {
				boardPoint = new Point(coord,index.y);
			} else {
				boardPoint = new Point(index.x,coord);
			}

			BoardTile currentTile = Board.getTile(boardPoint);

			if(currentTile == null || currentTile instanceof LaserReceiver) {
				viable = false;
			}

			Robot currentRobot = currentTile.getRobot();
			if(currentRobot != null) {
				currentRobot.addDamage(damage);
			}
			coord ++;
		}
	}

	@Override
	public void draw(RenderBatch b)
	{
		b.draw(new RenderInstance()
				.withTexture("tileLaserEmitter")
				.withDestinationRegion(new Region(this.position, new Vector2(TILE_SIZE), true))
				.withDepth(1f)
				.withLayer(1)
				.build()
				);
	}

	@Override
	public void destroy()
	{
		// No implementation required

	}

}
