package client;

import java.awt.*;
import java.awt.event.ComponentEvent;

import javax.swing.*;


public class Launcher extends JFrame{
	
	public void LauncherUI() {
		InitWindow();
		
		
	}

	public void InitWindow() {

		Dimension upperFieldSize;
		Main Main = new Main();
		
		//Create Object
		GetScreenData gcd = new GetScreenData();
		
		Dimension size = new Dimension();
		Dimension findMidPoint = new Dimension();

		//Set Screen Size to this 
		size.setSize(400, 550);
		
		//Put the Window MidPoint in the Screen Center
		findMidPoint.setSize((gcd.width/2) - (size.width/2), (gcd.height/2) - (size.height/2));
		
		//Set the Windows Parrameters
		this.setSize(size);
		this.setResizable(false);
		this.setLocation(findMidPoint.width, findMidPoint.height);
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//*****************************************************************************************
		
	//Colors	
		Color background = new Color(215, 215, 210);
		Color interactionFields = new Color(255, 255, 255);
		
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
		
		//Server Address Field Init
		serverAddress.setBounds(new Rectangle(10, 50, 220, 50));
		serverAddress.setBackground(interactionFields);	
		serverAddress.setText("Server Adresse");
		serverAddress.setFont(new Font("Serif",Font.PLAIN,20));
		serverAddress.setForeground(Color.LIGHT_GRAY);
		
		//this kind of if-statement set a low-visible Text as default
		serverAddress.addActionListener(e -> {
			if (serverAddress.getText() != "Server Adresse") {
				serverAddress.setFont(new Font("Serif",Font.PLAIN,35));
				serverAddress.setForeground(Color.BLACK);
			}
		});
		
		//Port Field Init
		port.setBounds(new Rectangle(240, 50, 90, 50));
		port.setBackground(interactionFields);	
		port.setText("Port");
		port.setFont(new Font("Serif",Font.PLAIN,20));
		port.setForeground(Color.LIGHT_GRAY);
		port.addActionListener(e -> {
			if (port.getText() != "Server Adresse") {
				port.setFont(new Font("Serif",Font.PLAIN,35));
				port.setForeground(Color.BLACK);
			}
		});
		
		centerInputField.add(userName);
		centerInputField.add(colorSelect);
		
		//Username Init
		userName.setBounds(new Rectangle(10, 20, 320, 50));
		userName.setFont(new Font("Serif",Font.PLAIN,35));
		userName.setBackground(interactionFields);
		userName.setText("Username");
		userName.setFont(new Font("Serif",Font.PLAIN,20));
		userName.setForeground(Color.LIGHT_GRAY);
		userName.addActionListener(e -> {
			if (userName.getText() != "Server Adresse") {
				userName.setFont(new Font("Serif",Font.PLAIN,35));
				userName.setForeground(Color.BLACK);
			}
		});
		
		//ColorInit
		colorSelect.setBounds(new Rectangle(10, 80, 320, 50));
		colorSelect.setFont(new Font("Serif",Font.PLAIN,35));
		colorSelect.setBackground(new Color(230, 240, 90));
		colorSelect.addActionListener(e -> {switch (colorSelect.getSelectedIndex()) {
			case 0: colorSelect.setBackground(new Color(250, 160, 150));
				break;
			case 1: colorSelect.setBackground(new Color(140, 140, 180));
				break;
			case 2: colorSelect.setBackground(new Color(135, 200, 140));
				break;
			case 3: colorSelect.setBackground(new Color(230, 240, 90));
				break;
		}});
		//^^is for the chosen Color
		
		
		//Connect Button Init
		lowerInputField.add(connectButton);
		connectButton.setBounds(new Rectangle(10, 50, 320, 50));
		connectButton.setBackground(interactionFields);	//DEBUG OPTION
		connectButton.setText("Connect");
		connectButton.setFont(new Font("Serif",Font.PLAIN,20));
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
			
			System.exit(0); //PLaceHOLD
		});
		
		this.setVisible(true);
	}
}
