package client;

// external: https://mvnrepository.com/artifact/org.json/json/20210307
import org.json.JSONObject;
import game.*;

public class Controller {
	private static double clientVersion = 0.1;
	private static String clientName = "human-dont-get-mad";
	
	//decrypt client input
	protected void deciverInput(Client player, String input) {
		JSONObject json = new JSONObject(input);
		JSONObject data = new JSONObject();

		if (json.get("type") == MsgType.welcome) {
			//register
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
