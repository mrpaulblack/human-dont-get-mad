package testing.server;

import static org.junit.Assert.assertEquals;
import java.lang.reflect.Method;
import server.ServerController;
import org.junit.Before;
import org.junit.Test;
import game.PlayerColor;

/**
* <h1>Unit-Test for server.ServerController</h1>
* <p>This is a unit test of the methods found in the ServerController class within the server package</p>
* <b>Note:</b> Private methods were tested as well in order to ensure error-free 
* execution of core server-side functions. The "trick" to access private methods was initially found here: 
* https://www.artima.com/articles/testing-private-methods-with-junit-and-suiterunner
* and was chosen because it does not use external libraries or frameworkds.  
* Tests for protected methods are in here as well to have a clear 
* dividing line between tests and live-code.
*
* @author  Konrad Krueger
* @version 1.0
* @since   2021-07-23
* @apiNote MAEDN 3.0
*/


public class ServerControllerTest 
{	
	    public ServerController testController;
	    
	    //Declaring an instance of the ServerController class as general setup for tests.
	    @Before
	    public void beforeClass() {
	        testController = new ServerController();
	    }

	    //Test of the color decoding method from string to enum for the color red.
	    @Test
	    public void testDecodeColorRed() 
	    {
	        try 
	        {	           
	            Method testinstance = ServerController.class.getDeclaredMethod("decodeColor", String.class);
	            testinstance.setAccessible(true);
	            assertEquals(PlayerColor.RED, testinstance.invoke(testController, "red"));
	        } 
	        catch (Exception e) {e.printStackTrace();}	        
	    }
	    
	    //Test of the color decoding method from string to enum for the color blue.
	    @Test
	    public void testDecodeColorBlue() 
	    {
	        try 
	        {	           
	            Method testinstance = ServerController.class.getDeclaredMethod("decodeColor", String.class);
	            testinstance.setAccessible(true);
	            assertEquals(PlayerColor.BLUE, testinstance.invoke(testController, "blue"));
	        } 
	        catch (Exception e) {e.printStackTrace();}	        
	    }
	    
	    //Test of the color decoding method from string to enum for the color green.
	    @Test
	    public void testDecodeColorGreen() 
	    {
	        try 
	        {	           
	            Method testinstance = ServerController.class.getDeclaredMethod("decodeColor", String.class);
	            testinstance.setAccessible(true);
	            assertEquals(PlayerColor.GREEN, testinstance.invoke(testController, "green"));
	        } 
	        catch (Exception e) {e.printStackTrace();}	        
	    }
	    
	    //Test of the color decoding method from string to enum for the color yellow.
	    @Test
	    public void testDecodeColorYellow() 
	    {
	        try 
	        {	           
	            Method testinstance = ServerController.class.getDeclaredMethod("decodeColor", String.class);
	            testinstance.setAccessible(true);
	            assertEquals(PlayerColor.YELLOW, testinstance.invoke(testController, "yellow"));
	        } 
	        catch (Exception e) {e.printStackTrace();}	        
	    }
	    
	    //Test of the color decoding method from string to enum for the default case..
	    @Test
	    public void testDecodeColorDefault() 
	    {
	        try 
	        {	           
	            Method testinstance = ServerController.class.getDeclaredMethod("decodeColor", String.class);
	            testinstance.setAccessible(true);
	            assertEquals(null, testinstance.invoke(testController, ""));
	        } 
	        catch (Exception e) {e.printStackTrace();}	        
	    }
	    
	    public void testSendWelcome()
	    {
	    	
	    	
	    }
}