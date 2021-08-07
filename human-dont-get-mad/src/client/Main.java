package client;

import java.util.concurrent.TimeUnit;
import game.Log;
import game.LogController;

/**
* <h1>Main</h1>
* <p>Will controll the behaving of the Windows </p>
* <b>Note:</b> 
*
* @author  Tim Menzel
* @version 1.0
* @since   2021-08-2
*/
public class Main {
	
	static boolean tryingConnect = false;
	static boolean connectionSuccessful = false;
	
	public static void main(String[] args) {
		
		LogController.setGlobalLogLvl(Log.DEBUG);
		
		boolean isConnected = false;
		
	
		GameWindow gw = new GameWindow();	
		ClientController cc = new ClientController(gw);
		Launcher lui = new Launcher(cc, gw);
		
		gw.giveController(cc);
		
		lui.setVisible(true);
	}
}

