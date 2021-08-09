package client;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.util.concurrent.TimeUnit;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

import org.json.JSONArray;
// external: https://mvnrepository.com/artifact/org.json/json/20210307
import org.json.JSONObject;

import game.Log;
import game.LogController;
import game.MsgType;
import game.PlayerColor;
/**
* <h1>ClientController</h1>
* <p>The ClientController class is a abstraction that can send json messages
* that implement the maedn protocol spec and decipher recieved json data
* it is the layer between the client socket and the GUI.</p>
* <b>Note:</b> The ClientController should only be instaciated with every client socket.
*
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
	private String showtext = "";
	public Integer selsectedOption = -1;
	private float clientVersion = 1.0f;
	
	private Client player;
	private GameWindow gameWindow;
	private Messager messager;
	UISettings colors = new UISettings();
	
	
	
//Images
	 ImageIcon diceOne = new ImageIcon(this.getClass().getResource("/DiceOne.png"));
	 ImageIcon diceTwo = new ImageIcon(this.getClass().getResource("/DiceTwo.png"));
	 ImageIcon diceThree = new ImageIcon(this.getClass().getResource("/DiceThree.png"));
	 ImageIcon diceFour = new ImageIcon(this.getClass().getResource("/DiceFour.png"));
	 ImageIcon diceFive = new ImageIcon(this.getClass().getResource("/DiceFive.png"));
	 ImageIcon diceSix = new ImageIcon(this.getClass().getResource("/DiceSix.png"));
	
	
	
	 /**
	 *	<h1><i>ClientController</i></h1>
	 * <p>This Constructor sets the Client as as the player attribute.
	 * Each Method is going to ac on this client.</p>
	 * @param GameWindow
	 * @param Messager
	 */
	public ClientController(GameWindow gameWindow, Messager messager) {
		this.gameWindow = gameWindow;
		this.messager = messager;
	}
		
	public void player(Client player) {
		this.player = player;
	} 
	
	/**
	 *	<h1><i>diceSelecion</i></h1>
	 * <p>chose and print the correct dice to every player.</p>
	 * @param dice
	 */
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
			
