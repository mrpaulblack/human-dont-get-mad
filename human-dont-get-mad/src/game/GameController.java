package game;

import org.json.JSONObject;

//since August 03rd 2021
public interface GameController {
	//TODO add doc
	public PlayerColor register(PlayerColor requestedColor, String name, String clientName, Float clientVersion);
	public Boolean ready(PlayerColor color);
	public GameState getGameState();
	public JSONObject toJSON();
}
