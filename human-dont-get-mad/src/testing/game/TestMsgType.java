package testing.game;

import static org.junit.Assert.*;
import org.junit.Test;
import game.MsgType;

/**
* <h1>Unit-Test for game.MsgType</h1>
* <p>This is a unit test of the methods found in the MsgType class within the game package</p>

*
* @version 1.0
* @since   2021-07-24
* @apiNote MAEDN 3.0
* @implNote JUnit 4
*/

public class TestMsgType 
{	
    //Test of the convert toString method for the Register-case
	@Test
	public void testToStringRegister()	
	{ 
		String testString = "register";
		assertEquals(MsgType.REGISTER.toString(), testString);	
	}
	
    //Test of the convert toString method for the Ready-case
	@Test
	public void testToStringReady()	
	{ 
		String testString = "ready";
		assertEquals(MsgType.READY.toString(), testString);	
	}
	
    //Test of the convert toString method for the Move-case
	@Test
	public void testToStringMove()	
	{ 
		String testString = "move";
		assertEquals(MsgType.MOVE.toString(), testString);		
	}
	
    //Test of the convert toString method for the Welcome-case
	@Test
	public void testToStringWelcome()	
	{ 
		String testString = "welcome";
		assertEquals(MsgType.WELCOME.toString(), testString);	
	}
	
    //Test of the convert toString method for the Assign Color-case
	@Test
	public void testToStringAssignColor()	
	{ 
		String testString = "assignColor";
		assertEquals(MsgType.ASSIGNCOLOR.toString(), testString);	
	}
	
    //Test of the convert toString method for the Update-case
	@Test
	public void testToUpdate()	
	{ 
		String testString = "update";
		assertEquals(MsgType.UPDATE.toString(), testString);	
	}	
	
    //Test of the convert toString method for the Turn-case
	@Test
	public void testToTurn()	
	{ 
		String testString = "turn";
		assertEquals(MsgType.TURN.toString(), testString);	
	}	
	
    //Test of the convert toString method for the Player Disconnected-case
	@Test
	public void testToPlayerDisconnected()	
	{ 
		String testString = "playerDisconnected";
		assertEquals(MsgType.PLAYERDISCONNECTED.toString(), testString);	
	}
	
    //Test of the convert toString method for the Message-case
	@Test
	public void testToMessage()	
	{ 
		String testString = "message";
		assertEquals(MsgType.MESSAGE.toString(), testString);	
	}	
	
    //Test of the convert toString method for the Error-case
	@Test
	public void testToError()	
	{ 
		String testString = "error";
		assertEquals(MsgType.ERROR.toString(), testString);		
	}		
}



