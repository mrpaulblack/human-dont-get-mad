package testing.game;

import static org.junit.Assert.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import org.junit.Before;
import org.junit.Test;
import game.RuleSet;
import game.PlayerColor;
import game.Player;
import game.Dice;
import game.Figure;
import game.GamePosition;

import java.util.HashMap;

/**
* <h1>Unit-Test for game.RuleSet</h1>
* <p>This is a unit test of the methods found in the RuleSet class within the game package</p>
* <b>Note:</b> Private methods were tested as well in order to ensure error-free 
* execution of core server-side functions. The "trick" to access private methods was initially found here: 
* https://www.artima.com/articles/testing-private-methods-with-junit-and-suiterunner
* and was chosen because it does not use external libraries or frameworkds.  
* Tests for protected methods are in here as well to have a clear 
* dividing line between tests and live-code.
*
* @version 1.0
* @since   2021-08-07
* @apiNote MAEDN 3.0
* @implNote JUnit 4
*/


public class TestRuleSet 
{
	//Declaration of required variables for testing.
	public RuleSet testRuleSet;
	public PlayerColor testColor;
	public Player testPlayer;
	public Dice testDice;
	public HashMap<PlayerColor, Player> players;
	public GamePosition testPositionHome;
	public Figure[] testFigureArray;
	
	//Setup to create the environment for the tested methods	
	@Before
	public void testRuleSetConst() 
	{ 
		testRuleSet = new RuleSet(); 
		testColor = PlayerColor.RED;
		testPlayer = new Player(testColor, "John Doe", "TestLudo", 1.0f, false);
		testDice = new Dice();
		players = new HashMap<PlayerColor, Player>();
		players.put(testColor, testPlayer);
		testPositionHome = GamePosition.HOME;		 
	}
	
	// Unit-Test for the method execute within the RuleSet-class using a dice roll of six which should return a true
	@Test
	public void testExecuteSix() 
	{
		try 
        {	           
        	Class<?>[]args = {PlayerColor.class, Figure.class, HashMap.class};        	
        	Field getFigure = Player.class.getDeclaredField("figures");
        	getFigure.setAccessible(true);
        	Field setDice = Dice.class.getDeclaredField("currentDice");
           	setDice.setAccessible(true);
        	setDice.set(testDice, 6);
            Method testInstance = RuleSet.class.getDeclaredMethod("execute", args);
            testInstance.setAccessible(true);
            Field testGetObject = Player.class.getDeclaredField("dice");
            testGetObject.setAccessible(true);
            Figure[] testFigure = (Figure[]) getFigure.get(testPlayer);
            Object[] setUp = {testColor, testFigure[0], players};
            testGetObject.set(testPlayer, testDice);

            assertTrue((Boolean)testInstance.invoke(testRuleSet, setUp));
        } 
        catch (Exception e) {e.printStackTrace();}	        
	  }
	
	// Unit-Test for the method execute within the RuleSet-class using a dice roll of every other possible dice roll between 1-5 which should return a false
	@Test
	public void testExecuteOther() 
	{
		for(int i = 1; i < 6; i++) 
		{
			try 
	        {	           
	        	Class<?>[]args = {PlayerColor.class, Figure.class, HashMap.class};        	
	        	Field getFigure = Player.class.getDeclaredField("figures");
	        	getFigure.setAccessible(true);
	        	Field setDice = Dice.class.getDeclaredField("currentDice");
	           	setDice.setAccessible(true);
	        	setDice.set(testDice, i);
	            Method testInstance = RuleSet.class.getDeclaredMethod("execute", args);
	            testInstance.setAccessible(true);
	            Field testGetObject = Player.class.getDeclaredField("dice");
	            testGetObject.setAccessible(true);
	            Figure[] testFigure = (Figure[]) getFigure.get(testPlayer);
	            Object[] setUp = {testColor, testFigure[0], players};
	            testGetObject.set(testPlayer, testDice);

	            assertFalse((Boolean)testInstance.invoke(testRuleSet, setUp));
	        } 
	        catch (Exception e) {e.printStackTrace();}	        
		  } 
		}
	
