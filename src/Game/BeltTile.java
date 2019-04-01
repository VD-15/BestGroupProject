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

	public BeltTile(Vector2 placement) {
		super();
		this.location = placement;
	}
	
	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void act() {
		// TODO Auto-generated method stub
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
		b.draw("", new Region(this.location, new Vector2(64, 64), true), Color.WHITE(), 1f);
		
	}

}
