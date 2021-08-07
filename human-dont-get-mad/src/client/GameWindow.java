package client;

import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import game.Log;
import game.LogController;

import client.ClientController;

import javax.swing.*;


/**
* <h1>GameWindow</h1>
* <p>This is the Main Game Window, it will show things</p>
* <b>Note:</b> The ClientController should only be instaciated with every client socket.
*
* @author  Tim Menzel
* @version 1.0
* @since   2021-08-2
*/
public class GameWindow extends JFrame {
	
	private ClientController ClientController;
	
	public void giveController(ClientController ClientController) {
		this.ClientController = ClientController;
	}
	
	UISettings uis = new UISettings();	
	GetScreenData gcd = new GetScreenData();
	
	String temp = "";
	boolean isPressed = false;
	
//Panels
	private JPanel mainWindowReplacer = new JPanel();
	private JPanel mainGameArea = new JPanel();
	private JPanel statsArea = new JPanel();
	private JPanel statsAreaNames = new JPanel();
	private JPanel statsAreaStats = new JPanel();
	private JPanel diceArea = new JPanel();
	
	private JPanel redBase = new JPanel();
	private JPanel greenBase = new JPanel();
	private JPanel blueBase = new JPanel();
	private JPanel yellowBase = new JPanel();
	private JPanel redHouse = new JPanel();
	private JPanel greenHouse = new JPanel();
	private JPanel blueHouse = new JPanel();
	private JPanel yellowHouse = new JPanel();
	
//Labels
	private JLabel playerOneStats= new JLabel();
	private JLabel playerTwoStats= new JLabel();
	private JLabel playerThreeStats= new JLabel();
	private JLabel playerFourStats= new JLabel();
	private JLabel playerOneName= new JLabel();
	private JLabel playerTwoName= new JLabel();
	private JLabel playerThreeName= new JLabel();
	private JLabel playerFourName= new JLabel();
	
//String of all labels 
	JLabel[] playerStats = {playerOneStats, playerTwoStats, playerThreeStats, playerFourStats};
	JLabel[] playerNames = {playerOneName, playerTwoName, playerThreeName, playerFourName};
	
//Buttons
	JButton dice = new JButton();
	//generate 40 Buttons for the gameboard
	JButton[] buttons = new JButton[40];
	
	//these buttons are generated with a specific name for better readability of the code
	JButton redBaseOne = new JButton();
	JButton redBaseTwo = new JButton();
	JButton redBaseThree = new JButton();
	JButton redBaseFour = new JButton();
	
	JButton greenBaseOne = new JButton();
	JButton greenBaseTwo = new JButton();
	JButton greenBaseThree = new JButton();
	JButton greenBaseFour = new JButton();
	
	JButton blueBaseOne = new JButton();
	JButton blueBaseTwo = new JButton();
	JButton blueBaseThree = new JButton();
	JButton blueBaseFour = new JButton();
	
	JButton yellowBaseOne = new JButton();
	JButton yellowBaseTwo = new JButton();
	JButton yellowBaseThree = new JButton();
	JButton yellowBaseFour = new JButton();
	
	JButton[] redBases =  {redBaseOne, redBaseTwo, redBaseThree, redBaseFour};
	JButton[] greenBases =  {greenBaseOne, greenBaseTwo, greenBaseThree, greenBaseFour};
	JButton[] blueBases =  {blueBaseOne, blueBaseTwo, blueBaseThree, blueBaseFour};
	JButton[] yellowBases =  {yellowBaseOne, yellowBaseTwo, yellowBaseThree, yellowBaseFour};
	
	JButton[][] bases = {redBases, greenBases, blueBases, yellowBases};
	
	
	JButton redHouseOne = new JButton();
	JButton redHouseTwo = new JButton();
	JButton redHouseThree = new JButton();
	JButton redHouseFour = new JButton();
	
