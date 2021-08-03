package client;

// external: https://mvnrepository.com/artifact/org.json/json/20210307
import org.json.JSONObject;

import game.Log;
import game.LogController;
import game.MsgType;

/**
* <h1>ClientController</h1>
* <p>The ClientController class is a abstraction that can send json messages
* that implement the maedn protocol spec and decipher recieved json data
* it is the layer between the client socket and the GUI.</p>
* <b>Note:</b> The ClientController should only be instaciated with every client socket.
*
* @author  Paul Braeuning
* @author Tim Menzel
* @version 1.0
* @since   2021-07-23
*/
public class ClientController {
	
	Launcher l = new Launcher();
	
	private String clientName = "human-dont-get-mad";
	private double clientVersion = 0.1;
	private Client player;
	
	/**
	 *	<h1><i>ClientController</i></h1>
	 * <p>This Constructor sets the Client as as the player attribute.
	 * Each Method is going to ac on this client.</p>
	 * @param player - Client this controller is assigned to
	 */
	public ClientController(Client player) {
		this.player = player;
	}
	
	
	/**
	 *	<h1><i>sendRegister</i></h1>
	 * <p>This method is sending a register message to the server.</p>
	 */
	protected void sendRegister() {
		
		JSONObject json = new JSONObject();
		JSONObject data = new JSONObject();
		json.put("type", MsgType.REGISTER.toString());
		data.put("clientName", clientName);
		data.put("clientVersion", clientVersion);
		data.put("playerName", l.lUserName);
		data.put("requestedColor", l.lFavColor);
		json.put("data", data);
		player.out(json.toString());
	}

	
	/**
	 *	<h1><i>decoder</i></h1>
	 * <p>This method decodes the recieved data by a server and calls based on the 
	 * parsed type different methods with the data payload as the parameters.</p>
	 * @param input - String of the recieved data
	 */
	protected void decoder(String input) {
		JSONObject json = new JSONObject(input);
		JSONObject data = new JSONObject(json.getJSONObject("data").toString());
		LogController.log(Log.DEBUG, json.get("type") + ": " + data);

		if (json.get("type").equals(MsgType.WELCOME.toString().toLowerCase())) {
			//TODO check if protocol and servr software is supported
			sendRegister();
		}
		else if (json.get("type").equals(MsgType.ASSIGNCOLOR.toString())) {
			//TODO wait for GUI and send ready
		}
		else if (json.get("type").equals(MsgType.UPDATE.toString())) {
			//TODO update board; asynchron
		}
		else if (json.get("type").equals(MsgType.TURN.toString())) {
			//TODO send move
		}
		else if (json.get("type").equals(MsgType.PLAYERDISCONNECTED.toString())) {
			//TODO update stats; asynchron
		}
		else if (json.get("type").equals(MsgType.MESSAGE.toString())) {
			//TODO idk
		}
		else if (json.get("type").equals(MsgType.ERROR.toString())) {
			//TODO depends; maybe disconnect
		}
		else {
			//TODO wrong data terminate connection
		}
	}
}
