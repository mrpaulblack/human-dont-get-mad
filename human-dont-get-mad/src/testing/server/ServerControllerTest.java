package testing.server;

import static org.junit.Assert.assertEquals;
import java.lang.reflect.Method;
import server.ServerController;
import org.junit.Before;
import org.junit.Test;
import game.PlayerColor;

public class ServerControllerTest {
	
	    public ServerController testController;
	    
	    @Before
	    public void beforeClass() {
	        testController = new ServerController();
	    }

	    @Test
	    public void unitTest() {
	        try {	           
	            Method method = ServerController.class.getDeclaredMethod("decodeColor", String.class);
	            method.setAccessible(true);
	            assertEquals(PlayerColor.RED, method.invoke(testController, "red"));
	        } 
	        catch (Exception e) {e.printStackTrace();}	        
	    }
	}