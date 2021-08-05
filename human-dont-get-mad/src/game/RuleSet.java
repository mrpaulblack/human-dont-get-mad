package game;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

//TODO write doc
public class RuleSet {
	Integer boardSize = 40;

	//TODO write doc; returns turn option for figure or null if cannot be moved
	protected JSONObject dryRun(PlayerColor currentPlayer, Figure currentFigure, HashMap<PlayerColor, Player> players) {
		String tempPosition = rules(currentPlayer, currentFigure, players, false).toString();
		if (tempPosition == null ) {
			return null;
		}
		else {
			JSONObject json = new JSONObject();
			JSONObject oldPosition = new JSONObject(currentFigure.getJSON());
			JSONObject newPosition = new JSONObject(tempPosition);
			json.put("newPosition", newPosition);
			json.put("oldPosition", oldPosition);
			return json;
		}
	}

	//TODO write doc; returns new Position or if successful
	private JSONObject rules(PlayerColor currentPlayer, Figure currentFigure, HashMap<PlayerColor, Player> players, Boolean execute) {
		JSONObject json = new JSONObject();

		// you can move figure out of player start (when figure in start, dice = 6 and starting field is not currentPlayer)
		if (currentFigure.getType() == GamePosition.START && players.get(currentPlayer).dice.getDice() == 6 && getBoard(getBoardOffset(currentPlayer), players) != currentPlayer) {
			json.put("type", GamePosition.FIELD).toString();
			json.put("index", getBoardOffset(currentPlayer));
			return json;
		}

		// returns new position on the field if possible
		else if (currentFigure.getType() == GamePosition.FIELD && players.get(currentPlayer).dice.getDice() != 0) {
			Integer tempIndex = getNewBoardPosition(currentFigure.getIndex(), currentPlayer, players);
			if (tempIndex != null && getBoard(tempIndex, players) != currentPlayer) {
				json.put("type", GamePosition.FIELD).toString();
				json.put("index", tempIndex);
				return json;
			}
		}
		else if (currentFigure.getType() == GamePosition.HOME  && players.get(currentPlayer).dice.getDice() != 0) {
			//try to move in player
		}
		return null;
	}
	
	//TODO write doc; return new Position on Field
	private Integer getNewBoardPosition(Integer currentIndex, PlayerColor currentPlayer, HashMap<PlayerColor, Player> players) {
		Integer tempIndex = currentIndex + players.get(currentPlayer).dice.getDice();
		// player0
		if (currentPlayer.getValue() == 0 && tempIndex < boardSize) {
			return tempIndex;
		}
		// if there is already a wrap around and not out of bounds
		else if (currentIndex < getBoardOffset(currentPlayer) && tempIndex < getBoardOffset(currentPlayer)) {
			return tempIndex;
		}
		// if new position is bigger then offset for the player and smaller than the size of the board (NO wrap around)
		else if (currentIndex >= getBoardOffset(currentPlayer) && tempIndex < boardSize) {
			return tempIndex;
		}
		// if new position is bigger then board size wrap around if new value is smaller than offset
		else if (currentIndex >= getBoardOffset(currentPlayer) && tempIndex >= boardSize && tempIndex - boardSize < getBoardOffset(currentPlayer)) {
			return tempIndex - boardSize;
		}
		else { return null; }
	}

	//TODO write doc; returns board offset for a player
	private Integer getBoardOffset(PlayerColor color) {
		return 10 * color.getValue();
	}
	
	//TODO write doc; returns the Player Color thats on that index or else null
	private PlayerColor getBoard(Integer index, HashMap<PlayerColor, Player> players) {
		for (Map.Entry<PlayerColor, Player> player : players.entrySet()) {
			for (Integer i = 0; i < player.getValue().figures.length; i++) {
				if (player.getValue().figures[i].getType() == GamePosition.FIELD && player.getValue().figures[i].getIndex() == index) {
					return player.getKey();
				}
			}
		}
		return null;
	}

	//TODO add doc; returns true if all figures are in start
	@SuppressWarnings("unused")
	private Boolean allFiguresStart(Figure[] figures) {
		Integer counter = 0;
		for (Integer i = 0; i < figures.length; i++) {
			if (figures[i].getType() == GamePosition.START) {
				counter++;
			}
		}
		if (counter >= figures.length) {
			return true;
		}
		else { return false; }
	}
}
