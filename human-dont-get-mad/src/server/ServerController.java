package server;

// external: https://mvnrepository.com/artifact/org.json/json/20210307
import org.json.JSONObject;

import game.MsgType;

public class ServerController {
	private double protocolVersion = 3;
	private double serverVersion = 0.1;
	private String serverName = "human-dont-get-mad";
	private ClientThread[] client = new ClientThread[4];
	
	//send welcome message
	protected void sendWelcome(ClientThread player) {
		JSONObject json = new JSONObject();
		JSONObject data = new JSONObject();
		json.put("type", MsgType.welcome);
		data.put("protocolVersion", protocolVersion);
		data.put("serverName", serverName);
		data.put("serverVersion", serverVersion);
		json.put("data", data);
		player.out(json.toString());
	}
	
	//send assignColor
	private void sendassignColor(ClientThread player) {
		JSONObject json = new JSONObject();
		JSONObject data = new JSONObject();
		json.put("type", MsgType.assignColor);
		//TODO assign avail color and honor client request
		data.put("color", "red");
		json.put("data", data);
		player.out(json.toString());
	}
	
	//decrypt client input
	protected void decipher(ClientThread player, String input) {
		JSONObject json = new JSONObject(input);
		JSONObject data = new JSONObject(json.get("data".toString()));

		if (json.get("type").equals(MsgType.register.toString())) {
			sendassignColor(player);
		}
		else if (json.get("type").equals(MsgType.ready.toString())) {
			//update
		}
		else if (json.get("type").equals(MsgType.move.toString())) {
			//update or error
		}
		else if (json.get("type").equals(MsgType.message.toString())) {
			//idk
		}
		else if (json.get("type").equals(MsgType.error.toString())) {
			//depends; maybe client disconnect
		}
		else {
			//wrong data terminate connection
		}
	}

}
