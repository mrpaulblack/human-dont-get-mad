package client;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;

public class GameControl {
	
	static String transmit = "test";
	static GameWindow gw = new GameWindow();
	
	public static void transmiter() {
			
			transmit = Main.ip;
			System.out.println("Text from TA: " + transmit);
			
			gw.setText();
		
	}
}

