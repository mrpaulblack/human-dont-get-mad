package client;

import java.util.concurrent.TimeUnit;


/* IMPORTANT NOTES
 * In this version the launcher doesn't connect to anything it just do a "simulation"
 * -> it will wait for a second to emulate a loading time 
 */
public class Main {
	
	public String ip = null;		// Transmit the Server Address
	String port = null;			// Transmit the Port
	String uname = null;		// Transmit the Username 
	Integer favColor = -1;			// Transmit the Preferred Color (if not chosen, set to -1 -> random Coloe)
	static boolean tryingConnect = false;
	
	public static void main(String[] args) {
		
		boolean isConnected = false;
		boolean connectionSuccessful = false;
		
		Launcher lui = new Launcher();
		lui.setVisible(false);
		GameWindow gw = new GameWindow();	
		
		//DEBUG FOR FASTER WINDOW ENTERING
		//gw.setVisible(true);
	
		//Runns as long as no connection get established
		while (isConnected == false) {
			
			lui.setVisible(true);
			
			//get triggered by the connect Button -> sets the window to false
			while (tryingConnect) {
				
				lui.setVisible(false);
				
				//Connection Simulation
				try {
					TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				//if a Connection should been successful -> set true
				//if a Connection should been failed -> set false
				//JUST A CONNECTION DUMMY
				connectionSuccessful = true;//PLACEHOLDER check if connection get established
				
				//if it is successful it will let the launcher closed and open the game window
				if (connectionSuccessful) {
					
					isConnected = true;
					tryingConnect = false; //is connect so it can be set false
					gw.setVisible(true);
					
				}
				//if failed it will be reopend
				else {
					lui.setVisible(true);
				}
			}
		}
	}
}
