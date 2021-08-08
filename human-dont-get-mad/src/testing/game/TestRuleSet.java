package testing.game;

import static org.junit.Assert.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.json.JSONObject;
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
            
			for(int i = 1; i < 4; i++) 
			{
				Object[] setUp = {i, testFigure[0], testFigure};
				assertEquals(i==2 ? null:i ,testInstance.invoke(testRuleSet, setUp));
			}			
		}
		catch (Exception e) {e.printStackTrace();}			
	}
	
	//Unit-Test for the rules-method found in the RuleSet class without forcing the turn to be executed and testing the movement from Start to Field.
	@Test
	public void testRulesToField() 
	{
		try 
		{			
			Class<?>[]args = {PlayerColor.class, Figure.class, HashMap.class, Boolean.class};
			
			Method testInstance = RuleSet.class.getDeclaredMethod("rules", args);
			testInstance.setAccessible(true);
			
			Field getFigure = Player.class.getDeclaredField("figures");
			getFigure.setAccessible(true);
			Figure[] testFigure = (Figure[]) getFigure.get(testPlayer);
			Method testSetFigureType = Figure.class.getDeclaredMethod("setType", GamePosition.class);
			testSetFigureType.setAccessible(true);
			testSetFigureType.invoke(testFigure[0], GamePosition.START);
			
            Field testGetObject = Player.class.getDeclaredField("dice");
            
        	Field setDice = Dice.class.getDeclaredField("currentDice");
           	setDice.setAccessible(true);
        	setDice.set(testDice, 6);        
            testGetObject.setAccessible(true);

            testGetObject.set(testPlayer, testDice);
            
            JSONObject expectedObject = new JSONObject();
            expectedObject.put("type", GamePosition.FIELD.toString());
            expectedObject.put("index", 0);

			Object[] setUp = {testColor, testFigure[0] , players, false}; //2 Tests mit true vs false			
			assertEquals(expectedObject.toString() ,(String)testInstance.invoke(testRuleSet, setUp).toString());
		}
		catch (Exception e) {e.printStackTrace();}	
	}
	
	//Unit-Test for the rules-method found in the RuleSet class without forcing the turn to be executed and testing the movement within Field.
	@Test
	public void testRulesField() 
	{
		try 
		{			
			//private JSONObject rules(PlayerColor currentPlayer, Figure currentFigure, HashMap<PlayerColor, Player> players, Boolean execute) 
			Class<?>[]args = {PlayerColor.class, Figure.class, HashMap.class, Boolean.class};
			
			Method testInstance = RuleSet.class.getDeclaredMethod("rules", args);
			testInstance.setAccessible(true);
			
			Field getFigure = Player.class.getDeclaredField("figures");
			getFigure.setAccessible(true);
			Figure[] testFigure = (Figure[]) getFigure.get(testPlayer);
			Method testSetFigureType = Figure.class.getDeclaredMethod("setType", GamePosition.class);
			testSetFigureType.setAccessible(true);
			testSetFigureType.invoke(testFigure[0], GamePosition.FIELD);
			
            Field testGetObject = Player.class.getDeclaredField("dice");
         
        	Field setDice = Dice.class.getDeclaredField("currentDice");
           	setDice.setAccessible(true);
        	setDice.set(testDice, 6);        
            testGetObject.setAccessible(true);

            testGetObject.set(testPlayer, testDice);
            
            JSONObject expectedObject = new JSONObject();
            expectedObject.put("type", GamePosition.FIELD.toString());
            expectedObject.put("index", 6);

			Object[] setUp = {testColor, testFigure[0] , players, false};		
			assertEquals(expectedObject.toString() ,(String)testInstance.invoke(testRuleSet, setUp).toString());
		}
		catch (Exception e) {e.printStackTrace();}	
	}
	
	//Unit-Test for the rules-method found in the RuleSet class without forcing the turn to be executed and testing the movement within Home.
	@Test
	public void testRulesHome() 
	{
		try 
		{			
			Class<?>[]args = {PlayerColor.class, Figure.class, HashMap.class, Boolean.class};
			
			Method testInstance = RuleSet.class.getDeclaredMethod("rules", args);
			testInstance.setAccessible(true);
			
			Field getFigure = Player.class.getDeclaredField("figures");
			getFigure.setAccessible(true);
			Figure[] testFigure = (Figure[]) getFigure.get(testPlayer);
			Method testSetFigureType = Figure.class.getDeclaredMethod("setType", GamePosition.class);
			testSetFigureType.setAccessible(true);
			testSetFigureType.invoke(testFigure[0], GamePosition.HOME);
			
            Field testGetObject = Player.class.getDeclaredField("dice");
            
        	Field setDice = Dice.class.getDeclaredField("currentDice");
           	setDice.setAccessible(true);
        	setDice.set(testDice, 3);        
            testGetObject.setAccessible(true);

            testGetObject.set(testPlayer, testDice);
            
            JSONObject expectedObject = new JSONObject();
            expectedObject.put("type", GamePosition.HOME.toString());
            expectedObject.put("index", 3);

			Object[] setUp = {testColor, testFigure[0] , players, false};			
			assertEquals(expectedObject.toString() ,(String)testInstance.invoke(testRuleSet, setUp).toString());
		}
		catch (Exception e) {e.printStackTrace();}	
	}
	
	//Unit-Test for the rules-method found in the RuleSet class without forcing the turn to be executed and testing the movement from Field to Home.
	@Test
	public void testRulesToHome() 
	{
		try 
		{			
			Class<?>[]args = {PlayerColor.class, Figure.class, HashMap.class, Boolean.class};
			
			Method testInstance = RuleSet.class.getDeclaredMethod("rules", args);
			testInstance.setAccessible(true);
			
			Field getFigure = Player.class.getDeclaredField("figures");
			getFigure.setAccessible(true);
			Figure[] testFigure = (Figure[]) getFigure.get(testPlayer);
			Method testSetFigureType = Figure.class.getDeclaredMethod("setType", GamePosition.class);
			testSetFigureType.setAccessible(true);
			testSetFigureType.invoke(testFigure[0], GamePosition.FIELD);
			Method testSetFigureIndex = Figure.class.getDeclaredMethod("setIndex", Integer.class);
			testSetFigureIndex.setAccessible(true);			
			testSetFigureIndex.invoke(testFigure[0], 38);
			
            Field testGetObject = Player.class.getDeclaredField("dice");
           
        	Field setDice = Dice.class.getDeclaredField("currentDice");
           	setDice.setAccessible(true);
        	setDice.set(testDice, 4);        
            testGetObject.setAccessible(true);

            testGetObject.set(testPlayer, testDice);
            
            JSONObject expectedObject = new JSONObject();
            expectedObject.put("type", GamePosition.HOME.toString());
            expectedObject.put("index", 2);

			Object[] setUp = {testColor, testFigure[0] , players, false};		
			assertEquals(expectedObject.toString() ,(String)testInstance.invoke(testRuleSet, setUp).toString());
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
}
