package client;

import java.awt.Color;

// external: https://mvnrepository.com/artifact/org.json/json/20210307
import org.json.JSONObject;

import game.Log;
import game.LogController;
import game.MsgType;

public class ClientController {
	private double supportedProtocolVersion = 3.0;
	private double supportedServerVersion = 0.1;
	private String serverName = "human-dont-get-mad";
	private double clientVersion = 0.1;
	private String clientName = "human-dont-get-mad";
	private Client player;
	
	//constructor
	public ClientController(Client player) {
		this.player = player;
	}
	
	//impl. register message
	protected void sendRegister() {
		JSONObject json = new JSONObject();
		JSONObject data = new JSONObject();
		json.put("type", MsgType.register);
		data.put("clientName", clientName);
		data.put("clientVersion", serverName);
		data.put("playerName", clientVersion);
		data.put("requestedColor", Color.red);
		json.put("data", data);
		player.out(json.toString());
		LogController.log(Log.debug, json.toString());
	}
	//decrypt client input
	protected void decipher(String input) {
		JSONObject json = new JSONObject(input);
		JSONObject data = new JSONObject(json.getJSONObject("data"));

		if (json.get("type") == MsgType.welcome) {
			if (data.getDouble("protocolVersion") == supportedProtocolVersion && data.getDouble("serverVersion") >= supportedServerVersion) {
				sendRegister();
			}
			else {}
		}
		else if (json.get("type") == MsgType.assignColor) {
			//ready
		}
		else if (json.get("type") == MsgType.update) {
			//ready
		}
		else if (json.get("type") == MsgType.turn) {
			//ready
		}
		else if (json.get("type") == MsgType.playerDisconnected) {
			//ready
		}
		else if (json.get("type") == MsgType.message) {
			//ready
		}
		else if (json.get("type") == MsgType.error) {
			//ready
		}
		else {
			//wrong data terminate connection
		}
	}

}
