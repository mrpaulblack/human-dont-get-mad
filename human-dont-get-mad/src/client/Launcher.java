package client;

import java.awt.*;
import java.util.Random;

import game.Log;
import game.LogController;
import javax.swing.*;

/**
* <h1>Launcher</h1>
* <p>Will display the Launcher</p>
*
* @author  Tim Menzel
* @version 1.0
* @since   2021-08-2
*/
public class Launcher extends JFrame{
	
	private ClientController clientController;
	private GameWindow gamewindow;
	
	//Create Object
	GetScreenData gcd = new GetScreenData();
	
	//Constructor
	public Launcher(ClientController clientController, GameWindow gamewindow) {
		this.clientController = clientController;
		this.gamewindow = gamewindow;
		Launcher();	
	}
	
	/**
	 *	<h1><i>Launcher</i></h1>
	 * <p>This is the screen you will see while you want to establish a connection</p>
	 */
	public void Launcher() {
		
		Dimension size = new Dimension();
		Dimension findMidPoint = new Dimension();

		//Set Screen Size to this //So Parts are Hardcoded -> Changes may lead to errors
		size.setSize(400, 450);
		
		//Put the Window MidPoint in the Screen Center
		findMidPoint.setSize((gcd.width/2) - (size.width/2), (gcd.height/2) - (size.height/2));
		
		//Set the Windows Parameters
		this.setSize(size);
		this.setResizable(false);
		this.setLocation(findMidPoint.width, findMidPoint.height);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Human-Dont-Get-Mad: connect");
		
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
		String[] colorStrings = {"Red", "Blue", "Green", "Yellow"};
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
		serverAddress.setText("127.0.0.1");
		serverAddress.setFont(generalFont);
		serverAddress.setForeground(uis.textColor);
	
	//Port Field Init
		portLbl.setBounds(new Rectangle(240, 19, 320, 12));
		portLbl.setFont(labelFont);
		portLbl.setText("Port (Default: 2342)");
		
		port.setBounds(new Rectangle(240, 32, 90, 35));
		port.setBackground(interactionFields);	
		port.setText("2342");
		port.setFont(generalFont);
		port.setForeground(uis.textColor);
		
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
		userName.setText(getRandomUserName());
		userName.setFont(generalFont);
		userName.setForeground(uis.textColor);
		
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
			Client client = new Client(clientController, gamewindow, this);
			clientController.player(client);
			
			client.serverAdress = serverAddress.getText();
			client.port = Integer.parseInt(port.getText());
			clientController.userName = userName.getText();
			clientController.favColor = colorSelect.getSelectedItem().toString().toLowerCase(); //for now it is yellow because there isent a function to handle this feature
			
			
			LogController.log(Log.DEBUG, "Server Adress:" + client.serverAdress);
			LogController.log(Log.DEBUG, "Port:" + client.port);
			LogController.log(Log.DEBUG, "Username: " + clientController.userName);
			LogController.log(Log.DEBUG, "Picked Color:" + clientController.favColor) ;

			//Main.tryingConnect = true;
			//Main.connectionHandler(this);
			client.start();
		});	
	}
	
	
	public String getRandomUserName() {
		Random ran = new Random();
		switch (ran.nextInt(25)) {
		case 0: return "Admin";
		case 1: return "Tim";
		case 2: return "Paul";
		case 3: return "Konrad";
		case 4: return "Mayer";
		case 5: return "Schmid";
		case 6: return "PeterPan";
		case 7: return "abcdefgh";
		case 8: return "NameUSer";
		case 9: return "Passwort";
		case 10: return "123";
		case 11: return "HTWK Player";
		case 12: return "Bitte Gib";
		case 13: return "WindowsLover";
		case 14: return "LinuxHater";
		case 15: return "LinuxForWin";
		case 16: return "Uns Eine";
		case 17: return "JKP";
		case 18: return "Wer Das Liest is Doof";
		case 19: return "Hund";
		case 20: return "Katze";
		case 21: return "(Hier könnte ihre Werbung stehen)";
		case 22: return "Hey there iam using Wahtsapp";
		case 23: return "SUN";
		case 24: return "Uns 1";
		case 25: return "Kleines";
		default: return "";
		}
	}
}