	JButton greenHouseOne = new JButton();
	JButton greenHouseTwo = new JButton();
	JButton greenHouseThree = new JButton();
	JButton greenHouseFour = new JButton();
	
	JButton blueHouseOne = new JButton();
	JButton blueHouseTwo = new JButton();
	JButton blueHouseThree = new JButton();
	JButton blueHouseFour = new JButton();
	
	JButton yellowHouseOne = new JButton();
	JButton yellowHouseTwo = new JButton();
	JButton yellowHouseThree = new JButton();
	JButton yellowHouseFour = new JButton();
	
	JButton[] redHouses = {redHouseOne, redHouseTwo, redHouseThree, redHouseFour};
	JButton[] greenHouses = {greenHouseOne, greenHouseTwo, greenHouseThree, greenHouseFour};
	JButton[] blueHouses = {blueHouseOne, blueHouseTwo, blueHouseThree, blueHouseFour};
	JButton[] yellowHouses = {yellowHouseOne, yellowHouseTwo, yellowHouseThree, yellowHouseFour};
	
	JButton[][] houses = {redHouses, greenHouses, blueHouses, yellowHouses};
	
//Variables
	private	int heightCurrentWindow = 0;
	private int widthCurrentWindow = 0;
	private boolean isLandScape = false;
	private boolean gotTurned = true;
	
//Colors	
	Color background = uis.background;
	Color interactionFields = uis.interactionFields;
					
//Fonts
	Font generalFont = uis.generalFont;
	Font labelFont = uis.labelFont;
	
//Constructor
	public GameWindow() {
		GameWindow();		
	}
	
	
	/**
	 *	<h1><i>getCurrentWindowSize</i></h1>
	 * <p>This Method will set the variables for the width and hight</p>
	 */
	public void getCurrentWindowSize() {
		widthCurrentWindow = this.getBounds().width;
		heightCurrentWindow = this.getBounds().height;
		LogController.log(Log.TRACE, "Current Screen Width: " + widthCurrentWindow);
		LogController.log(Log.TRACE, "Current Screeen Hight: " + heightCurrentWindow);
		}
	
	
	