	// Unit-Test for the method getNewHomePosition within the RuleSet-class. Testing for each possible Position with different figures.
		@Test
	public void testGetNewHomePosition()
	{
		try
		{
			Class<?>[]args = {Integer.class, Figure.class, Figure[].class};
			
			Method testInstance = RuleSet.class.getDeclaredMethod("getNewHomePosition", args);
			testInstance.setAccessible(true);
			Field getFigure = Player.class.getDeclaredField("figures");
			getFigure.setAccessible(true);
			Method testSetFigureType = Figure.class.getDeclaredMethod("setType", GamePosition.class);
			testSetFigureType.setAccessible(true);
			Figure[] testFigure = (Figure[]) getFigure.get(testPlayer);	
			testSetFigureType.invoke(testFigure[1], GamePosition.START);
			testSetFigureType.invoke(testFigure[2], GamePosition.HOME);
			testSetFigureType.invoke(testFigure[3], GamePosition.FIELD);
            
			//testFigure[0].	
			for(int i = 1; i < 4; i++) 
			{
				Object[] setUp = {i, testFigure[0], testFigure};
				assertEquals(i==2 ? null:i ,testInstance.invoke(testRuleSet, setUp));
			}			
		}
		catch (Exception e) {e.printStackTrace();}			
	}
	

	
	
	

	//Unit-Test for the getNewBoardIndex-Method (private) for the regular move on board using a random dice value and doing 100 iterations of the test in order to test multiple outcomes.
	@Test
	public void TestGetNewBoardIndexRegular() 
	{
	  for(int i = 0; i < 100; i++)
	  {	
        try 
        {	           
        	Class<?>[]args = {Integer.class, PlayerColor.class, Player.class};
        	
            Method testInstance = RuleSet.class.getDeclaredMethod("getNewBoardIndex", args);
            Field testGetObject = Player.class.getDeclaredField("dice");
            Method testGetDice = Dice.class.getDeclaredMethod("set");
            testGetObject.setAccessible(true);
            testInstance.setAccessible(true);
            testGetDice.setAccessible(true);          
       
            testGetDice.invoke(testDice);
            testGetObject.set(testPlayer, testDice);
            
            Object[] setUp = {0, testColor, testPlayer};            
            
            int expectedInteger = 0;
            
            assertEquals(expectedInteger, (int)testInstance.invoke(testRuleSet, setUp), 6);
        } 
        catch (Exception e) {e.printStackTrace();}	        
	  } 
	}	
	
	/*Unit-Test for the getNewBoardIndex-Method (private) for the regular move around board using a random dice value and doing 100 iterations of the test in order to test multiple outcomes.
		@Test
		public void TestGetNewBoardIndexAround() 
		{
		  for(int i = 0; i < 10; i++)
		  {	
	        try 
	        {	           
	        	Class<?>[]args = {Integer.class, PlayerColor.class, Player.class};
	        	
	            Method testInstance = RuleSet.class.getDeclaredMethod("getNewBoardIndex", args);
	            Field testGetObject = Player.class.getDeclaredField("dice");
	            Method testGetDice = Dice.class.getDeclaredMethod("set");
	            testGetObject.setAccessible(true);
	            testInstance.setAccessible(true);
	            testGetDice.setAccessible(true);            
	            
	            //testGetObject.
	            testGetDice.invoke(testDice);
	            testGetObject.set(testPlayer, testDice);
	            
	            Object[] setUp = {40, testColor, testPlayer};            
	            
	            int expectedInteger = -6;
	            
	            assertEquals(expectedInteger, (int)testInstance.invoke(testRuleSet, setUp), 12);
	        } 
	        catch (Exception e) {e.printStackTrace();}	        
		  } 
		}*/
	
	
	
	
		
		
	
	
	
}
