package client;

import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Observable;

import javax.swing.*;




/**
* is the main game window
* <p>
* after a successful login this window gets displayed
*
* @param  
*/
public class GameWindow extends JFrame {
	
	static UISettings uis = new UISettings();	
	static GetScreenData gcd = new GetScreenData();
	static Main Main = new Main();
	static String temp = "";
	
//Panels
	private JPanel mainWindowReplacer = new JPanel();
	private JPanel mainGameArea= new JPanel();
	private JPanel statsArea= new JPanel();
	private JPanel statsAreaText= new JPanel();
	private JPanel statsAreaDice= new JPanel();
	private JPanel diceArea= new JPanel();
	
	
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
	JLabel[] playerStats = {playerOneStats, playerOneName, playerTwoStats, playerTwoName, 
							playerThreeStats, playerThreeName, playerFourStats, playerFourName};
	
//Buttons
	JButton dice = new JButton();
	//generate 40 Buttons for the gameboard
	static JButton[] buttons = new JButton[40];
	
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
	JButton[] bases =  {redBaseOne, redBaseTwo, redBaseThree, redBaseFour,
						greenBaseOne, greenBaseTwo, greenBaseThree, greenBaseFour,
						blueBaseOne, blueBaseTwo, blueBaseThree, blueBaseFour,
						yellowBaseOne, yellowBaseTwo, yellowBaseThree, yellowBaseFour};
	
	
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
	
	JButton[] houses = {redHouseOne, redHouseTwo, redHouseThree, redHouseFour,
						greenHouseOne, greenHouseTwo, greenHouseThree, greenHouseFour,
						blueHouseOne, blueHouseTwo, blueHouseThree, blueHouseFour,
						yellowHouseOne, yellowHouseTwo, yellowHouseThree, yellowHouseFour};
	
	
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
	