	/**
	 *	<h1><i>GameWinodw</i></h1>
	 * <p>This function will proceed and display the current window</p>
	 */
	public void GameWindow() {

		Dimension windowSize = new Dimension();
		windowSize.setSize(gcd.width/2, gcd.height/2);
		
		this.setLocation(0, 0);
		this.setSize(windowSize);	//Set de default size to half the screensize for better UX
//		this.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		this.setResizable(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Human-Dont-Get-Mad");
				
	//WindowSize Listener			
		//This block is responsive for the responsive UI
		//It will change the layout depending on the aspect ratio
	this.addComponentListener(new ComponentAdapter() {
	    public void componentResized(ComponentEvent componentEvent) {
	    	getCurrentWindowSize();
	    	LogController.log(Log.TRACE, "Current Window Width: " + widthCurrentWindow);
	    	LogController.log(Log.TRACE, "Current Window Hight: " + heightCurrentWindow);
	        
	        //Logic to turn the layout as resources friendly as possible
	        if(widthCurrentWindow >= heightCurrentWindow) {
	        	isLandScape = true;
	        	
	        	if (isLandScape && gotTurned) {
	        		gotTurned = false;
	        		alignmentConstruct(true);
	        		LogController.log(Log.DEBUG, "The Window Layout is set to Landscape");
	        	}
	        }
	        else {
	        	isLandScape = false;
	        	
	        	if(!isLandScape && !gotTurned) { 
	        		gotTurned = true;
	        		alignmentConstruct(false);
	        		LogController.log(Log.DEBUG, "The Window Layout is set to Portrait");
	        	}
	        }
	        }
	    });
	
		statsArea.setLayout(new GridLayout(1, 2, 5, 5));
		
		statsArea.add(statsAreaStats);
		statsArea.add(statsAreaNames);
		
		statsArea.setBackground(background);
		statsAreaStats.setBackground(background);
		statsAreaNames.setBackground(background);
		
		statsArea.setLayout(new GridLayout(1, 2, 5, 5));
		statsAreaStats.setLayout(new GridLayout(4, 1, 5, 5));
		statsAreaNames.setLayout(new GridLayout(4, 1, 5, 5));
		
		
		for(JLabel playerStat : playerStats ) {
			playerStat.setBackground(background);
			playerStat.setOpaque(true);
			statsAreaStats.add(playerStat);
		}
		
		for(JLabel playerName : playerNames ) {
			playerName.setBackground(background);
			playerName.setOpaque(true);
			statsAreaNames.add(playerName);
		}
		
		
		
		dice.setLocation(5, 5);
		dice.setSize(241, 241);
		dice.setText("Are Your Ready\n "
				+ "For Fun!");
		diceArea.add(dice);
		diceArea.setLayout(null);
		dice.setText("PRESS FOR READY");
		dice.addActionListener(e -> {
			if (ClientController.gameIsStarted) {
				//ClientController.rollDice();
			}
			else {
				if (isPressed) {
	
					ClientController.isReady = false;
					ClientController.sendReady();
					dice.setText("YOU ARE READY");
					isPressed = false;
				}
				else {
					
					ClientController.isReady = true;
					ClientController.sendReady();
					
					isPressed = true;
				}
			}
		});
		
		
		for(int i = 0; i < buttons.length; i++)
			buttons[i] = new JButton();
			
		gameboard();
	}
	
	
	/**
	 *	<h1><i>alignmentConstruct</i></h1>
	 * <p>This function will build the window depending on its current state 
	 * it will set it in Landscape or Portrait mode</p>
	 * @param SetLandScape is the result from the calculation of the window size 
	 */
	public void alignmentConstruct(boolean SetLandScape) {
		
		GridBagConstraints gbcls = new GridBagConstraints();
		mainWindowReplacer.setLayout(new GridBagLayout());
		gbcls.fill = GridBagConstraints.BOTH;
		gbcls.insets = new Insets(5, 5, 5, 5);
		
		GridBagConstraints gbcp = new GridBagConstraints();
		mainWindowReplacer.setLayout(new GridBagLayout());
		gbcp.fill = GridBagConstraints.BOTH;
		gbcp.insets = new Insets(2, 2, 2, 2);
		
		if (SetLandScape) {
			
			gbcls.gridx = 0;
			gbcls.gridy = 0;
			gbcls.gridheight = 2;
			gbcls.weightx = 1;
			mainWindowReplacer.add(mainGameArea, gbcls);
			mainGameArea.setBackground(background);
			gbcls.gridheight = 1;
			gbcls.weightx = 1;
			gbcls.weighty = 1;
			
			gbcls.gridx = 1;
			gbcls.gridy = 0;
			mainWindowReplacer.add(statsArea, gbcls);
			statsArea.setBackground(background);

			gbcls.gridx = 1;
			gbcls.gridy = 1;
			mainWindowReplacer.add(diceArea, gbcls);
			diceArea.setBackground(background);
			
		}
		else {
			
			gbcp.gridx = 0;
			gbcp.gridy = 0;
			gbcp.gridwidth = 2;
			gbcp.weighty = 1;
			mainWindowReplacer.add(mainGameArea, gbcp);
			mainGameArea.setBackground(background);
			gbcp.gridwidth = 1;
			gbcp.weightx = 1;
			gbcp.weighty = 1;
			
			gbcp.gridx = 0;
			gbcp.gridy = 1;
			mainWindowReplacer.add(statsArea, gbcp);
			statsArea.setBackground(background);

			gbcp.gridx = 1;
			gbcp.gridy = 1;
			mainWindowReplacer.add(diceArea, gbcp);
			diceArea.setBackground(background);
		}
		
		this.add(mainWindowReplacer);
	}
	
