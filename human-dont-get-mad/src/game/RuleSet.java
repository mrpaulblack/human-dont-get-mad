package game;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

//TODO write doc
public class RuleSet {
	
	//TODO write doc; returns turn option for figure or null if cannot be moved
	protected JSONObject dryRun(PlayerColor currentPlayer, Figure currentFigure, HashMap<PlayerColor, Player> players) {
		// if all figures are in start
		if (allFiguresStart(players.get(currentPlayer).figures)) {
			if (players.get(currentPlayer).dice.getDice() == 0) {
				// did not get a six after 3 tries
				return null;
			}
			else if (getBoard(getBoardOffset(currentPlayer), players) != null) {
				// start field on board is full
			}
			else {
				// index on start field is empty
			}
		}
		else {
			//check if figure on field or in end can be moved
		}
		return null;
	}
	
	//TODO write doc; returns board offset for a player
	private Integer getBoardOffset(PlayerColor color) {
		return 10 * color.getValue();
	}
	
	//TODO write doc; returns the figure thats on that index or else null
	private Figure getBoard(Integer index, HashMap<PlayerColor, Player> players) {
		for (Map.Entry<PlayerColor, Player> player : players.entrySet()) {
			for (Integer i = 0; i < player.getValue().figures.length; i++) {
				if (player.getValue().figures[i].getType() == GamePosition.FIELD && player.getValue().figures[i].getIndex() == index) {
					return player.getValue().figures[i];
				}
			}
		}
		return null;
	}
	
	//TODO add doc; returns true if all figures are in start
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
