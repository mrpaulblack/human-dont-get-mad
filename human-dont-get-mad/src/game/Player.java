package game;

import org.json.JSONArray;
import org.json.JSONObject;

//since August 2nd 2021
/**
* <h1>Player</h1>
* <p>Player class for each player of the game. Each player has their own dice and 4
* figures in their start region. This class also saves all the attributes provided by the client,
* like name, client name and the version of the client used to connect to the server.</p>
*
* @author  Paul Braeuning
* @version 1.0
* @since   2021-08-02
* @apiNote MAEDN 3.0
*/
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
	
	/**
	 * <h1><i>Player</i></h1>
	 * <p>Player constructor that writes parameters to attributes.</p>
	 * @param color - PlayerColor of that player
	 * @param name - String with the name of the player
	 * @param clientName - String with the name of the client
	 * @param clientVersion - Float with the version of the client
	 * @param isBot - set to true if the player is a BOT
	 */
	public Player(PlayerColor color, String name, String clientName, Float clientVersion, Boolean isBot) {
		this.color = color;
		this.name = name;
		this.clientName = clientName;
		this.clientVersion = clientVersion;
		this.isBot = isBot;
	}
	
	/**
	 * <h1><i>setReady</i></h1>
	 * <p>Sets player to ready.</p>
	 * @param state - Boolean of is the player ready or not
	 */
	protected void setReady(Boolean state) {
		ready = state;
	}
	
	/**
	 * <h1><i>getReady</i></h1>
	 * <p>This method returns if the player is ready or not.</p>
	 * @return Boolean - true if the player is ready; false if not
	 */
	protected Boolean getReady() {
		return ready;
	}
	
	/**
	 * <h1><i>toJSON</i></h1>
	 * <p>This method returns all positions of all player figures and the attributes
	 * of the player as JSON in MAEDN specifications.</p>
	 * @return JSONObject - returns JSON about the player
	 */
	protected JSONObject toJSON() {
		JSONObject json = new JSONObject();
		JSONArray data = new JSONArray();
		json.put("color", color.toString());
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
