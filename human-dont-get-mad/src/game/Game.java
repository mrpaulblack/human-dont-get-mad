package game;

import java.util.ArrayList;


public class Game {
	private GameState state = GameState.WAITINGFORPLAYER;
	private ArrayList<Player> player = new ArrayList<Player>();
	
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
	
	//add a new player to the game
	//TODO check if there is still a player needed
	public String register(PlayerColor requestedColor, String name, String clientName, Float clientVersion) {
		PlayerColor assignedColor = null;

		if (requestedColor == null) {
			assignedColor = PlayerColor.getAvail();
		}
		else {
			assignedColor = PlayerColor.getAvail(requestedColor);
		}

		player.add(new Player(assignedColor, name, clientName, clientVersion));
		return assignedColor.toString().toLowerCase();
	}

}
