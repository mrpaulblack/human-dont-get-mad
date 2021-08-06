package client;

import java.awt.Color;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import javax.swing.JButton;
import javax.swing.JLabel;

import org.json.JSONArray;
// external: https://mvnrepository.com/artifact/org.json/json/20210307
import org.json.JSONObject;

import game.Log;
import game.LogController;
import game.MsgType;
import server.ClientThread;

/**
* <h1>ClientController</h1>
* <p>The ClientController class is a abstraction that can send json messages
* that implement the maedn protocol spec and decipher recieved json data
* it is the layer between the client socket and the GUI.</p>
* <b>Note:</b> The ClientController should only be instaciated with every client socket.
*
* @author  Paul Braeuning
* @author Tim Menzel
* @version 1.0
* @since   2021-07-23
*/
public class ClientController {
	
	//Placehold if theres is more data to work with
	static boolean gameIsStarted = false;
	boolean isReady = false;
	private String clientName = "human-dont-get-mad";
	public String userName = "";
	public String favColor = "";
	public Integer selsectedOption = -1;
	private Integer[] j = new Integer[4];
	private double clientVersion = 0.1;
	
	UISettings uis = new UISettings();
	private Client player;
	private GameWindow gameWindow;
	
	
	
	/**
	 *	<h1><i>ClientController</i></h1>
	 * <p>This Constructor sets the Client as as the player attribute.
	 * Each Method is going to ac on this client.</p>
	 * @param player - Client this controller is assigned to
	 */
	public ClientController(GameWindow gameWindow) {
		this.gameWindow = gameWindow;
	}
	
	public void player(Client player) {
		this.player = player;
	}
	
	/**
	 *	<h1><i>sendRegister</i></h1>
	 * <p>This method is sending a register message to the server.</p>
	 */
	protected void sendRegister() {
		
		JSONObject json = new JSONObject();
		JSONObject data = new JSONObject();
		json.put("type", MsgType.REGISTER.toString());
		data.put("clientName", clientName);
		data.put("clientVersion", clientVersion);
		data.put("playerName", userName);			
		data.put("requestedColor", favColor);
		json.put("data", data);
		player.out(json.toString());
	}
	
	protected void sendReady() {
		JSONObject json = new JSONObject();
		JSONObject data = new JSONObject();
		json.put("type", MsgType.READY.toString());
		data.put("ready", isReady);
		json.put("data", data);
		player.out(json.toString());
	}
	
	protected void sendMove(int option) {
		JSONObject json = new JSONObject();
		JSONObject data = new JSONObject();
		json.put("type", MsgType.MOVE.toString());
		data.put("selectedOption", option);
		json.put("data", data);
		player.out(json.toString());
		LogController.log(Log.DEBUG, "TX  Selecet Move: " + option);
	}
	
	
	/**
	 *	<h1><i>decoder</i></h1>
	 * <p>This method decodes the recieved data by a server and calls based on the 
	 * parsed type different methods with the data payload as the parameters.</p>
	 * @param input - String of the recieved data
	 */
	protected void decoder(String input) {
		JSONObject json = new JSONObject(input);
		JSONObject data = new JSONObject(json.getJSONObject("data").toString());
		LogController.log(Log.DEBUG, json.get("type") + ": " + data);

		if (json.get("type").equals(MsgType.WELCOME.toString())) {
			//TODO check if protocol and servr software is supported
			sendRegister();
		}
		else if (json.get("type").equals(MsgType.ASSIGNCOLOR.toString())) {
			updateReadyScreen();
			
			//QuikDeBug
			gameWindow.setTitle("Human-Dont-Get-Mad: " + data.getString("color"));
			//TODO wait for GUI and send ready
		}
		else if (json.get("type").equals(MsgType.UPDATE.toString())) {
			updateGameScreen(data);
			
		}
		else if (json.get("type").equals(MsgType.TURN.toString())) {
			updateTurns(data);
		}
		else if (json.get("type").equals(MsgType.PLAYERDISCONNECTED.toString())) {
			//TODO update stats; asynchron
		}
		else if (json.get("type").equals(MsgType.MESSAGE.toString())) {
			//TODO idk
		}
		else if (json.get("type").equals(MsgType.ERROR.toString())) {
			//TODO depends; maybe disconnect
		}
		else {
			//TODO wrong data terminate connection
		}
	}
	
	public void updateReadyScreen() {
		if (gameIsStarted) {
			//TODO got executed if the game has started after the pregame screen
		}
		else {
			//TODO should be executed befor the game started -> if a player pressed ready, set it everywhere green
		}
	}
	
