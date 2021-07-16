package client;

public class Main {
	
	public String ip = null;		// Transmit the Server Address
	String port = null;			// Transmit the Port
	String uname = null;		// Transmit the Username
	int favColor = -1;			// Transmit the Preferred Color (if not chosen, set to -1 -> random Coloe)

	public static void main(String[] args) {
		
		Launcher lui = new Launcher();
		lui.LauncherUI();
		 
	}

}
