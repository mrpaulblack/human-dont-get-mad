package client;

import java.awt.Color;

// external: https://mvnrepository.com/artifact/org.json/json/20210307
import org.json.JSONObject;

import game.MsgType;

public class ClientController {
	private String clientName = "human-dont-get-mad";
	private double clientVersion = 0.1;
	private String playerName = "Anna";
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
		data.put("clientVersion", clientVersion);
		data.put("playerName", playerName);
		data.put("requestedColor", Color.red);
		json.put("data", data);
		player.out(json.toString());
	}

	//decrypt client input
	protected void decipher(String input) {
		JSONObject json = new JSONObject(input);
		JSONObject data = new JSONObject(json.getJSONObject("data").toString());

		if (json.get("type").equals(MsgType.welcome.toString())) {
			//TODO check if protocol and servr software is supported
			sendRegister();
		}
		else if (json.get("type").equals(MsgType.assignColor.toString())) {
			//wait for ready
		}
		else if (json.get("type").equals(MsgType.update.toString())) {
			//update board
		}
		else if (json.get("type").equals(MsgType.turn.toString())) {
			//move
		}
		else if (json.get("type").equals(MsgType.playerDisconnected.toString())) {
			//update stats
		}
		else if (json.get("type").equals(MsgType.message.toString())) {
			//idk
		}
		else if (json.get("type").equals(MsgType.error.toString())) {
			//depends; maybe disconnect
		}
		else {
			//wrong data terminate connection
		}
	}

}
