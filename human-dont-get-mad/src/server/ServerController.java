package server;

import java.util.ArrayList;

// external: https://mvnrepository.com/artifact/org.json/json/20210307
import org.json.JSONObject;

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
	private ArrayList<ClientThread> clients = new ArrayList<ClientThread>();
	private Game game = new Game();
	
	/**
	 *	<h1><i>sendWelcome</i></h1>
	 * <p>This method is sending a welcome message to the selected player (ClientThread).</p>
	 * @param player - ClientThread that receives JSON
	 */
	protected void sendWelcome(ClientThread client) {
		JSONObject json = new JSONObject();
		JSONObject data = new JSONObject();
		json.put("type", MsgType.WELCOME.toString().toLowerCase());
		data.put("protocolVersion", protocolVersion);
		data.put("serverName", serverName);
		data.put("serverVersion", serverVersion);
		json.put("data", data);
		client.out(json.toString());
	}
	
	/**
	 *	<h1><i>sendassignColor</i></h1>
	 * <p>This method is sending a assingColor message to the selected player (ClientThread).</p>
	 * @param player - ClientThread that receives JSON
	 */
	private void sendassignColor(ClientThread client, PlayerColor color) {
		JSONObject json = new JSONObject();
		JSONObject data = new JSONObject();
		json.put("type", MsgType.ASSIGNCOLOR.toString().toLowerCase());
		data.put("color", color.toString().toLowerCase());
		json.put("data", data);
		client.out(json.toString());
	}
	
	// TODO needs doc
	// Broadcast update to all player
	private void broadcastUpdate() {
		JSONObject json = new JSONObject();
		JSONObject data = new JSONObject();
		json.put("type", MsgType.UPDATE.toString().toLowerCase());
		data.put("state", game.getGameState().toString().toLowerCase());
		//TODO add current player (null if game is not running)
		data.put("currentPlayer", "");
		//TODO add winner (null if game state is NOT finished)
		data.put("winner", "");
		data.put("players", game.toJSON());
		json.put("data", data);
		for (ClientThread client : clients) {
			client.out(json.toString());
		}
	}
	
	//send error to client without message; overload method
	private void sendError(ClientThread client, MsgError error) {
		JSONObject json = new JSONObject();
		JSONObject data = new JSONObject();
		json.put("type", MsgType.ERROR.toString().toLowerCase());
		data.put("error", error.toString().toLowerCase());
		json.put("data", data);
		client.out(json.toString());
	}
	
	//send error to client with message
	//TODO use for something?
	@SuppressWarnings("unused")
	private void sendError(ClientThread client, MsgError error, String message) {
		JSONObject json = new JSONObject();
		JSONObject data = new JSONObject();
		json.put("type", MsgType.ERROR.toString().toLowerCase());
		json.put("message", message);
		data.put("error", error.toString().toLowerCase());
		json.put("data", data);
		client.out(json.toString());
	}
	
	/**
	 *	<h1><i>decoder</i></h1>
	 * <p>This method decodes the received data by a client and calls based on the 
	 * parsed type different methods with the data payload as the parameters.</p>
	 * @param player - ClientThread that receives JSON
	 * @param imput - String with the received data by ClientThread
	 */
	protected void decoder(ClientThread client, String input) throws Exception {
		JSONObject json = new JSONObject(input);
		JSONObject data = new JSONObject(json.get("data").toString());

		//register
		if (json.getString("type").equals(MsgType.REGISTER.toString().toLowerCase()) && client.getState() == MsgType.WELCOME && game.getGameState() == GameState.WAITINGFORPLAYER) {
			PlayerColor tempColor;
			if (data.has("requestedColor")) {
				tempColor = game.register(decodeColor(data.getString("requestedColor")), data.getString("playerName"), data.getString("clientName"), data.getFloat("clientVersion"));
			}
			else {
				tempColor = game.register(null, data.getString("playerName"), data.getString("clientName"), data.getFloat("clientVersion"));
			}
			if (tempColor != null) {
				clients.add(client);
				client.setState(MsgType.REGISTER);
				sendassignColor(client, tempColor);
				broadcastUpdate();
			}
			else {
				sendError(client, MsgError.SERVERFULL);
				throw new IllegalArgumentException();
			}
		}
		
		//ready
		else if (json.getString("type").equals(MsgType.READY.toString().toLowerCase()) && client.getState() == MsgType.REGISTER && game.getGameState() == GameState.WAITINGFORPLAYER) {
			//TODO update and add client to client array
			client.setState(MsgType.READY);
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
