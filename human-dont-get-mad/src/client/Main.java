package client;

import game.Log;
import game.LogController;

/**
* <h1>Main</h1>
* <p>Will controll the behaving of the Windows </p>
* <b>Note:</b> 
*
* @version 1.0
* @since   2021-08-2
*/
public class Main {
	
	static boolean tryingConnect = false;
	static boolean connectionSuccessful = false;
	 
	public static void main(String[] args) {
		
		LogController.setGlobalLogLvl(Log.INFO);
		
		Messager m = new Messager();
		GameWindow gw = new GameWindow();	
		ClientController cc = new ClientController(gw, m);
		Launcher lui = new Launcher(cc, gw);
		
		m.messager();
		
		gw.giveController(cc, m);
		
		lui.setVisible(true);
	}
}

