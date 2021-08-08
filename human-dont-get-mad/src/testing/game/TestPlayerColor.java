package testing.game;

import static org.junit.Assert.*;
import org.junit.Test;
import game.PlayerColor;

/**
* <h1>Unit-Test for game.PlayerColor</h1>
* <p>This is a unit test of the methods found in the PlayerColor class within the game package</p>

*
* @author  Konrad Krueger
* @version 1.0
* @since   2021-07-24
* @apiNote MAEDN 3.0
* @implNote JUnit 4
*/

public class TestPlayerColor 
{
	
    //Test of the convert toString method for the Red-case
	@Test
	public void testToStringRed()	
	{ 
		String testString = "red";
		assertEquals(PlayerColor.RED.toString(), testString);	
	}
	
    //Test of the convert toString method for the Blue-case
	@Test
	public void testToStringBlue()	
	{ 
		String testString = "blue";
		assertEquals(PlayerColor.BLUE.toString(), testString);	
	}
	
    //Test of the convert toString method for the Green-case
	@Test
	public void testToStringGreen()	
	{ 
		String testString = "green";
		assertEquals(PlayerColor.GREEN.toString(), testString);	
	}
	
    //Test of the convert toString method for the Yellow-case
	@Test
	public void testToStringYellow()	
	{ 
		String testString = "yellow";
		assertEquals(PlayerColor.YELLOW.toString(), testString);	
	}	
}
