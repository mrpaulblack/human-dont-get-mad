package game;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

/**
* <h1>RuleSet</h1>
* <p>This class is the actual decision maker which generates and executes turns
* for a given figure.</p>
*
* @author  Paul Braeuning
* @version 1.0
* @since   2021-08-05
* @apiNote MAEDN 3.0
*/
public class RuleSet {

	/**
	 * <h1><i>dryrun</i></h1>
	 * <p>This method either generates the possible move for a figure and
	 * returns it in MAEDN specifications or returns an empty object,
	 * if there is no turn possible for that figure.</p>
	 * @param currentPlayer - PlayerColor of the current player
	 * @param currentFigure - Figure that the turn is generated for
	 * @param players - HashMap<PlayerColor, Player> of all players
	 * @return JsonObject - of the possible move or else an empty object
	 */
	protected JSONObject dryrun(PlayerColor currentPlayer, Figure currentFigure, HashMap<PlayerColor, Player> players) {
		JSONObject json = new JSONObject();
		JSONObject tempPosition = rules(currentPlayer, currentFigure, players, false);

		if (tempPosition.has("index")) {
			json.put("oldPosition", currentFigure.getJSON());
			json.put("newPosition", tempPosition);
			return json;
		}
		else { return json; }
	}

	/**
	 * <h1><i>execute</i></h1>
	 * <p>This method executes the possible turn for a given figure
	 * of a given player and either returns true when successful or false
	 * if not.</p>
	 * @param currentPlayer - PlayerColor of the current player
	 * @param currentFigure - Figure that the turn is generated for
	 * @param players - HashMap<PlayerColor, Player> of all players
	 * @return Boolean - true if successful, else false
	 */
	protected Boolean execute(PlayerColor currentPlayer, Figure currentFigure, HashMap<PlayerColor, Player> players) {
		if (rules(currentPlayer, currentFigure, players, true).has("ok")) {
			return true;
		}
		else { return false; }
	}

	/**
	 * <h1><i>rules</i></h1>
	 * <p>This is the main rule set. It either generates a turn (or returns an empty object
	 * if not possible) or it executes that turn and returns an OK JSON object if
	 * successful.</p>
	 * @param currentPlayer - PlayerColor of the current player
	 * @param currentFigure - Figure that the turn is generated for
	 * @param players - HashMap<PlayerColor, Player> of all players
	 * @param execute - Boolean if the turn should be executed or just tested
	 * @return Boolean - true if successful, else false
	 */
	private JSONObject rules(PlayerColor currentPlayer, Figure currentFigure, HashMap<PlayerColor, Player> players, Boolean execute) {
		JSONObject newPosition = new JSONObject();
		Integer tempIndex;

		// you can move figure out of player start (when figure in start, dice = 6 and starting field is not currentPlayer)
		if (currentFigure.getType() == GamePosition.START && players.get(currentPlayer).dice.get() == 6 && getBoardPosition(currentPlayer.getOffset(), players) != currentPlayer) {
			if (!execute) {
				newPosition.put("type", GamePosition.FIELD.toString());
				newPosition.put("index", currentPlayer.getOffset());
				return newPosition;
			}
			else {
				Player tempPlayer = players.get(getBoardPosition(currentPlayer.getOffset(), players));
				if (tempPlayer != null) {
					Integer tempFigureIndex = getBoardFigureIndex(currentPlayer.getOffset(), players);
					tempPlayer.figures[tempFigureIndex].setType(GamePosition.START);
					tempPlayer.figures[tempFigureIndex].setIndex(tempPlayer.figures[tempFigureIndex].getStartIndex());
				}
				currentFigure.setType(GamePosition.FIELD);
				currentFigure.setIndex(currentPlayer.getOffset());
				return newPosition.put("ok", "ok");
			}
		}

		// returns new position on the field if possible
		else if (currentFigure.getType() == GamePosition.FIELD) {
			tempIndex = getNewBoardIndex(currentFigure.getIndex(), currentPlayer, players.get(currentPlayer));
			// move figure on field
			if (tempIndex != null && tempIndex >= 0 && getBoardPosition(tempIndex, players) != currentPlayer) {
				if (!execute) {
					newPosition.put("type", GamePosition.FIELD.toString());
					newPosition.put("index", tempIndex);
					return newPosition;
				}
				else {
					Player tempPlayer = players.get(getBoardPosition(tempIndex, players));
					if (tempPlayer != null) {
						Integer tempFigureIndex = getBoardFigureIndex(tempIndex, players);
						tempPlayer.figures[tempFigureIndex].setType(GamePosition.START);
						tempPlayer.figures[tempFigureIndex].setIndex(tempPlayer.figures[tempFigureIndex].getStartIndex());
					}
					currentFigure.setType(GamePosition.FIELD);
					currentFigure.setIndex(tempIndex);
					return newPosition.put("ok", "ok");
				}
			}
			// move figure from field into player home
			else if (tempIndex != null && tempIndex < 0) {
				Integer tempHomeIndex = getNewHomePosition((tempIndex * -1) -1, currentFigure, players.get(currentPlayer).figures);
				if (tempHomeIndex != null) {
					if (!execute) {
						newPosition.put("type", GamePosition.HOME.toString());
						newPosition.put("index", tempHomeIndex);
						return newPosition;
					}
					else {
						currentFigure.setType(GamePosition.HOME);
						currentFigure.setIndex(tempHomeIndex);
						return newPosition.put("ok", "ok");
					}
				}
			}
		}

		// move figure from inside player home to further down inside player home
		else if (currentFigure.getType() == GamePosition.HOME && players.get(currentPlayer).dice.get() < players.get(currentPlayer).figures.length) {
			tempIndex = getNewHomePosition(currentFigure.getIndex() + players.get(currentPlayer).dice.get(), currentFigure, players.get(currentPlayer).figures);
			if (tempIndex != null) {
				if (!execute) {
					newPosition.put("type", GamePosition.HOME.toString());
					newPosition.put("index", tempIndex);
					return newPosition;
				}
				else {
					currentFigure.setType(GamePosition.HOME);
					currentFigure.setIndex(tempIndex);
					return newPosition.put("ok", "ok");
				}
			}
		}
		return newPosition;
	}

