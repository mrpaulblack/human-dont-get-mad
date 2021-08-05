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
		LogController.log(Log.DEBUG, "Generating Turn for: " + players.get(currentPlayer).toJSON(false));
		if (tempPosition == null ) {
			return null;
		}
		else {
			JSONObject json = new JSONObject();
			JSONObject oldPosition = new JSONObject(currentFigure.getJSON());
			JSONObject newPosition = new JSONObject(tempPosition);
			json.put("newPosition", newPosition);
			json.put("oldPosition", oldPosition);
			LogController.log(Log.DEBUG, "Turn avail:" + json);
			return json;
		}
	}

	//TODO write doc; returns new Position or if successful
	private JSONObject rules(PlayerColor currentPlayer, Figure currentFigure, HashMap<PlayerColor, Player> players, Boolean execute) {
		JSONObject json = new JSONObject();
		Integer tempIndex;

		// you can move figure out of player start (when figure in start, dice = 6 and starting field is not currentPlayer)
		if (currentFigure.getType() == GamePosition.START && players.get(currentPlayer).dice.getDice() == 6 && getBoard(currentPlayer.getOffset(), players) != currentPlayer) {
			json.put("type", GamePosition.FIELD).toString();
			json.put("index", currentPlayer.getOffset());
			return json;
		}

		// returns new position on the field if possible
		else if (currentFigure.getType() == GamePosition.FIELD && players.get(currentPlayer).dice.getDice() != 0) {
			tempIndex = getNewBoardPosition(currentFigure.getIndex(), currentPlayer, players.get(currentPlayer));
			//TODO need to check if player can move their figure into their home
			if (tempIndex != null && getBoard(tempIndex, players) != currentPlayer) {
				json.put("type", GamePosition.FIELD).toString();
				json.put("index", tempIndex);
				return json;
			}
		}
		
		// returns new position in player home if  possible
		else if (currentFigure.getType() == GamePosition.HOME  && players.get(currentPlayer).dice.getDice() != 0 && players.get(currentPlayer).dice.getDice() < 4) {
			tempIndex = getNewHomePosition(players.get(currentPlayer).dice, currentFigure, players.get(currentPlayer).figures);
			if (tempIndex != null) {
				json.put("type", GamePosition.HOME).toString();
				json.put("index", tempIndex);
				return json;
			}
		}
		return null;
	}

	//TODO write doc; check if figure can be moved in player home
	private Integer getNewHomePosition(Dice dice, Figure currentFigure, Figure[] figures) {
		if (currentFigure.getIndex() + dice.getDice() < figures.length) {
			for (Integer i = 0; i < figures.length; i++) {
				if (figures[i] != currentFigure && figures[i].getType() == GamePosition.HOME && figures[i].getIndex() == currentFigure.getIndex() + dice.getDice()) {
					return null;
				}
			}
			return currentFigure.getIndex() + dice.getDice();
		}
		else { return null; }
	}
	
	//TODO write doc; return new Position on Field
	private Integer getNewBoardPosition(Integer currentIndex, PlayerColor currentPlayer, Player player) {
		Integer tempIndex = currentIndex + player.dice.getDice();
		// player0
		if (currentPlayer.getValue() == 0 && tempIndex < boardSize) {
			return tempIndex;
		}
		// if there is already a wrap around and not out of bounds
		else if (currentIndex < currentPlayer.getOffset() && tempIndex < currentPlayer.getOffset()) {
			return tempIndex;
		}
		// if new position is bigger then offset for the player and smaller than the size of the board (NO wrap around)
		else if (currentIndex >= currentPlayer.getOffset() && tempIndex < boardSize) {
			return tempIndex;
		}
		// if new position is bigger then board size wrap around if new value is smaller than offset
		else if (currentIndex >= currentPlayer.getOffset() && tempIndex >= boardSize && tempIndex - boardSize < currentPlayer.getOffset()) {
			return tempIndex - boardSize;
		}
		else { return null; }
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
