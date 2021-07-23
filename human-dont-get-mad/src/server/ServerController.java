package server;

// external: https://mvnrepository.com/artifact/org.json/json/20210307
import org.json.JSONObject;

import game.Log;
import game.LogController;
import game.MsgType;

public class ServerController {
	private double protocolVersion = 3;
	private double serverVersion = 0.1;
	private String serverName = "human-dont-get-mad";
	
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
	
	//decipher client input
	protected void decoder(ClientThread player, String input) {
		JSONObject json = new JSONObject(input);
		JSONObject data = new JSONObject(json.get("data").toString());
		LogController.log(Log.debug, json.get("type") + ": " + data);

		if (json.get("type").equals(MsgType.register.toString())) {
			sendassignColor(player);
		}
		else if (json.get("type").equals(MsgType.ready.toString())) {
			//update and add client to client array
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
