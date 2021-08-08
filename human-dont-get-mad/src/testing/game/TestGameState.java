package testing.game;

import static org.junit.Assert.*;
import org.junit.Test;
import game.GameState;

/**
* <h1>Unit-Test for game.GameState</h1>
* <p>This is a unit test of the methods found in the GameState class within the game package</p>

*
* @author  Konrad Krueger
* @version 1.0
* @since   2021-07-24
* @apiNote MAEDN 3.0
* @implNote JUnit 4
*/

public class TestGameState 
{	
    //Test of the convert toString method for the Waiting For Players-case
	@Test
	public void testToStringWaitngForPlayers()	
	{ 
		String testString = "waitingForPlayers";
		assertEquals(GameState.WAITINGFORPLAYERS.toString(), testString);	
	}
	
    //Test of the convert toString method for the Running-case
	@Test
	public void testToStringRunning()	
	{ 
		String testString = "running";
		assertEquals(GameState.RUNNING.toString(), testString); 
	}
	
    //Test of the convert toString method for the Finished-case
	@Test
	public void testToStringFinished()	
	{ 
		String testString = "finished";
		assertEquals(GameState.FINISHED.toString(), testString);	
	}
}
