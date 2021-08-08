package testing.game;

import static org.junit.Assert.*;
import java.lang.reflect.Method;
import org.junit.Before;
import org.junit.Test;
import game.Figure;
import game.GamePosition;
import game.PlayerColor;

/**
* <h1>Unit-Test for game.Figure</h1>
* <p>This is a unit test of the methods found in the Figure class within the game package</p>

*
* @version 1.0
* @since   2021-07-24
* @apiNote MAEDN 3.0
* @implNote JUnit 4
*/

public class TestFigure 
{
	//Declaring an instance of the Figure class as general setup for tests.
    public Figure testFigure;
    
    //Constructor for Figure class
    @Before
    public void testClass() { testFigure = new Figure(1); }

    //Test for the correct assessment of the start index. 
    @Test
    public void testGetStartIndex() 
    {
        try 
        {	           
            Method testInstance = Figure.class.getDeclaredMethod("getStartIndex" );
            testInstance.setAccessible(true);
            Integer testInteger = 1;
            assertEquals(testInteger, testInstance.invoke(testFigure));
        } 
        catch (Exception e) {e.printStackTrace();}	        
    }      
}
