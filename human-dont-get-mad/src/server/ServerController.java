package server;

import java.util.HashMap;
import java.util.Map.Entry;

// external: https://mvnrepository.com/artifact/org.json/json/20210307
import org.json.JSONObject;

import game.MsgError;
import game.MsgType;
import game.PlayerColor;
import game.Game;
import game.GameController;
import game.GameState;

/**
* <h1>ServerController</h1>
* <p>The ServerController class is a abstraction that can send JSON messages
* that implements the MAEDN protocol specification and decipher received JSON data;
* It is the layer between every ClientThread and the game logic.</p>
* <b>Note:</b> The ServerController should only be instantiated once every server
* and NOT for each ClientThread.
*
* @author  Paul Braeuning
* @version 1.0
* @since   2021-07-23
* @apiNote MAEDN 3.0
*/
public class ServerController {
	private double protocolVersion = 3.0;
	private double serverVersion = 0.1;
	private String serverName = "human-dont-get-mad";
	private HashMap<ClientThread, PlayerColor> clients = new HashMap<ClientThread, PlayerColor>();
	private GameController game = new Game();
	
	/**
	 * <h1><i>sendWelcome</i></h1>
	 * <p>This method is sending a welcome message to the selected player (ClientThread).</p>
	 * @param client - ClientThread that receives JSON
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
	 * <h1><i>sendassignColor</i></h1>
	 * <p>This method is sending a assingColor message to the selected player (ClientThread).</p>
	 * @param client - ClientThread that receives JSON
	 * @param color - PlayerColor that the client gets assigned to
	 */
	private void sendassignColor(ClientThread client, PlayerColor color) {
		JSONObject json = new JSONObject();
		JSONObject data = new JSONObject();
		json.put("type", MsgType.ASSIGNCOLOR.toString().toLowerCase());
		data.put("color", color.toString().toLowerCase());
		json.put("data", data);
		client.out(json.toString());
	}
	
	/**
	 * <h1><i>broadcastUpdate</i></h1>
	 * <p>This method is broadcasting the current game state,
	 * with all player info and all figure positions to all clients that are
	 * registered.</p>
	 */
	private void broadcastUpdate() {
		JSONObject json = new JSONObject();
		json.put("type", MsgType.UPDATE.toString().toLowerCase());
		json.put("data", game.toJSON());
		for (Entry<ClientThread, PlayerColor> client : clients.entrySet()) {
			client.getKey().out(json.toString());
		}
	}
	
	/**
	 * <h1><i>sendError</i></h1>
	 * <p>This method returns a selected error without a message to the
	 * defined client.</p>
	 * @param client - ClientThread with the socket
	 * @param error - MsgError is the error type that is send to the client
	 */
	private void sendError(ClientThread client, MsgError error) {
		JSONObject json = new JSONObject();
		JSONObject data = new JSONObject();
		json.put("type", MsgType.ERROR.toString().toLowerCase());
		data.put("error", error.toString().toLowerCase());
		json.put("data", data);
		client.out(json.toString());
	}
	
	/**
	 * <h1><i>decoder</i></h1>
	 * <p>This method decodes the received data by a client and calls based on the 
	 * parsed type different methods with the data payload as the parameters.</p>
	 * @param client - ClientThread that receives JSON
	 * @param imput - String with the received data by ClientThread
	 * @throws Exception if message cannot be decoded
	 */
	protected void decoder(ClientThread client, String input) throws Exception {
		JSONObject json = new JSONObject(input);
		JSONObject data = new JSONObject(json.get("data").toString());

		// register
		if (json.getString("type").equals(MsgType.REGISTER.toString().toLowerCase()) && client.getState() == MsgType.WELCOME && game.getGameState() == GameState.WAITINGFORPLAYER) {
			PlayerColor tempColor;
			if (data.has("requestedColor")) {
				tempColor = game.register(decodeColor(data.getString("requestedColor")), data.getString("playerName"), data.getString("clientName"), data.getFloat("clientVersion"));
			}
			else {
				tempColor = game.register(null, data.getString("playerName"), data.getString("clientName"), data.getFloat("clientVersion"));
			}
			if (tempColor != null) {
				clients.put(client, tempColor);
				client.setState(MsgType.REGISTER);
				sendassignColor(client, tempColor);
				broadcastUpdate();
			}
			else {
				sendError(client, MsgError.SERVERFULL);
				throw new IllegalArgumentException();
			}
		}
		
		// ready
		else if (json.getString("type").equals(MsgType.READY.toString().toLowerCase()) && client.getState() == MsgType.REGISTER && game.getGameState() == GameState.WAITINGFORPLAYER) {
			client.setState(MsgType.READY);
			if (game.ready(clients.get(client))) {
				//TODO handshake finished; game started and sendTurn() to first player
			}
			broadcastUpdate();
		}
		
		// move
		else if (json.getString("type").equals(MsgType.MOVE.toString().toLowerCase()) && client.getState() == MsgType.READY && game.getGameState() == GameState.RUNNING) {
			//TODO execute move or error when illegal argument
		}
		
		// message (optional)
		else if (json.getString("type").equals(MsgType.MESSAGE.toString())) {
			//TODO (optional) chat message support
		}
		
		// error
		else if (json.get("type").equals(MsgType.ERROR.toString())) {
			//TODO depends; maybe client disconnect
		}
		
		// unsupported
		else {
			sendError(client, MsgError.UNSUPPORTEDMESSAGETYPE);
			throw new IllegalArgumentException();
		}
	}

	/**
	 * <h1><i>decodeColor</i></h1>
	 * <p>This method takes the string name of a color and returns
	 * the corresponding PlayerColor type.</p>
	 * @param color - String of the color
	 * @return PlayerColor - returns the decoded PlayerColor
	 */
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
