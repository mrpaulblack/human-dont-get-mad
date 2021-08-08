package client;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
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
	public boolean isReady = false;
	public boolean diceShowable = false;
	private String clientName = "human-dont-get-mad";
	public String userName = "";
	public String favColor = "";
	public Integer selsectedOption = -1;
	private Integer dice = 0;
	private double clientVersion = 0.1;
	
	private Client player;
	private GameWindow gameWindow;
	UISettings colors = new UISettings();
	
//Images
	 ImageIcon diceOne = new ImageIcon(this.getClass().getResource("/DiceOne.png"));
	 ImageIcon diceTwo = new ImageIcon(this.getClass().getResource("/DiceTwo.png"));
	 ImageIcon diceThree = new ImageIcon(this.getClass().getResource("/DiceThree.png"));
	 ImageIcon diceFour = new ImageIcon(this.getClass().getResource("/DiceFour.png"));
	 ImageIcon diceFive = new ImageIcon(this.getClass().getResource("/DiceFive.png"));
	 ImageIcon diceSix = new ImageIcon(this.getClass().getResource("/DiceSix.png"));
	
	
	
	
	public void rollDice(){	
		
		/*
			Random randomDuration = new Random();
			Random randomDice = new Random();
			int showImage = 0;
			int g = randomDuration.nextInt(30);
			for (int h = 0; h < 0; h++) {	//Fix later
				
				
				showImage = randomDice.nextInt(6);
				diceSelection(showImage);
				System.out.println("CALL");
			}*/
			diceSelection(dice);
			LogController.log(Log.DEBUG, "Current Dice: " + dice);
	}
	
	public void diceSelection(int t) {
		switch (t) {
		case 1:	gameWindow.dice.setIcon(diceOne);
		LogController.log(Log.DEBUG, "Current dice ONE"); //change to TRACE
			break;
			
		case 2: gameWindow.dice.setIcon(diceTwo);
		LogController.log(Log.DEBUG, "Current dice TWO");
			break;
			
		case 3: gameWindow.dice.setIcon(diceThree);
		LogController.log(Log.DEBUG, "Current dice THREE");
			break;
			
		case 4: gameWindow.dice.setIcon(diceFour);
		LogController.log(Log.DEBUG, "Current dice FOUR");
			break;
			
		case 5: gameWindow.dice.setIcon(diceFive);
		LogController.log(Log.DEBUG, "Current dice FIVE");
			break;
			
		case 6: gameWindow.dice.setIcon(diceSix);
		LogController.log(Log.DEBUG, "Current dice SIX");
			break;
		}
	}
	
	
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
		LogController.log(Log.DEBUG, "TX:  SendRegister: " + data.toString());
	}
	
	protected void sendReady() {
		JSONObject json = new JSONObject();
		JSONObject data = new JSONObject();
		json.put("type", MsgType.READY.toString());
		data.put("ready", isReady);
		json.put("data", data);
		player.out(json.toString());
		LogController.log(Log.DEBUG, "TX:  SendReady: " + data.toString());
	}
	
	protected void sendMove(int option) {
		JSONObject json = new JSONObject();
		JSONObject data = new JSONObject();
		json.put("type", MsgType.MOVE.toString());
		data.put("selectedOption", option);
		json.put("data", data);
		player.out(json.toString());
		LogController.log(Log.DEBUG, "TX:  SendMove: " + data.toString());
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
			sendRegister();
		}
		else if (json.get("type").equals(MsgType.ASSIGNCOLOR.toString())) {
			updateReadyScreen();
			gameWindow.setTitle("Human-Dont-Get-Mad: " + data.getString("color"));
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
		
		for (JLabel JL : gameWindow.playerNames)
			JL.setBackground(colors.background);
		for (JLabel JL : gameWindow.playerStats)
			JL.setBackground(colors.background);
		
		System.out.println(data.getString("state"));
		System.out.println(data.has("currentPlayer"));
		if (data.has("currentPlayer")) {
			
			System.out.println(data.getString("currentPlayer"));
			
			if (data.get("currentPlayer").equals("red")) {
				
				gameWindow.playerNames[0].setBackground(colors.cRedPlayer);
				gameWindow.playerStats[0].setBackground(colors.cRedPlayer);
			}
			else if (data.get("currentPlayer").equals("blue")) {
				
				gameWindow.playerNames[1].setBackground(colors.cBluePlayer);
				gameWindow.playerStats[1].setBackground(colors.cBluePlayer);
			}
			else if (data.get("currentPlayer").equals("green")) {
				
				gameWindow.playerNames[2].setBackground(colors.cGreenPlayer);
				gameWindow.playerStats[2].setBackground(colors.cGreenPlayer);
			}
			else if (data.get("currentPlayer").equals("yellow")) {
				
				gameWindow.playerNames[3].setBackground(colors.cYellowPlayer);
				gameWindow.playerStats[3].setBackground(colors.cYellowPlayer);
			}
		
		}
		
		 
		LogController.log(Log.TRACE, "RX: Update: " + data.toString());
		JSONArray players = new JSONArray(data.getJSONArray("players"));
		JSONObject playert = new JSONObject(players.get(0).toString());
		gameWindow.dice.setBackground(Color.white);
		
		dice = (playert.getInt("dice"));
		rollDice();
		
		if(data.getString("state").equals("waitingForPlayers")) {
			
		//	gameWindow.dice.setBackground(Color.white);
			
			for (int i = 0; i < players.length(); i++) {
				JSONObject player = new JSONObject(players.get(i).toString());
				
				gameWindow.playerStats[i].setText("P" + (i + 1) + ": " + player.getString("color"));
				gameWindow.playerNames[i].setText(player.getString("name"));
				
				if (player.getBoolean("ready")) {
					gameWindow.playerStats[i].setBackground(colors.isReadyColor);
					gameWindow.playerNames[i].setBackground(colors.isReadyColor);
				} 
				else {
					gameWindow.playerStats[i].setBackground(colors.background);
					gameWindow.playerNames[i].setBackground(colors.background);
				}
			}
		}
		else if (data.getString("state").equals("running")) {
			
			gameIsStarted = true;
						
			for(JButton[] Houses : gameWindow.houses ) 
				for (JButton subHouses : Houses)
					subHouses.setBackground(colors.background);
						
			for (JButton[] Bases : gameWindow.bases)
				for (JButton subBases : Bases)
					subBases.setBackground(colors.background);
			
			for (JButton field : gameWindow.buttons)
				field.setBackground(Color.white);
			
			for (int i = 0; i < players.length(); i++) {
				
				JSONObject player = new JSONObject(players.get(i).toString());
				LogController.log(Log.DEBUG, "Player Array " + player);
				
					for (int j = 0; j <= 3; j++) {
						
						JSONArray figuers = new JSONArray(player.getJSONArray("figures"));
						JSONObject figuer = new JSONObject(figuers.get(j).toString());
						LogController.log(Log.TRACE, "Figure Array " + figuer);
							
						if (player.getString("color").equals("red")) {
							if (figuer.get("type").equals("start")) {
								gameWindow.redBases[figuer.getInt("index")].setBackground(Color.red);
							}
							else if (figuer.get("type").equals("field")) {
								gameWindow.buttons[figuer.getInt("index")].setBackground(Color.red);
							}
							else if (figuer.get("type").equals("home")) {
								gameWindow.redHouses[figuer.getInt("index")].setBackground(Color.red);
							}
						}
						else if(player.getString("color").equals("yellow")) {
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
						else if(player.getString("color").equals("blue")) {
						
							if (figuer.get("type").equals("start")) {
								gameWindow.blueBases[figuer.getInt("index")].setBackground(Color.blue);
							}
							else if (figuer.get("type").equals("field")) {
								gameWindow.buttons[figuer.getInt("index")].setBackground(Color.blue);
							}
							else if (figuer.get("type").equals("home")) {
								gameWindow.blueHouses[figuer.getInt("index")].setBackground(Color.blue);
							}		
						}
						else if(player.getString("color").equals("green")) {
							
							if (figuer.get("type").equals("start")) {
								gameWindow.greenBases[figuer.getInt("index")].setBackground(Color.green);
							}
							else if (figuer.get("type").equals("field")) {
								gameWindow.buttons[figuer.getInt("index")].setBackground(Color.green);
							}
							else if (figuer.get("type").equals("home")) {
								gameWindow.greenHouses[figuer.getInt("index")].setBackground(Color.green);
								
							}
						}
					}
				}
			}
			else if (data.getString("state").equals("finished")) {
				
			}
		}
	
	//NOT TESTET JET BECAUSE OF MISSING DATA
	public void updateTurns(JSONObject data) {
		
		gameWindow.dice.setText("");
		
		LogController.log(Log.TRACE, "RX: Turns: " + data.toString());
		
		JSONArray options = new JSONArray(data.getJSONArray("options"));
		
		
		if(options.length() != 0) {
		
			for (int i = 0; i < options.length(); i++) {
	
				JSONObject option = new JSONObject(options.get(i).toString());
				JSONObject oldPosition = new JSONObject(option.get("oldPosition").toString());
				JSONObject newPosition = new JSONObject(option.get("newPosition").toString());
				
				LogController.log(Log.DEBUG, "Possible Move: " + options.get(i).toString());
				
				int k = i;
				switch (gameWindow.getTitle()) {
					case "Human-Dont-Get-Mad: red": 
						
						if (oldPosition.get("type").equals("start")) {
							gameWindow.redBases[oldPosition.getInt("index")].setBackground(Color.black);
							gameWindow.redBases[oldPosition.getInt("index")].addActionListener(e -> {
								removeAL();
								sendMove(k);
								 
							gameWindow.redBases[oldPosition.getInt("index")].setBackground(Color.LIGHT_GRAY);
							});;
						}
						else if (oldPosition.get("type").equals("field")) {
							gameWindow.buttons[oldPosition.getInt("index")].setBackground(Color.black);
							gameWindow.buttons[oldPosition.getInt("index")].addActionListener(e -> {
								 removeAL();
								sendMove(k);
								
							});;	
						}
						else if (oldPosition.get("type").equals("home")) {
							gameWindow.redHouses[oldPosition.getInt("index")].setBackground(Color.black);
							gameWindow.redHouses[oldPosition.getInt("index")].addActionListener(e -> {
								removeAL();
								sendMove(k);
								 
							});;	
						}
						break;
					case "Human-Dont-Get-Mad: green": 

						if (oldPosition.get("type").equals("start")) {
							gameWindow.greenBases[oldPosition.getInt("index")].setBackground(Color.black);
							gameWindow.greenBases[oldPosition.getInt("index")].addActionListener(e -> {
								removeAL();
								sendMove(k);
							});;
						}
						else if (oldPosition.get("type").equals("field")) {
							gameWindow.buttons[oldPosition.getInt("index")].setBackground(Color.black);
							gameWindow.buttons[oldPosition.getInt("index")].addActionListener(e -> {
								removeAL();
								sendMove(k);
							});;	
						}
						else if (oldPosition.get("type").equals("home")) {
							gameWindow.greenHouses[oldPosition.getInt("index")].setBackground(Color.black);
							gameWindow.greenHouses[oldPosition.getInt("index")].addActionListener(e -> {
								removeAL();
								sendMove(k);
							});;	
						}
						break;
						
					case "Human-Dont-Get-Mad: blue": 

						if (oldPosition.get("type").equals("start")) {
							gameWindow.blueBases[oldPosition.getInt("index")].setBackground(Color.black);
							gameWindow.blueBases[oldPosition.getInt("index")].addActionListener(e -> {
								removeAL();
								sendMove(k);
							});;
						}
						else if (oldPosition.get("type").equals("field")) {
							gameWindow.buttons[oldPosition.getInt("index")].setBackground(Color.black);
							gameWindow.buttons[oldPosition.getInt("index")].addActionListener(e -> {
								removeAL();
								sendMove(k);
							});;	
						}
						else if (oldPosition.get("type").equals("home")) {
							gameWindow.blueHouses[oldPosition.getInt("index")].setBackground(Color.black);
							gameWindow.blueHouses[oldPosition.getInt("index")].addActionListener(e -> {
								removeAL();
								sendMove(k);
							});;	
						}
						break;
						
					case "Human-Dont-Get-Mad: yellow": 

						if (oldPosition.get("type").equals("start")) {
							gameWindow.yellowBases[oldPosition.getInt("index")].setBackground(Color.black);
							gameWindow.yellowBases[oldPosition.getInt("index")].addActionListener(e -> {
								removeAL();
								sendMove(k);
							});;
							
						}
						else if (oldPosition.get("type").equals("field")) {
							gameWindow.buttons[oldPosition.getInt("index")].setBackground(Color.black);
							gameWindow.buttons[oldPosition.getInt("index")].addActionListener(e -> {
								removeAL();
								sendMove(k);
							});;	
						}
						else if (oldPosition.get("type").equals("home")) {
							gameWindow.yellowHouses[oldPosition.getInt("index")].setBackground(Color.black);
							gameWindow.yellowHouses[oldPosition.getInt("index")].addActionListener(e -> {
								removeAL();
								sendMove(k);
							});;	
						}
						break;
				}
			}
		}
		else {
			gameWindow.dice.addActionListener(e -> {
				sendMove(selsectedOption);
			});
		}
	}
	
	public void removeAL() {
		for (JButton btn : gameWindow.buttons)
			for (ActionListener al : btn.getActionListeners())
				btn.removeActionListener(al);
		
		for (JButton[] btn : gameWindow.bases)
			for (JButton btnST2 : btn)
				for (ActionListener al :btnST2.getActionListeners())
					btnST2.removeActionListener(al);
		
		for (JButton[] btn : gameWindow.houses)
			for (JButton btnST2 : btn)
				for (ActionListener al :btnST2.getActionListeners())
					btnST2.removeActionListener(al);
	}
}
