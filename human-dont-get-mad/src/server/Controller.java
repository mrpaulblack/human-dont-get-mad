package server;

// external: https://mvnrepository.com/artifact/org.json/json/20210307
import org.json.JSONObject;
import game.*;

public class Controller {
	private static double protocolVersion = 3.0;
	private static double serverVersion = 0.1;
	private static String serverName = "human-dont-get-mad";
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
		LogController.log(Log.debug, json.toString());
	}
	
	//decrypt client input
	protected void deciverInput(ClientThread player, String input) {
		JSONObject json = new JSONObject(input);
		JSONObject data = new JSONObject();

		if (json.get("type") == MsgType.register) {
			//register
		}
		else if (json.get("type") == MsgType.ready) {
			//ready
		}
		else if (json.get("type") == MsgType.move) {
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
