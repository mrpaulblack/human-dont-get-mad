package client;

import java.awt.Color;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

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
			//TODO wait for GUI and send ready
		}
		else if (json.get("type").equals(MsgType.UPDATE.toString())) {
			updateGameScreen(data);
			
		}
		else if (json.get("type").equals(MsgType.TURN.toString())) {
			//TODO send move
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
			
			try {
				JSONObject player0 = new JSONObject(players.get(0).toString());
				gameWindow.playerStats[0].setText("P1: " + player0.getString("color"));
				gameWindow.playerStats[1].setText(player0.getString("name"));
				System.out.println("Hier WAR ICH");
				System.out.println("+ " + player0.getBoolean("ready"));
				System.out.println("+s " + player0.get("ready"));
				System.out.println("+ " + isReady);
				
				if (player0.getBoolean("ready")) {
					gameWindow.playerStats[0].setBackground(uis.isReadyColor);
					gameWindow.playerStats[1].setBackground(uis.isReadyColor);
				} 
				else {
					gameWindow.playerStats[0].setBackground(uis.background);
					gameWindow.playerStats[1].setBackground(uis.background);
				}
			}
			catch(Exception e) {
				System.out.println("IFTHISTHWOSIDK");			}
			
			try {
				JSONObject player1 = new JSONObject(players.get(1).toString());
				gameWindow.playerStats[2].setText("P1: " + player1.getString("color"));
				gameWindow.playerStats[3].setText(player1.getString("name"));
				if (player1.getBoolean("ready")) {
					gameWindow.playerStats[2].setBackground(uis.isReadyColor);
					gameWindow.playerStats[3].setBackground(uis.isReadyColor);
				} 
				else {
					gameWindow.playerStats[2].setBackground(uis.background);
					gameWindow.playerStats[3].setBackground(uis.background);
				}
			}
			catch(Exception e) {
				//dummyCode
			}
			
			try {
				JSONObject player2 = new JSONObject(players.get(2).toString());
				gameWindow.playerStats[4].setText("P1: " + player2.getString("color"));
				gameWindow.playerStats[5].setText(player2.getString("name"));
				if (player2.getBoolean("ready")) {
					gameWindow.playerStats[4].setBackground(uis.isReadyColor);
					gameWindow.playerStats[5].setBackground(uis.isReadyColor);
				} 
				else {
					gameWindow.playerStats[4].setBackground(uis.background);
					gameWindow.playerStats[5].setBackground(uis.background);
				}
			}
			catch(Exception e) {
				//dummyCode
			}
			
			try {
				JSONObject player3 = new JSONObject(players.get(3).toString());
				gameWindow.playerStats[6].setText("P1: " + player3.getString("color"));
				gameWindow.playerStats[7].setText(player3.getString("name"));
				if (player3.getBoolean("ready")) {
					gameWindow.playerStats[6].setBackground(uis.isReadyColor);
					gameWindow.playerStats[7].setBackground(uis.isReadyColor);
				} 
				else {
					gameWindow.playerStats[6].setBackground(uis.background);
					gameWindow.playerStats[7].setBackground(uis.background);
				}
			}
			catch(Exception e) {
				//dummyCode
			}
		
		}
		else if (data.getString("state").equals("running")) {
			for (JLabel stats : gameWindow.playerStats) {
				stats.setBackground(uis.background);
			}
		}
	}
}