	//Gets the current window size if needed
	public void getCurrentWindowSize() {
		widthCurrentWindow = this.getBounds().width;
		heightCurrentWindow = this.getBounds().height;
	}
		
		
	public void GameWindow() {

		Dimension windowSize = new Dimension();
		windowSize.setSize(gcd.width/2, gcd.height/2);
		
		this.setLocation(0, 0);
		this.setSize(windowSize);	//Set de default size to half the screensize for better UX
		this.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		this.setResizable(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Human-Dont-Get-Mad");
				
//WindowSize Listener			
	//This block is responsive for the responsive UI
	//It will change the layout depending on the aspect ratio
	this.addComponentListener(new ComponentAdapter() {
	    public void componentResized(ComponentEvent componentEvent) {
	    	getCurrentWindowSize();
	    	
	        System.out.println("WIDTH: " + widthCurrentWindow);		
	        System.out.println("HEIGHT: " + heightCurrentWindow);	
	        
	        //Logic to turn the layout as resources friendly as possible
	        if(widthCurrentWindow >= heightCurrentWindow) {
	        	isLandScape = true;
	        	
	        	if (isLandScape && gotTurned) {
	        		gotTurned = false;
	        		alignmentConstruct(true);
	        		System.out.println("Ausrichtung zu Land geaendert");
	        	}
	        	System.out.println("Ausrichtung ist Land");
	        }
	        else {
	        	isLandScape = false;
	        	
	        	if(!isLandScape && !gotTurned) {
	        		gotTurned = true;
	        		alignmentConstruct(false);
	        		System.out.println("Ausrichtung zu Port geaendert");
	        	}
	        	System.out.println("Ausrichtung ist Port");
	        }
	        }
	    });
	
		statsArea.setLayout(new GridLayout(1, 2, 5, 5));
		
		statsArea.add(statsAreaText);
		statsArea.add(statsAreaDice);
		
		statsArea.setBackground(background);
		statsAreaText.setBackground(background);
		statsAreaDice.setBackground(background);
		
		statsArea.setLayout(new GridLayout(1, 2, 5, 5));
		statsAreaText.setLayout(new GridLayout(4, 2, 5, 5));
		
		
		
		//Sets the Text for all Labels
		playerOneStats.setText("P1: " + "Red");			//for now, the color is a placehold until there is color data
		playerOneName.setText("Tim");					//for now, the name is a placeholder too
		playerTwoStats.setText("P2: " + "Green");		//for now, the color is a placehold until there is color data
		playerTwoName.setText("Konrad");				//for now, the name is a placeholder too
		playerThreeStats.setText("P3: " + "Blue");		//for now, the color is a placehold until there is color data
		playerThreeName.setText("Paul");				//for now, the name is a placeholder too
		playerFourStats.setText("P4: " + "Yellow");		//for now, the color is a placehold until there is color data
		playerFourName.setText("Bot Yellow");			//for now, the name is a placeholder too
		
		for(JLabel playerStat : playerStats ) {
			playerStat.setBackground(background);
			playerStat.setOpaque(true);
			statsAreaText.add(playerStat);
		}
		
		diceArea.add(dice);
		dice.addActionListener(e -> {
			buttons[0].setBackground(Color.pink);
		});
		
		
		for(int i = 0; i < buttons.length; i++) {
			buttons[i] = new JButton();
		}
		gameboard();
	}
	
	
		//is Responsive for the Layout
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
			gbcls.weightx = 2;
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
			gbcp.weighty = 2;
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
	
	public void gameboard() {
		
		JPanel redBase = new JPanel();
		redBase.setBackground(Color.RED);
		redBase.setLayout(new GridLayout(2,2,15,15));
		
		JPanel greenBase = new JPanel();
		greenBase.setBackground(Color.GREEN);
		greenBase.setLayout(new GridLayout(2,2,15,15));
		
		JPanel blueBase = new JPanel();
		blueBase.setBackground(Color.BLUE);
		blueBase.setLayout(new GridLayout(2,2,15,15));
		
		JPanel yellowBase = new JPanel();
		yellowBase.setBackground(Color.YELLOW);
		yellowBase.setLayout(new GridLayout(2,2,15,15));
		

		JPanel redHouse = new JPanel();
		redHouse.setBackground(Color.RED);
		redHouse.setLayout(new GridLayout(1,4,30,30));
		
		JPanel greenHouse = new JPanel();
		greenHouse.setBackground(Color.GREEN);
		greenHouse.setLayout(new GridLayout(4,1,30,30));
		
		JPanel blueHouse = new JPanel();
		blueHouse.setBackground(Color.BLUE);
		blueHouse.setLayout(new GridLayout(1,4,30,30));
		
		JPanel yellowHouse = new JPanel();
		yellowHouse.setBackground(Color.YELLOW);
		yellowHouse.setLayout(new GridLayout(4,1,30,30));
		
		
		for(JButton Bases : bases ) 
			Bases.setBackground(Color.LIGHT_GRAY);
		
		for(JButton Houses : houses ) 
			Houses.setBackground(Color.LIGHT_GRAY);
		
		for(JButton btn : buttons ) 
			btn.setBackground(Color.WHITE);
		
		
		GridBagConstraints gbcgb = new GridBagConstraints();
		mainGameArea.setLayout(new GridBagLayout());
		gbcgb.fill = GridBagConstraints.BOTH;
		gbcgb.insets = new Insets(2, 2, 15, 15);
		
		gbcgb.weightx = 1;
		gbcgb.weighty = 1;
		
		gbcgb.gridx = 0;
		gbcgb.gridy = 0;
		gbcgb.gridheight = 2;
		gbcgb.gridwidth = 2;
		
		mainGameArea.add(redBase, gbcgb);
		
		gbcgb.gridx = 9;
		gbcgb.gridy = 0;
		mainGameArea.add(greenBase, gbcgb);
		
		
		gbcgb.gridx = 0;
		gbcgb.gridy = 9;
		mainGameArea.add(blueBase, gbcgb);
		
		gbcgb.gridx = 9;
		gbcgb.gridy = 9;
		mainGameArea.add(yellowBase, gbcgb);
		
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
