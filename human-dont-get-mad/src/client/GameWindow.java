package client;

import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.*;


/**
* is the main game window
* <p>
* after a successful login this window gets displayed
*
* @param  
*/
public class GameWindow extends JFrame {

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
	
	JLabel[] playerStats = {playerOneStats, playerOneName, playerTwoStats, playerTwoName, 
			playerThreeStats, playerThreeName, playerFourStats, playerFourName};
	
//Buttons
	JButton dice = new JButton();
	
//Variables
	private	int heightCurrentWindow = 0;
	private int widthCurrentWindow = 0;
	int count = 0; //TEMP Counter for testing coloering
	
	private boolean isLandScape = false;
	private boolean gotTurned = true;

	
//Has to be Public for now -> CHANGE LATER
	//Fetch UISettings object
	UISettings uis = new UISettings();	
			//Colors	
		Color background = uis.background;
		Color interactionFields = uis.interactionFields;
					
			//Fonts
		Font generalFont = uis.generalFont;
		Font labelFont = uis.labelFont;
			//FOR NOW these are the Colors/Fonts from the UI
	
	
	//Constructor
	public GameWindow() {
		GameWindow();	
	}
	
	public void GameWindow() {
		
		//Create Object
		GetScreenData gcd = new GetScreenData();
		Main Main = new Main();
		
		Dimension windowSize = new Dimension();

		windowSize.setSize(gcd.width, gcd.height);
		
		//Set the Windows Parameters
		this.setSize(windowSize);
		this.setResizable(true);
		this.setLocation(0, 0);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Human-Dont-Get-Mad");
		
				
//Listener
//*****************************************************************************************


//WindowSize Listener			
	//This block is responsive for the responsive UI		
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
	//*****************************************************************************************
	
	//FOR NOW THERES NO CONTENT	
				
	//*****************************************************************************************

	//FOR NOW THERES NO CONTENT	
	statsArea.setLayout(new GridLayout(1, 2, 5, 5));
	
	statsArea.add(statsAreaText);
	statsArea.add(statsAreaDice);
	
	statsArea.setBackground(background);
	statsAreaText.setBackground(background);
	statsAreaDice.setBackground(background);
	
	statsArea.setLayout(new GridLayout(1, 2, 5, 5));
	statsAreaText.setLayout(new GridLayout(4, 2, 5, 5));
	
	GameControl gc = new GameControl();
	
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
		System.out.println("count is " + count);
		if(count == 0) {
			playerStats[0].setBackground(Color.RED);
			playerStats[1].setBackground(Color.RED);
			count++;
			playerOneStats.setText(gc.setplayeronestats());
		}
		else if(count == 1) {
			playerStats[0].setBackground(background);
			playerStats[1].setBackground(background);
			playerStats[2].setBackground(Color.GREEN);
			playerStats[3].setBackground(Color.GREEN);
			count++;
			playerStats[0].setText("P1: " + "Red");
		}
		else if(count == 2) {
			playerStats[2].setBackground(background);
			playerStats[3].setBackground(background);
			playerStats[4].setBackground(Color.BLUE);
			playerStats[5].setBackground(Color.BLUE);
			count++;
		}
		else if(count == 3) {
			playerStats[4].setBackground(background);
			playerStats[5].setBackground(background);
			playerStats[6].setBackground(Color.YELLOW);
			playerStats[7].setBackground(Color.YELLOW);
			count++;
		}
		else if (count == 4) {
			playerStats[6].setBackground(background);
			playerStats[7].setBackground(background);
			count = 0;
		}
	});
	
	
	
	}
	
	//Gets the current window size if needed
	public void getCurrentWindowSize() {
		widthCurrentWindow = this.getBounds().width;
		heightCurrentWindow = this.getBounds().height;
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
}