	public void updateGameScreen(JSONObject data) {
		
	
		
		if(data.getString("state").equals("waitingForPlayers")) {
			
			JSONArray players = new JSONArray(data.getJSONArray("players"));
			
			
			for (int i = 0; i < players.length(); i++) {
				JSONObject player = new JSONObject(players.get(i).toString());
				
				gameWindow.playerStats[i].setText("P" + (i + 1) + ": " + player.getString("color"));
				gameWindow.playerNames[i].setText(player.getString("name"));
				
				if (player.getBoolean("ready")) {
					gameWindow.playerStats[i].setBackground(uis.isReadyColor);
					gameWindow.playerNames[i].setBackground(uis.isReadyColor);
				} 
				else {
					gameWindow.playerStats[i].setBackground(uis.background);
					gameWindow.playerNames[i].setBackground(uis.background);
				}
			}
		}
		else if (data.getString("state").equals("running")) {
			
			gameIsStarted = true;
					
			for (JLabel stats : gameWindow.playerStats) 
				stats.setBackground(uis.background);
				
			for (JLabel names : gameWindow.playerNames)
				names.setBackground(uis.background);
				
			for(JButton[] Houses : gameWindow.houses ) 
				for (JButton subHouses : Houses)
					subHouses.setBackground(Color.LIGHT_GRAY);
						
			for (JButton[] Bases : gameWindow.bases)
				for (JButton subBases : Bases)
					subBases.setBackground(Color.LIGHT_GRAY);
				
			JSONArray players = new JSONArray(data.getJSONArray("players"));	
			
			for (int i = 0; i < players.length(); i++) {
						
				JSONObject player = new JSONObject(players.get(i).toString());
				LogController.log(Log.DEBUG, "Player Array " + player);
						
					for (int j = 0; j <= 3; j++) {
						
						JSONArray figuers = new JSONArray(player.getJSONArray("figures"));
						JSONObject figuer = new JSONObject(figuers.get(j).toString());
						LogController.log(Log.DEBUG, "Figure Array " + figuer);
							
						switch (i) {
						case 0: 
							if (figuer.get("type").equals("start")) {
								gameWindow.redBases[figuer.getInt("index")].setBackground(Color.red);
							}
							else if (figuer.get("type").equals("field")) {
								gameWindow.buttons[figuer.getInt("index")].setBackground(Color.red);
							}
							else if (figuer.get("type").equals("home")) {
								gameWindow.redHouses[figuer.getInt("index")].setBackground(Color.red);
									
							}break;
						case 1: 
							if (figuer.get("type").equals("start")) {
								gameWindow.greenBases[figuer.getInt("index")].setBackground(Color.green);
							}
							else if (figuer.get("type").equals("field")) {
								gameWindow.buttons[figuer.getInt("index")].setBackground(Color.green);
							}
							else if (figuer.get("type").equals("home")) {
								gameWindow.greenHouses[figuer.getInt("index")].setBackground(Color.green);
									
							}break;
						case 2:
							if (figuer.get("type").equals("start")) {
								gameWindow.blueBases[figuer.getInt("index")].setBackground(Color.blue);
							}
							else if (figuer.get("type").equals("field")) {
								gameWindow.buttons[figuer.getInt("index")].setBackground(Color.blue);
							}
							else if (figuer.get("type").equals("home")) {
								gameWindow.blueHouses[figuer.getInt("index")].setBackground(Color.blue);
									
							}break;
						case 3:
							if (figuer.get("type").equals("start")) {
								gameWindow.yellowBases[figuer.getInt("index")].setBackground(Color.yellow);
							}
							else if (figuer.get("type").equals("field")) {
								gameWindow.buttons[figuer.getInt("index")].setBackground(Color.yellow);
							}
							else if (figuer.get("type").equals("home")) {
								gameWindow.yellowHouses[figuer.getInt("index")].setBackground(Color.yellow);
								
							}
						}
					}
				}
			}
		}
	
	//NOT TESTET JET BECAUSE OF MISSING DATA
	public void updateTurns(JSONObject data) {
		
		JSONArray options = new JSONArray(data.getJSONArray("options"));
		
		
		
		LogController.log(Log.DEBUG, "Turns " + options.toString());
		LogController.log(Log.DEBUG, "Turns " + options.length());
		
		if(options.length() != 0) {
		
			for (int i = 0; i < options.length(); i++) {
	
				JSONObject option = new JSONObject(options.get(i).toString());
				JSONObject oldPosition = new JSONObject(option.get("oldPosition").toString());
				JSONObject newPosition = new JSONObject(option.get("newPosition").toString());
				
				LogController.log(Log.DEBUG, "Possible Move: " + options.get(i).toString());
				
				switch (gameWindow.getTitle()) {
					case "Human-Dont-Get-Mad: red": 
						
						System.out.println("+" + option.get("oldPosition"));
						System.out.println("x" + option.get("oldPosition").toString());
						
						int k = i;
						if (oldPosition.get("type").equals("start")) {
							gameWindow.redBases[oldPosition.getInt("index")].setBackground(Color.black);
							gameWindow.redBases[oldPosition.getInt("index")].addActionListener(e -> {
								sendMove(k);
							});;
						}
						else if (oldPosition.get("type").equals("field")) {
							gameWindow.buttons[oldPosition.getInt("index")].setBackground(Color.black);
							gameWindow.buttons[oldPosition.getInt("index")].addActionListener(e -> {
								sendMove(k);
							});;	
						}
						else if (oldPosition.get("type").equals("home")) {
							gameWindow.redHouses[oldPosition.getInt("index")].setBackground(Color.black);
							gameWindow.buttons[oldPosition.getInt("index")].addActionListener(e -> {
								sendMove(k);
							});;	
						}
						break;
					case "Human-Dont-Get-Mad: green": 
						
						break;
					case "Human-Dont-Get-Mad: blue": 
						
						break;
					case "Human-Dont-Get-Mad: yellow": 
						
						break;
				}
				
			}
		}
		else {
			sendMove(selsectedOption);
			LogController.log(Log.DEBUG, "TX Move: " + selsectedOption);
		}
	}
}
