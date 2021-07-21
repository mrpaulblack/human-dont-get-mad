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
public class GameWindow extends JFrame{

	private JPanel mainWindowReplacer = new JPanel();
	private JPanel mainGameArea= new JPanel();
	private JPanel statsArea= new JPanel();
	private JPanel diceArea= new JPanel();
	
	private	int heightCurrentWindow = 0;
	private int widthCurrentWindow = 0;
	
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
		
		
//*****************************************************************************************
		
//FOR NOW THERES NO CONTENT	
			
//*****************************************************************************************

//FOR NOW THERES NO CONTENT			
		
		
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