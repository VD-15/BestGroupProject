package Game;

import Game.Core.IUpdatable;
import Graphics.Color;
import Graphics.IDrawable;
import Graphics.RenderBatch;
import Utils.Region;
import Utils.Vector2;

/**
 * Belt Tile
 * 
 * @author Jedd Morgan
 * @version 25/03/2019
 */
public class BeltTile extends Location {
	
	private Direction direction;

	/**
	 * 
	 * @param placement The location where the tile is drawn.
	 * @param direction The direction the tile acts in.
	 */
	public BeltTile(Vector2 placement, Direction direction) {
		super();
		this.location = placement;
		this.direction = direction;
	}
	
	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void act() {
		//currentRobot.move();
	}

	@Override
	public void onLanded(Robot robot) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(double time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void draw(RenderBatch b) {
		b.draw("tileBelt", new Region(this.location, new Vector2(64, 64), true), Color.WHITE(), 1f);
		
	}

}
