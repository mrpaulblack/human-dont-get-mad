package game;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

//TODO write doc
public class RuleSet {
	Integer boardSize = 40;

	//TODO write doc; returns turn option for figure or null if cannot be moved
	protected JSONObject dryrun(PlayerColor currentPlayer, Figure currentFigure, HashMap<PlayerColor, Player> players) {
		JSONObject json = new JSONObject();
		JSONObject tempPosition = new JSONObject(rules(currentPlayer, currentFigure, players).toString());

		if (tempPosition.has("index")) {
			JSONObject oldPosition = new JSONObject(currentFigure.getJSON().toString());
			JSONObject newPosition = new JSONObject(tempPosition.toString());
			json.put("oldPosition", oldPosition);
			json.put("newPosition", newPosition);
			LogController.log(Log.DEBUG, "Turn avail: " + json);
			return json;
		}
		else {
			return json;
		}
	}
	
	// TODO add doc; execute turn
	protected Boolean execute(PlayerColor currentPlayer, Figure currentFigure, HashMap<PlayerColor, Player> players) {
		return false;
	}

	//TODO write doc; returns new Position or if successful
	private JSONObject rules(PlayerColor currentPlayer, Figure currentFigure, HashMap<PlayerColor, Player> players) {
		JSONObject newPosition = new JSONObject();
		Integer tempIndex;

		// you can move figure out of player start (when figure in start, dice = 6 and starting field is not currentPlayer)
		if (currentFigure.getType() == GamePosition.START && players.get(currentPlayer).dice.getDice() == 6 && getBoardPosition(currentPlayer.getOffset(), players) != currentPlayer) {
			newPosition.put("type", GamePosition.FIELD.toString());
			newPosition.put("index", currentPlayer.getOffset());
			return newPosition;
		}

		// returns new position on the field if possible
		else if (currentFigure.getType() == GamePosition.FIELD) {
			tempIndex = getNewBoardIndex(currentFigure.getIndex(), currentPlayer, players.get(currentPlayer));
			// return move on field
			if (tempIndex != null && tempIndex > 0 && getBoardPosition(tempIndex, players) != currentPlayer) {
				newPosition.put("type", GamePosition.FIELD.toString());
				newPosition.put("index", tempIndex);
				return newPosition;
			}
			// return move in player home
			else if (tempIndex != null && tempIndex <= 0) {
				Integer tempHomeIndex = getNewHomePosition(tempIndex * -1, currentFigure, players.get(currentPlayer).figures);
				if (tempHomeIndex != null) {
					newPosition.put("type", GamePosition.HOME.toString());
					newPosition.put("index", tempHomeIndex);
					return newPosition;
				}
			}
		}
		
		// returns new position in player home if  possible
		else if (currentFigure.getType() == GamePosition.HOME && players.get(currentPlayer).dice.getDice() < players.get(currentPlayer).figures.length) {
			tempIndex = getNewHomePosition(currentFigure.getIndex() + players.get(currentPlayer).dice.getDice(), currentFigure, players.get(currentPlayer).figures);
			if (tempIndex != null) {
				newPosition.put("type", GamePosition.HOME.toString());
				newPosition.put("index", tempIndex);
				return newPosition;
			}
		}
		return newPosition;
	}

	//TODO write doc; check if figure can be moved in player home
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
	
	//TODO write doc; return new Position on Field
	private Integer getNewBoardIndex(Integer currentIndex, PlayerColor currentPlayer, Player player) {
		Integer tempIndex = currentIndex + player.dice.getDice();
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
			return newVirt * -1;
		}
		else { return null; }
	}
	
	//TODO write doc; returns the Player Color thats on that index or else null
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
