package client;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class Messager extends JFrame {
	

	public JScrollPane messageWindow = new JScrollPane();
	 
	JList list = new JList();
	ArrayList<String> showMessage = new ArrayList();
	
	public JTextArea ta = new JTextArea();
	 
	public void messager() {
		messageWindow.setBackground(Color.black);
		
		this.setLayout(new GridLayout(1,1,1,1));
		//list.add
		this.add(list);
		
		messageWindow.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);  
		messageWindow.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED); 
		
		ta.setText("fgdfhjdöfihjgdfgioödfhg");
	}
	
	public void displayMassager(boolean visible) {
		System.out.println("Sichtbarkeit ist "+ visible);
		this.setVisible(visible);
	}

}
