package robotGame;

import java.util.Queue;

import core.Game;
import core.GameObject;
import core.IUpdatable;
import robotGame.tiles.BoardTile;
import utils.LogSeverity;
import utils.Logger;

public class GameManager extends GameObject implements IUpdatable {

	
	private Board board;
	
	private static final double TURN_TIME = 1;
	
	private Queue<Robot> robots;

	@Override
	public void init() {
		board = new Board();
		Game.instantiate(board);
		
		
	}

	
	/**
	 * Executes a round, round is made up of turns one for each player and one for the board
	 */
	private void round() {
		for(int i = 0; i < robots.size(); i++) {
			//Each Player Turn
			Robot r = robots.poll();
			r.act();
			
			robots.add(r);
		}
		robots.add(robots.poll());
		
		//
		for(BoardTile tile : board.getBoardTiles()) {
			tile.act();
		}
		
		Logger.log(this, LogSeverity.INFO, "Done a turn");
		
	}
	
	double deltaT = 0;
	
	@Override
	public void update(double time) {
		deltaT += time;
		
		if (deltaT > TURN_TIME) {
			deltaT = 0;
			
			//turn();
			
			
		}
	}
}