//		default:rollDice(t);
//			break;
		}
	}
	
	/**
	 *	<h1><i>sendRegister</i></h1>
	 * <p>This method is sending a register message to the server.</p>
	 */
	protected void sendRegister() {
		
		ReadysendText();	//just need to be called to send messages
		
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
	
	/**
	 *	<h1><i>sendReady</i></h1>
	 * <p>tranmitt Ready to the server.</p>
	 */
	protected void sendReady() {
		JSONObject json = new JSONObject();
		JSONObject data = new JSONObject();
		json.put("type", MsgType.READY.toString());
		data.put("ready", isReady);
		json.put("data", data);
		player.out(json.toString());
		LogController.log(Log.DEBUG, "TX:  SendReady: " + data.toString());
	}
	
	/**
	 *	<h1><i>sendMove</i></h1>
	 * <p>Send The current move.</p>
	 */
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
	 *	<h1><i>sendMessage</i></h1>
	 * <p>Send The current move.</p>
	 */
	protected void sendMessage(String messageToSend) {
		JSONObject json = new JSONObject();
		JSONObject data = new JSONObject();
		json.put("type", MsgType.MESSAGE.toString());
		json.put("message", messageToSend);
		data.put("broadcast", true);
		json.put("data", data);
		player.out(json.toString());
		LogController.log(Log.DEBUG, "TX:  SendMessage: " + messageToSend);
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
			gameWindow.setTitle("Human-Dont-Get-Mad: " + data.getString("color"));
		}
		else if (json.get("type").equals(MsgType.UPDATE.toString())) {
			updateGameScreen(data);
		}
		else if (json.get("type").equals(MsgType.TURN.toString())) {
			updateTurns(data);
		}
		else if (json.get("type").equals(MsgType.PLAYERDISCONNECTED.toString())) {
			// player disconnected from game
		}
		else if (json.get("type").equals(MsgType.MESSAGE.toString())) {
			displayMessage(json, data);
		}
		else if (json.get("type").equals(MsgType.ERROR.toString())) {
			LogController.log(Log.ERROR, "Error from server: " + data.get("error"));
		}
		else {
			LogController.log(Log.DEBUG, "Could not decode data: " + json);
		}
	}
	
	/**
	 *	<h1><i>updatGameReadyScreen</i></h1>
	 * <p>in short, it will reprint manuely the complete Gameboard with each update, this is to prevent mistakes.</p>
	 */
	public void updateGameScreen(JSONObject data) {
		
		for (JLabel JL : gameWindow.playerNames)
			JL.setBackground(colors.background);
		for (JLabel JL : gameWindow.playerStats)
			JL.setBackground(colors.background);
		
		if (data.has("currentPlayer")) {
			
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
		gameWindow.dice.setBackground(Color.white);
		
		for (int i = 0; i < players.length(); i++) {
			
			JSONObject player = new JSONObject(players.get(i).toString());
			diceSelection(player.getInt("dice"));
			
			gameWindow.playerStats[i].setText("P" + (i + 1) + ": " + player.getString("color"));
			gameWindow.playerNames[i].setText(player.getString("name"));
		}
		
		if(data.getString("state").equals("waitingForPlayers")) {
			
		//	gameWindow.dice.setBackground(Color.white);
			
			for (int i = 0; i < players.length(); i++) {
				JSONObject player = new JSONObject(players.get(i).toString());
				
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
				
				Color winner = new Color(0, 0, 0);
				
				if(data.get("winner").equals("red"))
					winner = colors.cRedPlayer;
				else if(data.get("winner").equals("yellow"))
					winner = colors.cYellowPlayer;
				else if(data.get("winner").equals("green"))
					winner = colors.cGreenPlayer;
				else if(data.get("winner").equals("blue"))
					winner = colors.cBluePlayer;
					
				//Win Animation
				while (true) {
					try {
						TimeUnit.MILLISECONDS.sleep(750);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					
					for(JButton[] Houses : gameWindow.houses ) 
						for (JButton subHouses : Houses)
							subHouses.setBackground(winner);
									
					for (JButton[] Bases : gameWindow.bases)
						for (JButton subBases : Bases)
							subBases.setBackground(winner);
						
					for (JButton field : gameWindow.buttons)
						field.setBackground(winner);
					
					try {
						TimeUnit.MILLISECONDS.sleep(750);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					
					for(JButton[] Houses : gameWindow.houses ) 
						for (JButton subHouses : Houses)
							subHouses.setBackground(Color.white);
									
					for (JButton[] Bases : gameWindow.bases)
						for (JButton subBases : Bases)
							subBases.setBackground(Color.white);
						
					for (JButton field : gameWindow.buttons)
						field.setBackground(Color.white);
				}
			}
		}
	
	/**
	 *	<h1><i>updateTurn</i></h1>
	 * <p>will ask the player viualy with turn he wants to make.</p>
	 */
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
							gameWindow.redBases[oldPosition.getInt("index")].setBackground(Color.DARK_GRAY);
							gameWindow.redBases[oldPosition.getInt("index")].addActionListener(e -> {
								
								sendMove(k);
								removeAL();
								 
							gameWindow.redBases[oldPosition.getInt("index")].setBackground(Color.LIGHT_GRAY);
							});;
						}
						else if (oldPosition.get("type").equals("field")) {
							gameWindow.buttons[oldPosition.getInt("index")].setBackground(Color.DARK_GRAY);
							gameWindow.buttons[oldPosition.getInt("index")].addActionListener(e -> {
								
								sendMove(k);
								removeAL();
								
							});;	
						}
						else if (oldPosition.get("type").equals("home")) {
							gameWindow.redHouses[oldPosition.getInt("index")].setBackground(Color.DARK_GRAY);
							gameWindow.redHouses[oldPosition.getInt("index")].addActionListener(e -> {
								
								sendMove(k);
								removeAL();
								 
							});;	
						}
						break;
					case "Human-Dont-Get-Mad: green": 

						if (oldPosition.get("type").equals("start")) {
							gameWindow.greenBases[oldPosition.getInt("index")].setBackground(Color.black);
							gameWindow.greenBases[oldPosition.getInt("index")].addActionListener(e -> {
								
								sendMove(k);
								removeAL();
							});;
						}
						else if (oldPosition.get("type").equals("field")) {
							gameWindow.buttons[oldPosition.getInt("index")].setBackground(Color.black);
							gameWindow.buttons[oldPosition.getInt("index")].addActionListener(e -> {
								
								sendMove(k);
								removeAL();
							});;	
						}
						else if (oldPosition.get("type").equals("home")) {
							gameWindow.greenHouses[oldPosition.getInt("index")].setBackground(Color.black);
							gameWindow.greenHouses[oldPosition.getInt("index")].addActionListener(e -> {
								
								sendMove(k);
								removeAL();
							});;	
						}
						break;
						
					case "Human-Dont-Get-Mad: blue": 

						if (oldPosition.get("type").equals("start")) {
							gameWindow.blueBases[oldPosition.getInt("index")].setBackground(Color.black);
							gameWindow.blueBases[oldPosition.getInt("index")].addActionListener(e -> {
								
								sendMove(k);
								removeAL();
							});;
						}
						else if (oldPosition.get("type").equals("field")) {
							gameWindow.buttons[oldPosition.getInt("index")].setBackground(Color.black);
							gameWindow.buttons[oldPosition.getInt("index")].addActionListener(e -> {
								
								sendMove(k);
								removeAL();
							});;	
						}
						else if (oldPosition.get("type").equals("home")) {
							gameWindow.blueHouses[oldPosition.getInt("index")].setBackground(Color.black);
							gameWindow.blueHouses[oldPosition.getInt("index")].addActionListener(e -> {
								
								sendMove(k);
								removeAL();
							});;	
						}
						break;
						
					case "Human-Dont-Get-Mad: yellow": 

						if (oldPosition.get("type").equals("start")) {
							gameWindow.yellowBases[oldPosition.getInt("index")].setBackground(Color.black);
							gameWindow.yellowBases[oldPosition.getInt("index")].addActionListener(e -> {
								
								sendMove(k);
								removeAL();
							});;
							
						}
						else if (oldPosition.get("type").equals("field")) {
							gameWindow.buttons[oldPosition.getInt("index")].setBackground(Color.black);
							gameWindow.buttons[oldPosition.getInt("index")].addActionListener(e -> {
								
								sendMove(k);
								removeAL();
							});;	
						}
						else if (oldPosition.get("type").equals("home")) {
							gameWindow.yellowHouses[oldPosition.getInt("index")].setBackground(Color.black);
							gameWindow.yellowHouses[oldPosition.getInt("index")].addActionListener(e -> {
								
								sendMove(k);
								removeAL();
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
	
	/**
	 *	<h1><i>removeAL</i></h1>
	 * <p>removes all Actionlistenser to prevent errors with stupid useres.</p>
	 */
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

		for (ActionListener al : gameWindow.dice.getActionListeners())
			gameWindow.dice.removeActionListener(al);
	}
	
	/**
	 *	<h1><i>displaymessage</i></h1>
	 * <p>will controll the message screen.</p>
	 */
	public void displayMessage(JSONObject json, JSONObject data) {
		
		showtext = showtext + data.get("sender").toString() + ": " + json.get("message").toString() + "\n";
		messager.incommingText.setText(showtext);
		LogController.log(Log.DEBUG, "RX Message: " + showtext);
	}
	
	public void ReadysendText() {
		
		messager.send.addActionListener(e -> {
			if (! messager.outgoingText.getText().equals("")) {
			sendMessage(messager.outgoingText.getText());
	
			showtext = showtext + "you" + ": " + messager.outgoingText.getText() + "\n";
			messager.outgoingText.setText("");
			messager.incommingText.setText(showtext);
			}
		});
	}
}
