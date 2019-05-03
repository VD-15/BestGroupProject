package junit;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import core.ContentManager;
import robotGame.Board;
import robotGame.tiles.BoardTile;

/**
 * Board Test
 * 
 * @author Jedd Morgan
 * @author Connor Graham
 */

class BoardTest {

	private static final String[] boardString = {"empty-1x1.brd", "empty-1x2.brd", "empty-2x40.brd"};

	@BeforeAll
	public static void boardTest() {

		ContentManager.setRootDirectory("content/boards/testBoards/");	
		for(String b : boardString) {
			ContentManager.loadText(b , b);
		}
	}

	static Stream<String> getBoards() {
		return Stream.of(boardString);
	}

	@BeforeEach
	public void setup() {


	}

	@ParameterizedTest
	@MethodSource("getBoards")
	void loadBoardFormat1Length(String boardName) {
		Board board = new Board(boardName);
		assertEquals(ContentManager.getTextByName(boardName).length -1, board.getBoardArray()[0].length);
	} 

	@ParameterizedTest
	@MethodSource("getBoards")
	void loadBoardFormat1Width(String boardName) {
		Board board = new Board(boardName);
		assertEquals(ContentManager.getTextByName(boardName)[1].length(), board.getBoardArray().length);
	}

	@ParameterizedTest
	@MethodSource("getBoards")
	void testNotNull(String boardName) 
	{
		assertNotNull(ContentManager.getTextByName(boardName));
	}

	@ParameterizedTest
	@MethodSource("getBoards")
	void testNotEmpty(String boardName) 
	{
		assertNotNull(ContentManager.getTextByName(boardName)[1]);
	}

}
