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
* @version 1.0
* @since   2021-08-02
* @apiNote MAEDN 3.0
*/
public class Game implements GameController {
	private HashMap<PlayerColor, Player> players = new HashMap<PlayerColor, Player>();
	private HashMap<Integer, Figure> currentTurn = new HashMap<Integer, Figure>();
	private RuleSet ruleset = new RuleSet();
	private GameState state = GameState.WAITINGFORPLAYERS;
	private PlayerColor currentPlayer = null;
	private PlayerColor winner = null;

	public PlayerColor register(PlayerColor requestedColor, String name, String clientName, Float clientVersion) {
		if (players.size() < 4) {
			PlayerColor assignedColor = null;
			assignedColor = PlayerColor.getAvail(requestedColor);
			players.put(assignedColor, new Player(assignedColor, name, clientName, clientVersion, false));
			LogController.log(Log.INFO, "New Player registered: " + players.get(assignedColor));
			return assignedColor;
		}
		else { return null; }
	}

	public void remove(PlayerColor color) {
		LogController.log(Log.INFO, "Player disconnected: " + players.get(color));
		if (state == GameState.WAITINGFORPLAYERS) {
			PlayerColor.setAvail(color);
			players.remove(color, players.get(color));
		}
		else if (state == GameState.RUNNING) {
			players.get(color).setToBot("Bot", "human-dont-get-mad-bot", 01.f);
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
		if (counter >= players.size()) {
			while (players.size() < 4) {
				PlayerColor assignedColor = PlayerColor.getAvail(null);
				players.put(assignedColor, new Player(assignedColor, "Bot", "human-dont-get-mad-bot", 0.1f, true));
			}
			state = GameState.RUNNING;
			for (Integer i = 0; i < 4; i++) {
				if(players.containsKey(PlayerColor.valueOf(i))) {
					currentPlayer = PlayerColor.valueOf(i);
					break;
				}
			}
			players.get(currentPlayer).dice.setStart();
			LogController.log(Log.INFO, "Game started: " + players);
			return true;
		}
		else {
			LogController.log(Log.DEBUG, "Player ready " + isReady + ": " + players.get(color));
			return false;
		}
		
	}

	public GameState getState() {
		return state;
	}

	public PlayerColor currentPlayer() {
		return currentPlayer;
	}

	public Boolean currentPlayerIsBot() {
		if (players.get(currentPlayer).getType()) {
			return true;
		}
		else { return false; }
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
				data.put(players.get(PlayerColor.valueOf(i)).toJSON());
			}
		}
		json.put("players", data);
		return json;
	}

	public JSONObject turn(Integer selected) {
		JSONObject data = new JSONObject();
		JSONArray options = new JSONArray();
		JSONObject tempTurn = new JSONObject();

		if (selected == null) {
			currentTurn.clear();
			for (Integer i = 0; i < players.get(currentPlayer).figures.length; i++) {
				tempTurn = ruleset.dryrun(currentPlayer, players.get(currentPlayer).figures[i], players);
				if (tempTurn.has("newPosition")) {
					currentTurn.put(currentTurn.size(), players.get(currentPlayer).figures[i]);
					options.put(tempTurn);
				}
			}
			data.put("options", options);
			LogController.log(Log.DEBUG, "Turn for " + currentPlayer + ": " + data);
			return data;
		}
		else if (selected == -1 && currentTurn.size() <= 0) {
			nextPlayer();
			data.put("ok", "ok");
			return data;
		}
		else if (currentTurn.containsKey(selected) && ruleset.execute(currentPlayer, currentTurn.get(selected), players)) {
			LogController.log(Log.DEBUG, "Executed turn " + selected + " successfully: " + currentTurn.get(selected).getJSON());
			if (gameWon()) {
				LogController.log(Log.INFO, "Game won: " + players.get(winner));
			}
			else {
				nextPlayer();
			}
			data.put("ok", "ok");
			return data;
		}
		else { return data; }
	}
	
	public void botTurn(long millis) {
		turn(null);
		if (currentTurn.size() <= 0) {
			turn(-1);
		}
		else {
			turn(players.get(currentPlayer).dice.getRandomInt(0, currentTurn.size() -1));
		}
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) { LogController.log(Log.ERROR, e.toString()); }
	}
	
	/**
	 * <h1><i>gameWon</i></h1>
	 * <p>This method checks if the current player won the game and returns true if so.
	 * It also sets the game state to finished, if the game is won.</p>
	 * @return Boolean - returns true if the current player won the game; else false
	 */
	private Boolean gameWon() {
		Integer counter = 0;
		for (Integer i = 0; i < players.get(currentPlayer).figures.length; i++) {
			if (players.get(currentPlayer).figures[i].getType() == GamePosition.HOME) {
				counter++;
			}
		}
		if (counter >= players.get(currentPlayer).figures.length) {
			winner = currentPlayer;
			state = GameState.FINISHED;
			return true;
		}
		else { return false; }
	}

	/**
	 * <h1><i>nextPlayer</i></h1>
	 * <p>This method sets the next player and keeps the current one, if
	 * they got a six. It also generates the next set of dice and resets the
	 * dice of the former current player.</p>
	 */
	private void nextPlayer() {
		// six; current player again
		if (players.get(currentPlayer).dice.get() == 6) {
				players.get(currentPlayer).dice.set();
		}
		// next player
		else {
			players.get(currentPlayer).dice.reset();
			if (currentPlayer.getValue() >= players.size() -1) {
				for (Integer i = 0; i < 4; i++) {
					if(players.containsKey(PlayerColor.valueOf(i))) {
						currentPlayer = PlayerColor.valueOf(i);
						break;
					}
				}
			}
			else {
				currentPlayer = PlayerColor.valueOf(currentPlayer.getValue() +1);
			}
		}
		if (allFiguresBlocked(players.get(currentPlayer).figures)) {
			players.get(currentPlayer).dice.setStart();
		}
		else { players.get(currentPlayer).dice.set(); }
		LogController.log(Log.DEBUG, "Next: " + players.get(currentPlayer) + " with dice " + players.get(currentPlayer).dice.get());
	}

	/**
	 * <h1><i>allFiguresBlocked</i></h1>
	 * <p>This method returns true if all figures in the figure array
	 * are either in the player start or home</p>
	 * @param figures - Figure[] array of the figures
	 * @return Boolean - if all figures are in the start or not
	 */
	private Boolean allFiguresBlocked(Figure[] figures) {
		Integer counter = 0;
		for (Integer i = 0; i < figures.length; i++) {
			if (figures[i].getType() == GamePosition.START || figures[i].getType() == GamePosition.HOME) {
				counter++;
			}
		}
		if (counter >= figures.length) {
			return true;
		}
		else { return false; }
	}
}
