package Game;

import Game.Core.IUpdatable;
import Graphics.Color;
import Graphics.IDrawable;
import Graphics.RenderBatch;
import Utils.Region;
import Utils.Vector2;

/**
 * Flag Tile
 * 
 * @author Jedd Morgan, Vanessa Kostadinova
 * @version 01/04/2019
 */
public class FlagTile extends Location {

	public FlagTile(Vector2 placement) {
		super();
		this.location = placement;
	}
	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void act() {
		//No implementation required
	}

	@Override
	public void onLanded(Robot robot) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void draw(RenderBatch b) {
		b.draw("tileFlag", new Region(this.location, new Vector2(64, 64), true), Color.WHITE(), 1f);
	}

	@Override
	public void update(double time) {
		// TODO Auto-generated method stub
		
	}

}
