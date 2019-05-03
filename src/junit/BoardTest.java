package junit;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.jupiter.api.Test;

import core.ContentManager;
import robotGame.Board;
import robotGame.tiles.BoardTile;

/**
 * Board Generates Board Tiles
 * 
 * @author Jedd Morgan
 * @author Connor Graham
 */

class BoardTest {

	private Board board;

	@BeforeClass
	public void boardTest() {
		
	}
	
	@Before
	public void setup() {
		
	}
	
	@Test
	void loadBoardFormat1Length() {
		
		ContentManager.setRootDirectory("content/");	
		
		ContentManager.loadText("boards/testTiles1.brd","testBoard1");
		board = new Board("testBoard1");
		assertEquals(ContentManager.getTextByName("testBoard1").length - 1, board.getBoardArray()[0].length);
	}
	@Test
	void loadBoardFormat1Width() {
		
		ContentManager.setRootDirectory("content/");	
		
		ContentManager.loadText("boards/testTiles1.brd","testBoard1");
		board = new Board("testBoard1");
		assertEquals(ContentManager.getTextByName("testBoard1")[1].length(), board.getBoardArray().length);
	}
	@Test
	void testNotNull() 
	{
		ContentManager.setRootDirectory("content/");	
		
		ContentManager.loadText("boards/test1x1.brd","testBoard2");
		board = new Board("testBoard2");
		assertNotNull(ContentManager.getTextByName("testBoard2"));
	}
	
	@Test
	void testNotEmpty() 
	{
		ContentManager.setRootDirectory("content/");	
		
		ContentManager.loadText("boards/test1x1.brd","testBoard2");
		board = new Board("testBoard2");
		assertNotNull(ContentManager.getTextByName("testBoard2")[1]);
	}
	
	

}
