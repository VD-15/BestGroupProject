package junit;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import core.ContentManager;
import robotGame.Board;
import robotGame.Direction;
import robotGame.Instruction;
import robotGame.Robot;
import utils.Point;
/**
 * Robot Test
 * 
 * @author Jedd Morgan
 * @author Connor Graham
 */
class RobotTest {
	
	private static Robot robot;
	
	@BeforeAll
	static void setup() 
	{
		ContentManager.setRootDirectory("content/boards/");	
		robot = new Robot(new Point(), 0);
	}
	
	@Test
	void testHealth() {
		assertEquals(robot.getHealth(), 5);
	}
	
	@Test
	void testAddDamage() 
	{
		Robot robot2 = new Robot(new Point(), 0);
		robot2.addDamage();
		assertEquals(robot2.getHealth(), 4);
	}
	
	@Test
	void testDefaultDirection() 
	{
		assertEquals(robot.getDefaultDirection(), Direction.NORTH);
	}
	
	@Test
	void testFacingDirection() 
	{
		Robot robot2 = new Robot(new Point(), 0);
		robot2.rotate(1);
		assertEquals(robot2.getFacingDirection(), Direction.EAST);
	}
	
	@Test
	void testResetLocation() 
	{
		ContentManager.loadText("empty-2x40.brd", "2x40");
		Board board = new Board("2x40");
		Robot robot2 = new Robot(new Point(0,0), 0);
		robot2.move(Direction.NORTH, 1);
		assertEquals(robot2.getIndex().y, 0);
	}
	
	@Test
	void testInstructions() 
	{
		ContentManager.loadText("empty-2x40.brd", "2x40");
		Board board = new Board("2x40");
		Robot robot2 = new Robot(new Point(0,0), 0);
		Instruction[] instructions = {Instruction.FORWARD};
		robot2.setInstructions(instructions);
		robot2.act();
		assertEquals(robot2.getIndex().y, 0);
	}
	

}
