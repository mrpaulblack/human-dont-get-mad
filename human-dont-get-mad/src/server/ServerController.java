package server;

// external: https://mvnrepository.com/artifact/org.json/json/20210307
import org.json.JSONObject;

import game.Log;
import game.LogController;
import game.MsgType;

/**
* <h1>ServerController</h1>
* <p>The ServerController class is a abstraction that can send json messages
* that implement the maedn protocol spec and decipher recieved json data
* it is the layer between every ClientThread and the game logic.</p>
* <b>Note:</b> The ServerController should only be instaciated once every server
* and NOT for each ClientThread.
*
* @author  Paul Braeuning
* @version 1.0
* @since   2021-07-23
*/
public class ServerController {
	private double protocolVersion = 3;
	private double serverVersion = 0.1;
	private String serverName = "human-dont-get-mad";
	
	/**
	 *	<h1><i>sendWelcome</i></h1>
	 * <p>This method is sending a welcome message to the selected player (ClientThread).</p>
	 * @param player - ClientThread that recieves json
	 */
	protected void sendWelcome(ClientThread player) {
		JSONObject json = new JSONObject();
		JSONObject data = new JSONObject();
		json.put("type", MsgType.WELCOME);
		data.put("protocolVersion", protocolVersion);
		data.put("serverName", serverName);
		data.put("serverVersion", serverVersion);
		json.put("data", data);
		player.out(json.toString());
	}
	
	/**
	 *	<h1><i>sendassignColor</i></h1>
	 * <p>This method is sending a assingColor message to the selected player (ClientThread).</p>
	 * @param player - ClientThread that recieves json
	 */
	private void sendassignColor(ClientThread player) {
		JSONObject json = new JSONObject();
		JSONObject data = new JSONObject();
		json.put("type", MsgType.ASSIGNCOLOR);
		//TODO assign avail color and honor client request
		data.put("color", "red");
		json.put("data", data);
		player.out(json.toString());
	}
	
	/**
	 *	<h1><i>decoder</i></h1>
	 * <p>This method decodes the recieved data by a client and calls based on the 
	 * parsed type different methods with the data payload as the parameters.</p>
	 * @param player - ClientThread that recieves json
	 * @param imput - String with the recieved data by ClientThread
	 */
	protected void decoder(ClientThread player, String input) {
		JSONObject json = new JSONObject(input);
		JSONObject data = new JSONObject(json.get("data").toString());
		LogController.log(Log.DEBUG, json.get("type") + ": " + data);

		if (json.get("type").equals(MsgType.REGISTER.toString())) {
			sendassignColor(player);
		}
		else if (json.get("type").equals(MsgType.READY.toString())) {
			//TODO update and add client to client array
		}
		else if (json.get("type").equals(MsgType.MOVE.toString())) {
			//TODO update or error
		}
		else if (json.get("type").equals(MsgType.MESSAGE.toString())) {
			//TODO idk
		}
		else if (json.get("type").equals(MsgType.ERROR.toString())) {
			//TODO depends; maybe client disconnect
		}
		else {
			//TODO wrong data terminate connection
		}
	}

}