	/**
	 * <h1><i>getNewHomePosition</i></h1>
	 * <p>This method checks if a figure of a player can be moved inside
	 * the player's home to a new position. It either returns that new position or
	 * null the current figure cannot be moved to that new position.</p>
	 * @param newIndex - Integer of the new position in player home
	 * @param currentFigure - Figure that the turn is generated for
	 * @param figures - Figure[] array of the players figures
	 * @return Integer - returns null or the new position in player home
	 */
	private Integer getNewHomePosition(Integer newIndex, Figure currentFigure, Figure[] figures) {
		if (newIndex < figures.length && newIndex >= 0) {
			for (Integer i = 0; i < figures.length; i++) {
				if (figures[i] != currentFigure && figures[i].getType() == GamePosition.HOME && figures[i].getIndex() == newIndex) {
					return null;
				}
			}
			return newIndex;
		}
		else { return null; }
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * <h1><i>getNewBoardIndex</i></h1>
	 * <p>This method generates the new position on the board for a given player.
	 * It either returns null if the new position if out of bounds, 0 to 39 for a new position
	 * on the board or -1 to -4 for a new position in the player end.</p>
	 * @param currentIndex - Integer of the new position in player home
	 * @param currenPlayer - PlaeyrColor of the current player
	 * @param player - Player is the current player object
	 * @return Integer - returns null or the new position on the board / player home
	 */
	private Integer getNewBoardIndex(Integer currentIndex, PlayerColor currentPlayer, Player player) {
		Integer boardSize = 40;
		Integer tempIndex = currentIndex + player.dice.get();
		Integer oldVirt = currentIndex - currentPlayer.getOffset();
		Integer newVirt = tempIndex - currentPlayer.getOffset();

		// move on field with wrap around
		if (newVirt < boardSize && newVirt >= 0 && oldVirt >= 0) {
			return tempIndex >= boardSize ? tempIndex -40 : tempIndex;
		}
		// move after one wrap around on field
		else if (newVirt < boardSize && newVirt < 0 && oldVirt < 0) {
			return tempIndex;
		}
		// able to move into player end
		else if (newVirt < boardSize && newVirt < player.figures.length && oldVirt < 0) {
			return (newVirt * -1) -1;
		}
		// workaround so player 0 can move their figure into the player end
		else if (newVirt >= boardSize && (newVirt - boardSize) < player.figures.length && oldVirt >= 0 && currentPlayer.getOffset() == 0) {
			return ((newVirt - 40 ) * -1) -1;
		}
		else { return null; }
	}

	/**
	 * <h1><i>getBoardPosition</i></h1>
	 * <p>This method checks if there is already a player on a given field
	 * on the board. It then returns null, if there is none or the color of that player.</p>
	 * @param index - Integer of the new position
	 * @param players - HashMap<PlayerColor, Player> of all players
	 * @return PlayerColor - either null or the color of the player on that field
	 */
	private PlayerColor getBoardPosition(Integer index, HashMap<PlayerColor, Player> players) {
		for (Map.Entry<PlayerColor, Player> player : players.entrySet()) {
			for (Integer i = 0; i < player.getValue().figures.length; i++) {
				if (player.getValue().figures[i].getType() == GamePosition.FIELD && player.getValue().figures[i].getIndex() == index) {
					return player.getKey();
				}
			}
		}
		return null;
	}

	/**
	 * <h1><i>getBoardFigureIndex</i></h1>
	 * <p>This method checks if there is already a player on a given field
	 * on the board. It then returns null, if there is none or the index of the blocking
	 * figure from the player's figures array.</p>
	 * @param index - Integer of the new position
	 * @param players - HashMap<PlayerColor, Player> of all players
	 * @return Integer - of the figure blocking the field
	 */
	private Integer getBoardFigureIndex(Integer index, HashMap<PlayerColor, Player> players) {
		for (Map.Entry<PlayerColor, Player> player : players.entrySet()) {
			for (Integer i = 0; i < player.getValue().figures.length; i++) {
				if (player.getValue().figures[i].getType() == GamePosition.FIELD && player.getValue().figures[i].getIndex() == index) {
					return i;
				}
			}
		}
		return null;
	}
}
