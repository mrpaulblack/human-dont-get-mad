package game;

import java.util.ArrayList;

import org.json.JSONArray;

//since August 2nd 2021
public class Game implements GameController{
	private GameState state = GameState.WAITINGFORPLAYER;
	private ArrayList<Player> players = new ArrayList<Player>();
	
	//constructor
	public Game() {
		//start
	}
	
	//set game state by calling method
	public void setGameState() {
		switch(state) {
		case WAITINGFORPLAYER: state = GameState.RUNNING;
		case RUNNING: state = GameState.FINISHED;
		case FINISHED:;
		}
	}
	
	//get the current game state
	public GameState getGameState() {
		return state;
	}
	
	//add a new player to the game and also the socket
	public PlayerColor register(PlayerColor requestedColor, String name, String clientName, Float clientVersion) {
		if (players.size() < 4) {
			PlayerColor assignedColor = null;
			assignedColor = PlayerColor.getAvail(requestedColor);
			players.add(new Player(assignedColor, name, clientName, clientVersion, false));
			LogController.log(Log.INFO, "New Player registered: " + players.get(players.size() -1).toJSON().toString());
			return assignedColor;
		}
		else { return null; }
	}
	
	//get json of current game for all player
	public JSONArray toJSON() {
		JSONArray json = new JSONArray();
		for (Player player : players) {
			json.put(player.toJSON());
		}
		return json;
	}

}
