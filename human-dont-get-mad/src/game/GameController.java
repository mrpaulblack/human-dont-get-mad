package game;

import org.json.JSONArray;

//since August 03rd 2021
public interface GameController {
	//TODO add doc
	public void setGameState();
	public GameState getGameState();
	public PlayerColor register(PlayerColor requestedColor, String name, String clientName, Float clientVersion);
	public JSONArray toJSON();
}
