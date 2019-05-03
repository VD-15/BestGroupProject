package junit;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import core.ContentManager;
import robotGame.Board;
import robotGame.Direction;
import robotGame.Robot;
import robotGame.tiles.BoardTile;
import robotGame.tiles.FlagTile;
import robotGame.tiles.GearTile;
import robotGame.tiles.PitTile;
import utils.Point;

class tileTest {
	/**
	 * Tile Test
	 * 
	 * @author Jedd Morgan
	 * @author Connor Graham
	 * @author Vanessa Kostadinova
	 */
	@BeforeEach
	void setUp() throws Exception {
		ContentManager.setRootDirectory("content/boards/");
	}

	@Test
	void currentRobotTest() {
		//new GameManager
	}
	
	@Test
	void pitTest() {
		ContentManager.loadText("only-pits.brd", "only-pits");
		Board board = new Board("only-pits");
		for(BoardTile b : board.getBoardTiles()) {
			assertTrue(b instanceof PitTile);
		}
	}
	
	@Test
	void flagTest() {
		ContentManager.loadText("only-flags.brd", "only-flags");
		Board board = new Board("only-flags");
		for(BoardTile b : board.getBoardTiles()) {
			assertTrue(b instanceof FlagTile);
		}
	}
	
	@Test
	void gearTest() {
		ContentManager.loadText("only-gears.brd", "only-gears");
		Board board = new Board("only-gears");
		for(BoardTile b : board.getBoardTiles()) {
			assertTrue(b instanceof GearTile);
		}
	}
	
	@Test
	void notGearTest() {
		ContentManager.loadText("only-flags.brd","not-gears");
		Board board = new Board("not-gears");
		for(BoardTile b : board.getBoardTiles()) {
			assertFalse(b instanceof GearTile);
		}
	}
	

}
