package game;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

/**
* <h1>Game</h1>
* <p>This is the main class for the actual game logic. The class creates player objects
* for each player and is running the actual game.</p>
* <b>Note:</b> You should only instantiate this class through the GameController interface.
* The Documentation for these is in the GameController interface.
*
* @author  Paul Braeuning
* @version 1.0
* @since   2021-08-02
* @apiNote MAEDN 3.0
*/
public class Game implements GameController {
	private HashMap<PlayerColor, Player> players = new HashMap<PlayerColor, Player>();
	private GameState state = GameState.WAITINGFORPLAYERS;
	private PlayerColor currentPlayer = null;
	private PlayerColor winner = null;

	public PlayerColor register(PlayerColor requestedColor, String name, String clientName, Float clientVersion) {
		if (players.size() < 4) {
			PlayerColor assignedColor = null;
			assignedColor = PlayerColor.getAvail(requestedColor);
			players.put(assignedColor, new Player(assignedColor, name, clientName, clientVersion, false));
			LogController.log(Log.INFO, "New Player registered: " + players.get(assignedColor).toJSON(false).toString());
			return assignedColor;
		}
		else { return null; }
	}
	
	public void remove(PlayerColor color) {
		LogController.log(Log.INFO, "Player disconnected: " + players.get(color).toJSON(false));
		if (state == GameState.WAITINGFORPLAYERS) {
			PlayerColor.setAvail(color);
			players.remove(color, players.get(color));
		}
		else if (state == GameState.RUNNING) {
			PlayerColor.setAvail(color);
			//TODO replace player in running game with BOT
		}
	}

	public Boolean ready(PlayerColor color, Boolean isReady) {
		Integer counter = 0;
		players.get(color).setReady(isReady);
		for (Map.Entry<PlayerColor, Player> player : players.entrySet()) {
			if (player.getValue().getReady()) {
				counter++;
			}
		}
		// TODO dirty fix so game starts only with 4 players since BOTS are not implemented yet; (counter >= players.size())
		if (counter >= 4) {
			if (players.size() <= 4) {
				//TODO fill the rest with BOTS
			}
			state = GameState.RUNNING;
			currentPlayer = PlayerColor.RED;
			players.get(currentPlayer).dice.setStartDice();
			LogController.log(Log.INFO, "Game started: " + players);
			return true;
		}
		else {
			LogController.log(Log.DEBUG, "Player ready " + isReady + ": " + players.get(color).toJSON(false));
			return false;
		}
		
	}

	public GameState getState() {
		return state;
	}

	public PlayerColor currentPlayer() {
		return currentPlayer;
	}

	public JSONObject toJSON() {
		JSONObject json = new JSONObject();
		JSONArray data = new JSONArray();
		json.put("state", state.toString());
		if (currentPlayer == null) {
			json.put("currentPlayer", "null");
		}
		else { json.put("currentPlayer", currentPlayer.toString()); }
		if (winner == null) {
			json.put("winner", "null");
		}
		else { json.put("winner", winner.toString()); }
		for (Integer i = 0; i < 4; i++) {
			if (players.get(PlayerColor.valueOf(i)) != null) {
				data.put(players.get(PlayerColor.valueOf(i)).toJSON(true));
			}
		}
		json.put("players", data);
		return json;
	}
	
	//returns true if executed move or when called with -1 returns turn options as array
	public JSONObject turn(Integer selected) {
		RuleSet ruleset = new RuleSet();
		JSONObject json = new JSONObject();
		JSONArray data = new JSONArray();
		JSONObject tempTurn = new JSONObject();
		if (selected <= -1) {
			for (Integer i = 0; i < players.get(currentPlayer).figures.length; i++) {
				tempTurn = ruleset.dryRun(currentPlayer, players.get(currentPlayer).figures[i], players);
				if (tempTurn != null) {
					data.put(tempTurn);
				}
			}
			json.put("options", data);
			return json;
		}
		else {
			// execute turn
			// if turn unsuccessful return error
			json.put("successful", true);
			return json;
		}
	}
}
