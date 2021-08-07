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
		JSONObject tempPosition = new JSONObject(rules(currentPlayer, currentFigure, players, false).toString());

		if (tempPosition.has("index")) {
			json.put("oldPosition", currentFigure.getJSON());
			json.put("newPosition", tempPosition);
			return json;
		}
		else { return json; }
	}
	
	// TODO add doc; execute turn
	protected Boolean execute(PlayerColor currentPlayer, Figure currentFigure, HashMap<PlayerColor, Player> players) {
		LogController.log(Log.DEBUG, "Trying to execute turn for " + currentPlayer);
		if (rules(currentPlayer, currentFigure, players, true).has("ok")) {
			return true;
		}
		else { return false; }
	}

	//TODO write doc; returns new Position or if successful
	private JSONObject rules(PlayerColor currentPlayer, Figure currentFigure, HashMap<PlayerColor, Player> players, Boolean execute) {
		JSONObject newPosition = new JSONObject();
		Integer tempIndex;
		// you can move figure out of player start (when figure in start, dice = 6 and starting field is not currentPlayer)
		if (currentFigure.getType() == GamePosition.START && players.get(currentPlayer).dice.getDice() == 6 && getBoardPosition(currentPlayer.getOffset(), players) != currentPlayer) {
			if (!execute) {
				newPosition.put("type", GamePosition.FIELD.toString());
				newPosition.put("index", currentPlayer.getOffset());
				return newPosition;
			}
			else {
				currentFigure.setType(GamePosition.FIELD);
				currentFigure.setIndex(currentPlayer.getOffset());
				return newPosition.put("ok", "ok");
			}
		}
		// returns new position on the field if possible
		else if (currentFigure.getType() == GamePosition.FIELD) {
			tempIndex = getNewBoardIndex(currentFigure.getIndex(), currentPlayer, players.get(currentPlayer));
			// return move on field
			if (tempIndex != null && tempIndex > 0 && getBoardPosition(tempIndex, players) != currentPlayer) {
				if (!execute) {
					newPosition.put("type", GamePosition.FIELD.toString());
					newPosition.put("index", tempIndex);
					return newPosition;
				}
				else {
					currentFigure.setType(GamePosition.FIELD);
					currentFigure.setIndex(tempIndex);
					return newPosition.put("ok", "ok");
				}
			}
			// return move in player home
			else if (tempIndex != null && tempIndex <= 0) {
				Integer tempHomeIndex = getNewHomePosition(tempIndex * -1, currentFigure, players.get(currentPlayer).figures);
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
		// returns new position in player home if  possible
		else if (currentFigure.getType() == GamePosition.HOME && players.get(currentPlayer).dice.getDice() < players.get(currentPlayer).figures.length) {
			tempIndex = getNewHomePosition(currentFigure.getIndex() + players.get(currentPlayer).dice.getDice(), currentFigure, players.get(currentPlayer).figures);
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
}
