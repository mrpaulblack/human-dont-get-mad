package client;

import java.awt.Dimension;
import javax.swing.*;


public class Launcher extends JFrame{
	
	public void LauncherUI() {
		InitWindow();
		
		
	}

	public void InitWindow() {

		//Create Object
		GetScreenData gcd = new GetScreenData();
		
		Dimension size = new Dimension();
		Dimension findMidPoint = new Dimension();
		
		//Set Screen Size to this 
		size.setSize(400, 600);
		
		//Puts the Window MidPoint in the Screen Center
		findMidPoint.setSize((gcd.width/2) - (size.width/2), (gcd.height/2) - (size.height/2));
		
		//Sets the Windows Parrameters
		this.setSize(size);
		this.setResizable(false);
		this.setLocation(findMidPoint.width, findMidPoint.height);
		this.setVisible(true);
		
	}
	
}
