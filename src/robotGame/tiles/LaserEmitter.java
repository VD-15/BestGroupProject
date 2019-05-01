package robotGame.tiles;

import core.IUpdatable;
import graphics.Color;
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
 * @version 01/05/2019
 */
public class LaserEmitter extends BoardTile implements IUpdatable {

	/** Damage points dealt by the laser.*/
	private static final int damage = 25;
	/** Direction laser is facing.*/
	private final Direction direction;
	/** {@link utils.Point Point} where the {@link robotGame.tiles.BoardTile tile} is indexed.*/
	private final Point index;

	private float laserWidth;

	/**
	 * Constructs a laser emitter.
	 * @param index {@link utils.Point Point} where the {@link robotGame.tiles.BoardTile tile} is indexed.
	 * @param direction {@link robotGame.Direction Direction} the tile is facing, must be EAST or SOUTH.
	 */
	public LaserEmitter(Point index, Direction direction) {
		super(index);

		// Sets variables.
		this.index = index;
		this.tag = "LaserEmitter";
		this.sprite = "tileLaserEmitter";
		this.laserWidth = 0f;

		//Detects correct direction has been given
		if(direction != Direction.EAST && direction != Direction.SOUTH) {
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
		while(true) {
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
			if(currentTile == null || (currentTile instanceof LaserReceiver)) {
				break;
			}

			// Robot currently being acted upon.
			Robot currentRobot = currentTile.getRobot();

			// Applies damage if robot exists.
			if(currentRobot != null) {
				currentRobot.addDamage(damage);
				break;
			}

			laserWidth++;
			// Increments coord for the search to continue.
			coord ++;
		}
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

		float width = 0f;
		if (laserWidth != 0) {
			width = (laserWidth * TILE_SIZE) + TILE_SIZE;
		}
		Region region = new Region(new Vector2(this.position.clone().x - (TILE_SIZE / 2), this.position.y), new Vector2(width , 2f), false);
		
		
		b.draw(new RenderInstance()
				.withTexture("uiBlank")
				.withColor(new Color(1f,0f,0f))
				.withDestinationRegion(region)
				.withDepth(4f)
				.withLayer(1)
				.withRotation(direction.add(-1).getAngle())
				.withRotationOrigin(this.position)
				.build()
				);
	}
	
	private float deltaT;
	@Override
	public void update(double time) {

		if(laserWidth > 0) {
			deltaT += time;
			if(deltaT > 1.5f) {
				deltaT = 0;
				laserWidth = 0;
			}
		}
	}
}
