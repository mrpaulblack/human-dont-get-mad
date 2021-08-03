package game;

import java.util.ArrayList;

//since August 2nd 2021
public class Game {
	private GameState state = GameState.WAITINGFORPLAYER;
	private ArrayList<Player> players = new ArrayList<Player>();
	private Dice dice = new Dice();
	
	//constructor
	public Game() {
		//start
	}
	
	//set game state
	//TODO without param and just set it auto
	public void setGameState(GameState newState) {
		state = newState;
	}
	
	//get the current game state
	public GameState getGameState() {
		return state;
	}
	
	//add a new player to the game and also the socket
	//TODO check if there is still a player needed
	public PlayerColor register(PlayerColor requestedColor, String name, String clientName, Float clientVersion) {
		if (players.size() < 4) {
			PlayerColor assignedColor = null;
			assignedColor = PlayerColor.getAvail(requestedColor);
			players.add(new Player(assignedColor, name, clientName, clientVersion, false));
			LogController.log(Log.INFO, "New Player registered: " + players.get(players.size() -1));
			return assignedColor;
		}
		else { return null; }
	}

}
