package game;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

//since August 2nd 2021
public class Game implements GameController{
	private HashMap<PlayerColor, Player> players = new HashMap<PlayerColor, Player>();
	private GameState state = GameState.WAITINGFORPLAYER;
	private PlayerColor currentPlayer = null;
	private PlayerColor winner = null;
	
	//get the current game state
	public GameState getGameState() {
		return state;
	}
	
	//add a new player to the game and also the socket
	public PlayerColor register(PlayerColor requestedColor, String name, String clientName, Float clientVersion) {
		if (players.size() < 4) {
			PlayerColor assignedColor = null;
			assignedColor = PlayerColor.getAvail(requestedColor);
			players.put(assignedColor, new Player(assignedColor, name, clientName, clientVersion, false));
			LogController.log(Log.INFO, "New Player registered: " + players.get(assignedColor).toJSON().toString());
			return assignedColor;
		}
		else { return null; }
	}
	
	//set player to ready
	public Boolean ready(PlayerColor color) {
		//TODO if game starts with <4 player; fill the rest with bots
		Integer counter = 0;
		players.get(color).setReady();
		for (Map.Entry<PlayerColor, Player> player : players.entrySet()) {
			if (player.getValue().getReady()) {
				counter++;
			}
		}
		if (counter >= players.size()) {
			state = GameState.RUNNING;
			currentPlayer = PlayerColor.RED;
			//TODO gen dice for first (red player)
			LogController.log(Log.INFO, "Game started: " + players);
			return true;
		}
		else {
			LogController.log(Log.DEBUG, "Player ready: " + players.get(color).getColor().toString().toLowerCase());
			return false;
		}
		
	}
	
	//get JSON of current game for all player
	public JSONObject toJSON() {
		JSONObject json = new JSONObject();
		JSONArray data = new JSONArray();
		json.put("state", state.toString().toLowerCase());
		if (currentPlayer == null) {
			json.put("currentPlayer", "null");
		}
		else { json.put("currentPlayer", currentPlayer.toString().toLowerCase()); }
		if (winner == null) {
			json.put("winner", "null");
		}
		else { json.put("winner", winner.toString().toLowerCase()); }
		for (Map.Entry<PlayerColor, Player> player : players.entrySet()) {
			data.put(player.getValue().toJSON());
		}
		json.put("players", data);
		return json;
	}

}
