package game;

import java.util.ArrayList;


public class Game {
	private GameState state = GameState.WAITINGFORPLAYER;
	private ArrayList<Player> player = new ArrayList<Player>();
	
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
	public String setPlayer(String requestedColor, String name, String clientName, Float clientVersion) {
		player.add(new Player("red", name, clientName, clientVersion));
		return "red";
	}

}
