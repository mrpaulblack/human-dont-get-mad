package testing.game;

import static org.junit.Assert.*;
import org.junit.Test;
import game.GamePosition;

/**
* <h1>Unit-Test for game.GamePosition</h1>
* <p>This is a unit test of the methods found in the GamePosition class within the game package</p>

*
* @author  Konrad Krueger
* @version 1.0
* @since   2021-07-24
* @apiNote MAEDN 3.0
* @implNote JUnit 4
*/

public class TestGamePosition 
{	
    //Test of the convert toString method for the Start-case
	@Test
	public void testToStringStart()	
	{ 
		String testString = "start";
		assertEquals(GamePosition.START.toString(), testString);	
	}
	
    //Test of the convert toString method for the Home-case
	@Test
	public void testToStringHome()	
	{ 
		String testString = "home";
		assertEquals(GamePosition.HOME.toString(), testString); 
	}
	
    //Test of the convert toString method for the Field-case
	@Test
	public void testToStringField()	
	{ 
		String testString = "field";
		assertEquals(GamePosition.FIELD.toString(), testString);	
	}
}
