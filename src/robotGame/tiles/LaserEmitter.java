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

	/** Damage points dealt by the laser.*/
	private final int damage;
	/** Direction laser is facing.*/
	private final Direction direction;
	/** {@link utils.Point Point} where the {@link robotGame.tiles.BoardTile tile} is indexed.*/
	private final Point index;

	/**
	 * Constructs a laser emitter.
	 * @param index {@link utils.Point Point} where the {@link robotGame.tiles.BoardTile tile} is indexed.
	 * @param direction {@link robotGame.Direction Direction} the tile is facing, must be EAST or SOUTH.
	 */
	public LaserEmitter(Point index, Direction direction) {
		super(index);
		
		this.damage = 25;
		this.index = index;
		this.tag = "LaserEmitter";
		
		// Detects correct direction has been given
		if(direction != Direction.EAST || direction != Direction.SOUTH) {
			this.direction = Direction.EAST;
		} else {
			this.direction = direction;
		}
	}

	/**
	 * {@inheritDoc}
	 * Searches tiles for end of board or receiver tile then damages {@link robotGame.Robot Robot}.
	 */
	public void act()
	{
		// Whether the laser is firing horizontally or vertically.
		boolean horizontal;
		// Whether the laser should continue past specified coordinate point.
		boolean viable = true;
		// Variable coordinate
		int coord;
		
		// Sets the coordinate accordingly
		if(direction == Direction.EAST) {
			coord = index.x;
			horizontal = true;
		} else {
			coord = index.y;
			horizontal = false;
		}
		
		// Searches all consecutive tiles until it runs off the board or reaches receiver.
		while(viable) {
			// Point currently being searched.
			Point boardPoint;

			// Updates boardPoint.
			if(horizontal) {
				boardPoint = new Point(coord,index.y);
			} else {
				boardPoint = new Point(index.x,coord);
			}
			
			// Tile currently being searched.
			BoardTile currentTile = Board.getTile(boardPoint);

			// Checks tile is viable.
			if(currentTile == null || currentTile instanceof LaserReceiver) {
				viable = false;
			}

			// Robot currently being acted upon.
			Robot currentRobot = currentTile.getRobot();
			
			// Applies damage if robot exists.
			if(currentRobot != null) {
				currentRobot.addDamage(damage);
			}
			
			coord ++;
		}
	}

	/**
	 * {@inheritDoc}
	 */
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

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void destroy()
	{
		// No implementation required
	}

}
