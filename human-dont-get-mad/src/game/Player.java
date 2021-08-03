package game;

import org.json.JSONArray;
import org.json.JSONObject;

//since August 2nd 2021
public class Player {
	private Figure[] figures = {new Figure(0), new Figure(1), new Figure(2), new Figure(3)};
	private PlayerColor color;
	private String name;
	private String clientName;
	@SuppressWarnings("unused")
	private Float clientVersion;
	private Boolean ready = false;
	private Boolean isBot = false;
	
	protected Dice dice = new Dice();
	
	//constructor set client attributes
	public Player(PlayerColor color, String name, String clientName, Float clientVersion, Boolean isBot) {
		this.color = color;
		this.name = name;
		this.clientName = clientName;
		this.clientVersion = clientVersion;
		this.isBot = isBot;
	}
	
	//get color from player
	protected PlayerColor getColor() {
		return color;
	}
	
	//set player to ready
	protected void setReady() {
		ready = true;
	}
	
	//get ready state
	protected Boolean getReady() {
		return ready;
	}
	
	//returns player stats as json in maedn spec
	protected JSONObject toJSON() {
		JSONObject json = new JSONObject();
		JSONArray data = new JSONArray();
		json.put("color", color.toString().toLowerCase());
		json.put("name", name);
		json.put("client", clientName);
		json.put("ready", ready);
		json.put("isBot", isBot);
		json.put("dice", dice.getDice());
		for (Figure figure : figures) {
			data.put(figure.getJSON());
		}
		json.put("figures", data);
		return json;
	}
}