	Dimension dim = mainWindowReplacer.getSize();
	int setWidthInsets = dim.width;
	int setHeigthInsets = dim.height;
	
	GridBagConstraints gbcgb = new GridBagConstraints();

	
	/**
	 *	<h1><i>gameBoard</i></h1>
	 * <p>Will build the gameboard</p>
	 */
	public void gameboard() {		
		mainGameArea.setLayout(new GridBagLayout());
		
		gbcgb.fill = GridBagConstraints.BOTH;
		
		redBase.setBackground(background);
		greenBase.setBackground(background);
		blueBase.setBackground(background);
		yellowBase.setBackground(background);
		redHouse.setBackground(background);
		greenHouse.setBackground(background);
		blueHouse.setBackground(background);
		yellowHouse.setBackground(background);
		
		
		for(JButton[] Houses : houses ) 
			for (JButton subHouses : Houses)
				subHouses.setBackground(Color.LIGHT_GRAY);
		
		for (JButton[] Bases : bases)
			for (JButton subBases : Bases)
				subBases.setBackground(Color.LIGHT_GRAY);
		
		for(JButton btn : buttons ) 
			btn.setBackground(Color.WHITE);
		
		
		//need to be called first to construct the window
		
		
    	setWidthInsets = gcd.width/80;
		setHeigthInsets = gcd.height/80;
		
		rePrintGameBoard();
		mainGameArea.addComponentListener(new ComponentAdapter() {
		    public void componentResized(ComponentEvent componentEvent) {
			    	
			   	dim = mainWindowReplacer.getSize();
	
			   	setWidthInsets = dim.width/80;
				setHeigthInsets = dim.height/80;
					
			   	rePrintGameBoard();
		    }
	    });
	}
	
	
	public void rePrintGameBoard() {
    	//FOR SOME FCK REASON I CANT TAK THE GAMEWINDOW
    	//dim = mainWindowReplacer.getSize();
		
    	
		LogController.log(Log.TRACE, "Insetes x: " + setWidthInsets);
		LogController.log(Log.TRACE, "Insetes y: " + setHeigthInsets);
		
		redBase.setLayout(new GridLayout(2,2,setWidthInsets, setHeigthInsets));
		greenBase.setLayout(new GridLayout(2,2,setWidthInsets, setHeigthInsets));
		blueBase.setLayout(new GridLayout(2,2,setWidthInsets, setHeigthInsets));
		yellowBase.setLayout(new GridLayout(2,2,setWidthInsets, setHeigthInsets));
		redHouse.setLayout(new GridLayout(1,4,setWidthInsets, setHeigthInsets));
		greenHouse.setLayout(new GridLayout(4,1, setWidthInsets, setHeigthInsets));
		blueHouse.setLayout(new GridLayout(1,4,setWidthInsets, setHeigthInsets));
		yellowHouse.setLayout(new GridLayout(4,1, setWidthInsets, setHeigthInsets));
		gbcgb.insets = new Insets(setHeigthInsets,setWidthInsets, 0,0);
		    
		gbcgb.weightx = 1;
		gbcgb.weighty = 1;
		
		gbcgb.gridx = 0;
		gbcgb.gridy = 0;
		gbcgb.gridheight = 2;
		gbcgb.gridwidth = 2;
		
		mainGameArea.add(redBase, gbcgb);
		
		gbcgb.gridx = 9;
		gbcgb.gridy = 0;
		mainGameArea.add(blueBase, gbcgb);
		
		gbcgb.gridx = 0;
		gbcgb.gridy = 9;
		mainGameArea.add(yellowBase, gbcgb);
		
		gbcgb.gridx = 9;
		gbcgb.gridy = 9;
		mainGameArea.add(greenBase, gbcgb);
		
		redBase.add(redBaseOne);
		redBase.add(redBaseTwo);
		redBase.add(redBaseThree);
		redBase.add(redBaseFour);
		
		greenBase.add(greenBaseOne);
		greenBase.add(greenBaseTwo);
		greenBase.add(greenBaseThree);
		greenBase.add(greenBaseFour);
		
		blueBase.add(blueBaseOne);
		blueBase.add(blueBaseTwo);
		blueBase.add(blueBaseThree);
		blueBase.add(blueBaseFour);
		
		yellowBase.add(yellowBaseOne);
		yellowBase.add(yellowBaseTwo);
		yellowBase.add(yellowBaseThree);
		yellowBase.add(yellowBaseFour);
			
		gbcgb.gridx = 1;
		gbcgb.gridy = 5;
		gbcgb.gridheight = 1;
		gbcgb.gridwidth = 4;
		mainGameArea.add(redHouse, gbcgb);
		
		gbcgb.gridx = 6;
		gbcgb.gridy = 5;
		mainGameArea.add(blueHouse, gbcgb);
		
		gbcgb.gridx = 5;
		gbcgb.gridy = 1;
		gbcgb.gridheight = 4;
		gbcgb.gridwidth = 1;
		mainGameArea.add(greenHouse, gbcgb);
		
		gbcgb.gridx = 5;
		gbcgb.gridy = 6;
		mainGameArea.add(yellowHouse, gbcgb);
		
		redHouse.add(redHouseOne);
		redHouse.add(redHouseTwo);
		redHouse.add(redHouseThree);
		redHouse.add(redHouseFour);
		
		greenHouse.add(greenHouseOne);
		greenHouse.add(greenHouseTwo);
		greenHouse.add(greenHouseThree);
		greenHouse.add(greenHouseFour);
		
		blueHouse.add(blueHouseOne);
		blueHouse.add(blueHouseTwo);
		blueHouse.add(blueHouseThree);
		blueHouse.add(blueHouseFour);
		
		yellowHouse.add(yellowHouseOne);
		yellowHouse.add(yellowHouseTwo);
		yellowHouse.add(yellowHouseThree);
		yellowHouse.add(yellowHouseFour);
		
		gbcgb.gridheight = 1;
		gbcgb.gridwidth = 1;
		gbcgb.gridy = 4;
		gbcgb.gridx = 0;
		mainGameArea.add(buttons[0], gbcgb);
		
		gbcgb.gridy = 4;
		gbcgb.gridx = 1;
		mainGameArea.add(buttons[1], gbcgb);
		
		gbcgb.gridy = 4;
		gbcgb.gridx = 2;
		mainGameArea.add(buttons[2], gbcgb);
		
		gbcgb.gridy = 4;
		gbcgb.gridx = 3;
		mainGameArea.add(buttons[3], gbcgb);
		
		gbcgb.gridy = 4;
		gbcgb.gridx = 4;
		mainGameArea.add(buttons[4], gbcgb);
		
		gbcgb.gridy = 3;
		gbcgb.gridx = 4;
		mainGameArea.add(buttons[5], gbcgb);
		
		gbcgb.gridy = 2;
		gbcgb.gridx = 4;
		mainGameArea.add(buttons[6], gbcgb);
		
		gbcgb.gridy = 1;
		gbcgb.gridx = 4;
		mainGameArea.add(buttons[7], gbcgb);
		
		gbcgb.gridy = 0;
		gbcgb.gridx = 4;
		mainGameArea.add(buttons[8], gbcgb);
		
		gbcgb.gridy = 0;
		gbcgb.gridx = 5;
		mainGameArea.add(buttons[9], gbcgb);
		
		gbcgb.gridy = 0;
		gbcgb.gridx = 6;
		mainGameArea.add(buttons[10], gbcgb);
		
		gbcgb.gridy = 1;
		gbcgb.gridx = 6;
		mainGameArea.add(buttons[11], gbcgb);
		
		gbcgb.gridy = 2;
		gbcgb.gridx = 6;
		mainGameArea.add(buttons[12], gbcgb);
		
		gbcgb.gridy = 3;
		gbcgb.gridx = 6;
		mainGameArea.add(buttons[13], gbcgb);
		
		gbcgb.gridy = 4;
		gbcgb.gridx = 6;
		mainGameArea.add(buttons[14], gbcgb);
		
		gbcgb.gridy = 4;
		gbcgb.gridx = 7;
		mainGameArea.add(buttons[15], gbcgb);
		
		gbcgb.gridy = 4;
		gbcgb.gridx = 8;
		mainGameArea.add(buttons[16], gbcgb);
		
		gbcgb.gridy = 4;
		gbcgb.gridx = 9;
		mainGameArea.add(buttons[17], gbcgb);
		
		gbcgb.gridy = 4;
		gbcgb.gridx = 10;
		mainGameArea.add(buttons[18], gbcgb);
		
		gbcgb.gridy = 5;
		gbcgb.gridx = 10;
		mainGameArea.add(buttons[19], gbcgb);
		
		gbcgb.gridy = 6;
		gbcgb.gridx = 10;
		mainGameArea.add(buttons[20], gbcgb);
		
		gbcgb.gridy = 6;
		gbcgb.gridx = 9;
		mainGameArea.add(buttons[21], gbcgb);
		
		gbcgb.gridy = 6;
		gbcgb.gridx = 8;
		mainGameArea.add(buttons[22], gbcgb);
		
		gbcgb.gridy = 6;
		gbcgb.gridx = 7;
		mainGameArea.add(buttons[23], gbcgb);
		
		gbcgb.gridy = 6;
		gbcgb.gridx = 6;
		mainGameArea.add(buttons[24], gbcgb);
		
		gbcgb.gridy = 7;
		gbcgb.gridx = 6;
		mainGameArea.add(buttons[25], gbcgb);
		
		gbcgb.gridy = 8;
		gbcgb.gridx = 6;
		mainGameArea.add(buttons[26], gbcgb);
		
		gbcgb.gridy = 9;
		gbcgb.gridx = 6;
		mainGameArea.add(buttons[27], gbcgb);
		
		gbcgb.gridy = 10;
		gbcgb.gridx = 6;
		mainGameArea.add(buttons[28], gbcgb);
		
		gbcgb.gridy = 10;
		gbcgb.gridx = 5;
		mainGameArea.add(buttons[29], gbcgb);
		
		gbcgb.gridy = 10;
		gbcgb.gridx = 4;
		mainGameArea.add(buttons[30], gbcgb);
		
		gbcgb.gridy = 9;
		gbcgb.gridx = 4;
		mainGameArea.add(buttons[31], gbcgb);
		
		gbcgb.gridy = 8;
		gbcgb.gridx = 4;
		mainGameArea.add(buttons[32], gbcgb);
		
		gbcgb.gridy = 7;
		gbcgb.gridx = 4;
		mainGameArea.add(buttons[33], gbcgb);
		
		gbcgb.gridy = 6;
		gbcgb.gridx = 4;
		mainGameArea.add(buttons[34], gbcgb);
		
		gbcgb.gridy = 6;
		gbcgb.gridx = 3;
		mainGameArea.add(buttons[35], gbcgb);
		
		gbcgb.gridy = 6;
		gbcgb.gridx = 2;
		mainGameArea.add(buttons[36], gbcgb);
		
		gbcgb.gridy = 6;
		gbcgb.gridx = 1;
		mainGameArea.add(buttons[37], gbcgb);
		gbcgb.gridy = 6;
		gbcgb.gridx = 0;
		mainGameArea.add(buttons[38], gbcgb);
		
		gbcgb.gridy = 5;
		gbcgb.gridx = 0;
		mainGameArea.add(buttons[39], gbcgb);
	}
}   
