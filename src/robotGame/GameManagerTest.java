package Game;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Game.Core.GameObject;
import Utils.LogSeverity;
import Utils.Logger;

class GameManagerTest {
	
	GameManager g;

	@BeforeEach
	void setUp() throws Exception {
		
		g = new GameManager();
		
	}

	@Test
	void formatTest() {
		try {
			assertEquals(g.getFormat(new File("Resources/boards/testboard.brd")), 1);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
	}
	
	@Test
	void LineLengthTest() {
		try {
			assertEquals(g.getLineLength(new File("Resources/boards/testboard.brd")), 9);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
