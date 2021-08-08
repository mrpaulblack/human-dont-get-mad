package testing.game;

import static org.junit.Assert.*;
import java.lang.reflect.Method;

import org.junit.Before;
import org.junit.Test;
import game.Figure;
import game.GamePosition;
import game.PlayerColor;


public class TestFigure 
{

    public Figure testFigure;
    

    @Before
    public void testClass() { testFigure = new Figure(1); }
    


    @Test
    public void testGetStartIndex() 
    {
        try 
        {	           
            Method testInstance = Figure.class.getDeclaredMethod("getStartIndex" );
            testInstance.setAccessible(true);
            Integer expectedInteger = 1;
            Integer testInteger = 2;
            assertEquals(testInteger, testInstance.invoke(testFigure));
        } 
        catch (Exception e) {e.printStackTrace();}	        
    }      
}
