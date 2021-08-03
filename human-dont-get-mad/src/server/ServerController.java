package server;

// external: https://mvnrepository.com/artifact/org.json/json/20210307
import org.json.JSONObject;

import game.Log;
import game.LogController;
import game.MsgError;
import game.MsgType;
import game.PlayerColor;
import game.Game;
import game.GameState;

/**
* <h1>ServerController</h1>
* <p>The ServerController class is a abstraction that can send JSON messages
* that implement the maedn protocol spec and decipher received JSON data
* it is the layer between every ClientThread and the game logic.</p>
* <b>Note:</b> The ServerController should only be instantiated once every server
* and NOT for each ClientThread.
*
* @author  Paul Braeuning
* @version 1.0
* @since   2021-07-23
*/
public class ServerController {
	private double protocolVersion = 3.0;
	private double serverVersion = 0.1;
	private String serverName = "human-dont-get-mad";
	private Game game = new Game();
	
	/**
	 *	<h1><i>sendWelcome</i></h1>
	 * <p>This method is sending a welcome message to the selected player (ClientThread).</p>
	 * @param player - ClientThread that receives JSON
	 */
	protected void sendWelcome(ClientThread player) {
		JSONObject json = new JSONObject();
		JSONObject data = new JSONObject();
		json.put("type", MsgType.WELCOME.toString().toLowerCase());
		data.put("protocolVersion", protocolVersion);
		data.put("serverName", serverName);
		data.put("serverVersion", serverVersion);
		json.put("data", data);
		player.out(json.toString());
	}
	
	/**
	 *	<h1><i>sendassignColor</i></h1>
	 * <p>This method is sending a assingColor message to the selected player (ClientThread).</p>
	 * @param player - ClientThread that receives JSON
	 */
	private void sendassignColor(ClientThread player, PlayerColor color) {
		JSONObject json = new JSONObject();
		JSONObject data = new JSONObject();
		json.put("type", MsgType.ASSIGNCOLOR.toString().toLowerCase());
		data.put("color", color.toString().toLowerCase());
		json.put("data", data);
		player.out(json.toString());
	}
	
	// TODO needs doc
	// Broadcast update to all player
	private void sendUpdate(ClientThread[] broadcast) {
		JSONObject json = new JSONObject();
		JSONObject data = new JSONObject();
		json.put("type", MsgType.UPDATE.toString().toLowerCase());
		//TODO add missing data
		json.put("data", data);
		for (ClientThread player : broadcast) {
			player.out(json.toString());
		}
	}
	
	//send error to client without message; overload method
	private void sendError(ClientThread player, MsgError error) {
		JSONObject json = new JSONObject();
		JSONObject data = new JSONObject();
		json.put("type", MsgType.ERROR.toString().toLowerCase());
		data.put("error", error.toString().toLowerCase());
		json.put("data", data);
		player.out(json.toString());
	}
	
	//send error to client with message
	private void sendError(ClientThread player, MsgError error, String message) {
		JSONObject json = new JSONObject();
		JSONObject data = new JSONObject();
		json.put("type", MsgType.ERROR.toString().toLowerCase());
		json.put("message", message);
		data.put("error", error.toString().toLowerCase());
		json.put("data", data);
		player.out(json.toString());
	}
	
	/**
	 *	<h1><i>decoder</i></h1>
	 * <p>This method decodes the received data by a client and calls based on the 
	 * parsed type different methods with the data payload as the parameters.</p>
	 * @param player - ClientThread that receives JSON
	 * @param imput - String with the received data by ClientThread
	 */
	protected void decoder(ClientThread player, String input) throws Exception {
		JSONObject json = new JSONObject(input);
		JSONObject data = new JSONObject(json.get("data").toString());
		LogController.log(Log.DEBUG, json.get("type") + ": " + data);

		//register
		if (json.getString("type").equals(MsgType.REGISTER.toString().toLowerCase()) && player.getState() == MsgType.WELCOME && game.getGameState() == GameState.WAITINGFORPLAYER) {
			PlayerColor tempColor;
			if (data.has("requestedColor")) {
				tempColor = game.register(decodeColor(data.getString("requestedColor")), data.getString("playerName"), data.getString("clientName"), data.getFloat("clientVersion"));
			}
			else {
				tempColor = game.register(null, data.getString("playerName"), data.getString("clientName"), data.getFloat("clientVersion"));
			}
			if (tempColor != null) {
				player.setState(MsgType.REGISTER);
				sendassignColor(player, tempColor);
			}
			else {
				sendError(player, MsgError.SERVERFULL);
				throw new IllegalArgumentException();
			}
		}
		
		//ready
		else if (json.getString("type").equals(MsgType.READY.toString().toLowerCase()) && player.getState() == MsgType.REGISTER && game.getGameState() == GameState.WAITINGFORPLAYER) {
			//TODO update and add client to client array
			player.setState(MsgType.READY);
		}
		
		//move
		else if (json.getString("type").equals(MsgType.MOVE.toString().toLowerCase())) {
			//TODO update or error
		}
		
		//message (optional)
		else if (json.getString("type").equals(MsgType.MESSAGE.toString())) {
			//TODO idk
		}
		
		//error
		else if (json.get("type").equals(MsgType.ERROR.toString())) {
			//TODO depends; maybe client disconnect
		}
		else { throw new IllegalArgumentException(); }
	}
	
	//decode a String color
	private PlayerColor decodeColor(String color) {
		switch(color) {
		case "red": return PlayerColor.RED;
		case "blue": return PlayerColor.BLUE;
		case "green": return PlayerColor.GREEN;
		case "yellow": return PlayerColor.YELLOW;
		default: return null;
		}
	}

}
