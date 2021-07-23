package server;

import org.json.JSONObject;

public class Controller {
	private static double protocolVersion = 3.0;
	private static double serverVersion = 0.1;
	private static String serverName = "human-dont-get-mad";
	
	//send welcome message
	public void sendWelcome(ClientThread player) {
		JSONObject json = new JSONObject();
		JSONObject data = new JSONObject();
		json.put("type", Type.welcome);
		data.put("protocolVersion", protocolVersion);
		data.put("serverName", serverName);
		data.put("serverVersion", serverVersion);
		json.put("data", data);
		player.out(json.toString());
	}

}
