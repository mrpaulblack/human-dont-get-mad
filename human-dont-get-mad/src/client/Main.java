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
		
		Launcher lui = new Launcher();
		GameWindow gw = new GameWindow();	
		
		//Runns as long as no connection get established
		while (isConnected == false) {
			
			lui.setVisible(true);
			
			//get triggered by the connect Button -> sets the window to false
			while (tryingConnect) {
				
				lui.setVisible(false);
				
				//Waits for connection
				try {
					TimeUnit.SECONDS.sleep(2);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				if (connectionSuccessful) {
					
					isConnected = true;
					tryingConnect = false; //is connect so it can be set false
					gw.setVisible(true);
				}
				//if failed it will be reopend
				else {
					tryingConnect = false;
					lui.setVisible(true);
				}
			}
		}
	}
}
