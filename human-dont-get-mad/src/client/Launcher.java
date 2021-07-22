package client;

import java.awt.*;
import java.awt.event.ComponentEvent;
import java.util.concurrent.TimeUnit;

import javax.swing.*;


public class Launcher extends JFrame{
	
	//Constructor
	public Launcher() {
		Launcher();	
	}
	
	public String ip = null;		// Transmit the Server Address
	String port = null;			// Transmit the Port
	String uname = null;		// Transmit the Username
	Integer favColor = -1;
	
	/**
	* Open the Launcher
	* It will be always centered on the screen center
	* It asks the user for the Server Address, Port, Username and the prefferd Color
	* <p>
	* This window is shows to establish a connection with a server
	* if the connection failed it should reopen
	*
	* @param  
	* It don't return anything, it will set the variables of another function
	* @return	ip			(String)	Sets the ip from the "Serveraddress" field
	* @return	port		(String)	Sets the port from the "port" field 
	* @return	username	(String)	Sets the Username from the "username" field 
	* @return	favColor	(String)	Sets the favcoler from the getcolor combobox
	*/
	public void Launcher() {

		Dimension upperFieldSize;
		Main Main = new Main();
		
		//Create Object
		GetScreenData gcd = new GetScreenData();
		
		Dimension size = new Dimension();
		Dimension findMidPoint = new Dimension();

		//Set Screen Size to this //So Parts are Hardcoded -> Changes may lead to errors
		size.setSize(400, 450);
		
		//Put the Window MidPoint in the Screen Center
		findMidPoint.setSize((gcd.width/2) - (size.width/2), (gcd.height/2) - (size.height/2));
		
		//Set the Windows Parrameters
		this.setSize(size);
		this.setResizable(false);
		this.setLocation(findMidPoint.width, findMidPoint.height);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Human-Dont-Get-Mad");
		
//*****************************************************************************************
		
		//Fetch UISettings object
		UISettings uis = new UISettings();
		
	//Colors	
		Color background = uis.background;
		Color interactionFields = uis.interactionFields;
		
	//Fonts
		Font generalFont = uis.generalFont;
		Font labelFont = uis.labelFont;
		
//*****************************************************************************************
		
	//Panels
		//Adds a panel in the Mainwindow for better control connect
		JPanel mainWinodwReplacer = new JPanel();
		//Adds a panels for better controllablebility
		JPanel upperInputField = new JPanel(); 
		JPanel centerInputField = new JPanel();
		JPanel lowerInputField = new JPanel();
		JPanel mainCenterField = new JPanel();
		//Adds a spacer, does what the name is for
		JPanel spacerUpperBorder = new JPanel();
		JPanel spacerLowerBorder = new JPanel();
		JPanel spacerLeftBorder = new JPanel();
		JPanel spacerRightBorder = new JPanel();
		
	//Buttons
		JButton connectButton = new JButton();
		
	//TextFields
		JTextField serverAddress = new JTextField();
		JTextField port = new JTextField();
		JTextField userName = new JTextField();
		
	//Labels
		JLabel userNameLbl = new JLabel();
		JLabel colorSelectLbl = new JLabel();
		JLabel colorSelectWarningLbl = new JLabel();
		JLabel serverAdressLbl = new JLabel();
		JLabel portLbl = new JLabel();
		
	//ComboBox
		String[] colorStrings = { "Red", "Blue", "Green", "Yellow"};
		JComboBox colorSelect = new JComboBox(colorStrings);
		colorSelect.setSelectedIndex(3);
		
		this.add(mainWinodwReplacer);
		
	//Layout Init
		mainWinodwReplacer.setLayout(new BorderLayout(10, 10));
		mainCenterField.setLayout(new GridLayout(3, 1, 7, 7));
		upperInputField.setLayout(null);
		centerInputField.setLayout(null);
		lowerInputField.setLayout(null);
		
	//Background Init
		mainWinodwReplacer.setBackground(background);	//DEBUG OPTION
		upperInputField.setBackground(background);	//DEBUG OPTION
		centerInputField.setBackground(background);	//DEBUG OPTION
		lowerInputField.setBackground(background);	//DEBUG OPTION
		spacerUpperBorder.setBackground(background);	//DEBUG OPTION
		spacerLowerBorder.setBackground(background);	//DEBUG OPTION
		spacerLeftBorder.setBackground(background);	//DEBUG OPTION
		spacerRightBorder.setBackground(background);	//DEBUG OPTION
		
	//mainWindowReplacer Selection
		mainWinodwReplacer.add(spacerUpperBorder, BorderLayout.NORTH);
		mainWinodwReplacer.add(mainCenterField, BorderLayout.CENTER);
		mainWinodwReplacer.add(spacerLowerBorder, BorderLayout.SOUTH);
		mainWinodwReplacer.add(spacerLeftBorder, BorderLayout.WEST);
		mainWinodwReplacer.add(spacerRightBorder, BorderLayout.EAST);
			
	//mainCenterFieldr Selection	
		mainCenterField.add(upperInputField);
		mainCenterField.add(centerInputField);
		mainCenterField.add(lowerInputField);
		
		//since the window is fixed and cant be changed the x y cords are hardcoded
		upperInputField.add(serverAddress);
		upperInputField.add(port);
		upperInputField.add(serverAdressLbl);
		upperInputField.add(portLbl);
		
		
	//Server Address Field Init
		serverAdressLbl.setBounds(new Rectangle(10, 19, 320, 12));
		serverAdressLbl.setFont(labelFont);
		serverAdressLbl.setText("Server Address");
		
		serverAddress.setBounds(new Rectangle(10, 32, 220, 35));
		serverAddress.setBackground(interactionFields);	
		serverAddress.setText("Server Adresse");
		serverAddress.setFont(generalFont);
		serverAddress.setForeground(Color.LIGHT_GRAY);
	
		//this kind of if-statement set a low-visible Text as default
		serverAddress.addActionListener(e -> {
			if (serverAddress.getText() != "Server Adresse") {
				serverAddress.setFont(generalFont);
				serverAddress.setForeground(Color.BLACK);
			}
		});
		
	//Port Field Init
		portLbl.setBounds(new Rectangle(240, 19, 320, 12));
		portLbl.setFont(labelFont);
		portLbl.setText("Port (Default: 2342)");
		
		port.setBounds(new Rectangle(240, 32, 90, 35));
		port.setBackground(interactionFields);	
		port.setText("2342");
		port.setFont(generalFont);
		port.setForeground(Color.LIGHT_GRAY);
		port.addActionListener(e -> {
			if (port.getText() != "Server Adresse") {
				port.setFont(generalFont);
				port.setForeground(Color.BLACK);
			}
		});
		
		centerInputField.add(userName);
		centerInputField.add(colorSelect);
		centerInputField.add(userNameLbl);
		centerInputField.add(colorSelectLbl);
		centerInputField.add(colorSelectWarningLbl);
		
		
		
		
	//Username Init
		userNameLbl.setBounds(new Rectangle(10, 2, 320, 12));
		userNameLbl.setFont(labelFont);
		userNameLbl.setText("Username");
		
		userName.setBounds(new Rectangle(10, 15, 320, 35));
		userName.setFont(generalFont);
		userName.setBackground(interactionFields);
		userName.setText("Username");
		userName.setFont(generalFont);
		userName.setForeground(Color.LIGHT_GRAY);
		userName.addActionListener(e -> {
			if (userName.getText() != "Server Adresse") {
				userName.setFont(generalFont);
				userName.setForeground(Color.BLACK);
			}
		});
		
	//ColorInit
		colorSelectLbl.setBounds(new Rectangle(10, 51, 320, 12));
		colorSelectLbl.setFont(labelFont);
		colorSelectLbl.setText("Chose your prefferd Color");
			
		colorSelect.setBounds(new Rectangle(10, 67, 320, 35));
		colorSelect.setFont(generalFont);
		colorSelect.setBackground(new Color(230, 240, 90));
		colorSelect.addActionListener(e -> {switch (colorSelect.getSelectedIndex()) {
			case 0: colorSelect.setBackground(new Color(250, 160, 150));
				break;
			case 1: colorSelect.setBackground(new Color(105, 165, 200));
				break;
			case 2: colorSelect.setBackground(new Color(135, 200, 140));
				break;
			case 3: colorSelect.setBackground(new Color(230, 240, 90));
				break;
		}});
		//^^is for the chosen Color
		
		colorSelectWarningLbl.setBounds(new Rectangle(10, 101, 320, 12));
		colorSelectWarningLbl.setFont(labelFont);
		colorSelectWarningLbl.setText("(If your color is alrady taken you get a random Color)");
		
		
	//Connect Button Init
		lowerInputField.add(connectButton);
		connectButton.setBounds(new Rectangle(10, 25, 320, 35));
		connectButton.setBackground(interactionFields);	//DEBUG OPTION
		connectButton.setText("Connect");
		connectButton.setFont(generalFont);
		connectButton.addActionListener(e -> {
			
			this.setVisible(false);
			Main.ip = serverAddress.getText();
			Main.port = port.getText();
			Main.uname = userName.getText();
			Main.favColor = colorSelect.getSelectedIndex();
			
			System.out.println("Server Adress:" + Main.ip);
			System.out.println("Port:" + Main.port);
			System.out.println("username:" + Main.uname);
			System.out.println("Picked Color:" + Main.favColor);
			
		});
		
		this.setVisible(true);
	}
}
