package client;


import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneLayout;

public class Messager extends JFrame {
	
	UISettings uis = new UISettings();

	JScrollPane messageWindow = new JScrollPane();
	JTextArea incommingText = new JTextArea();
	JTextField outgoingText = new JTextField();
	JButton send = new JButton();
	
	String textToSend = ""; 
	
	GridBagConstraints gbc = new GridBagConstraints();
	 
	public void messager() {
		
		this.setBackground(uis.background);
		this.setLayout(new GridBagLayout());
		this.setSize(400, 650);
		
		messageWindow.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);  
		messageWindow.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED); 
		messageWindow.setLayout(new ScrollPaneLayout());
		messageWindow.getViewport().setBackground(uis.background);
		messageWindow.getViewport().add(incommingText);
		
		incommingText.setLineWrap(false);
		incommingText.setWrapStyleWord(false);	
		incommingText.setEditable(false);
		
		send.setText("Send");
		
		gbc.weightx = 1;
		gbc.weighty = 10;
		gbc.fill = GridBagConstraints.BOTH;
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridheight = 2;
		gbc.gridwidth = 2;
		
		this.add(messageWindow, gbc);
		
		
		gbc.weighty = 0.5;
		gbc.weighty = 1;
		gbc.gridheight = 1;
		gbc.gridwidth = 1;
		
		gbc.gridx = 0;
		gbc.gridy = 2;
		this.add(outgoingText, gbc);
		outgoingText.setBackground(uis.background);
		
		
		gbc.gridx = 0;
		gbc.gridy = 3;
		this.add(send, gbc);
		send.setBackground(uis.background);	
	}
	
	public void displayMassager(boolean visible) {
		this.setVisible(visible);
	}

}
