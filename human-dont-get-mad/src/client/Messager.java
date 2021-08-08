package client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneLayout;

public class Messager extends JFrame {
	
	UISettings uis = new UISettings();

	JScrollPane messageWindow = new JScrollPane();
	JTextArea incommingText = new JTextArea();
	JTextArea outgoingText = new JTextArea();
	JButton send = new JButton();
	
	String textToSend = ""; 
	
	GridBagConstraints gbc = new GridBagConstraints();
	
	 
	public void messager() {
		
		this.setBackground(uis.background);
		this.setLayout(new GridBagLayout());
		this.setSize(400, 650);
		
		messageWindow.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);  
		messageWindow.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED); 
		messageWindow.setLayout(new ScrollPaneLayout());
		messageWindow.getViewport().setBackground(uis.background);
		messageWindow.getViewport().add(incommingText);
		
		incommingText.setLineWrap(true);
		incommingText.setWrapStyleWord(true);	
		incommingText.setEditable(false);
		
		send.setText("Send");
		
		gbc.weightx = 1;
		gbc.weighty = 2;
		gbc.fill = GridBagConstraints.BOTH;
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridheight = 2;
		gbc.gridwidth = 2;
		
		this.add(messageWindow, gbc);
		
		gbc.weighty = 1;
		gbc.gridheight = 1;
		gbc.gridwidth = 1;
		
		gbc.gridx = 0;
		gbc.gridy = 2;
		this.add(outgoingText, gbc);
		outgoingText.setBackground(uis.background);
		
		gbc.gridx = 1;
		gbc.gridy = 2;
		this.add(send, gbc);
		send.setBackground(uis.background);	
	}
	
	public void displayMassager(boolean visible) {
		this.setVisible(visible);
	}

}
