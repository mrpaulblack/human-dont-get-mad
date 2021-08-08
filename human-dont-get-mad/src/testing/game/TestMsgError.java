package testing.game;

import static org.junit.Assert.*;
import org.junit.Test;
import game.MsgError;

/**
* <h1>Unit-Test for game.MsgError</h1>
* <p>This is a unit test of the methods found in the MsgError class within the game package</p>

*
* @version 1.0
* @since   2021-07-24
* @apiNote MAEDN 3.0
* @implNote JUnit 4
*/

public class TestMsgError 
{	
    //Test of the convert toString method for the Unsupported Message Type-case
	@Test
	public void testToStringUnsupportedMessageType()	
	{ 
		String testString = "unsupportedMessageType";
		assertEquals(MsgError.UNSUPPORTEDMESSAGETYPE.toString(), testString);	
	}
	
    //Test of the convert toString method for the Unsupported Protocol Version-case
	@Test
	public void testToStringUnsupportedProtocolVersion()	
	{ 
		String testString = "unsupportedProtocolVersion";
		assertEquals(MsgError.UNSUPPORTEDPROTOCOLVERSION.toString(), testString);	
	}
	
    //Test of the convert toString method for the Server Full-case
	@Test
	public void testToStringServerFull()	
	{ 
		String testString = "serverFull";
		assertEquals(MsgError.SERVERFULL.toString(), testString);	
	}
	
    //Test of the convert toString method for the Illegal Move-case
	@Test
	public void testToStringIllegalMove()	
	{ 
		String testString = "illegalMove";
		assertEquals(MsgError.ILLEGALMOVE.toString(), testString);	
	}
	
    //Test of the convert toString method for the Not Your Turn-case
	@Test
	public void testToStringNotYourTurn()	
	{ 
		String testString = "notYourTurn";
		assertEquals(MsgError.NOTYOURTURN.toString(), testString);	
	}
	
    //Test of the convert toString method for the Unknown-case
	@Test
	public void testToStringUnknown()	
	{ 
		String testString = "unknown";
		assertEquals(MsgError.UNKNOWN.toString(), testString);	
	}	
}
