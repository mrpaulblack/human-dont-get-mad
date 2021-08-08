package client;

import java.awt.Color;
import java.awt.Font;

/**
* Will set the Launcher colors and fonts
* 
* @param  
* It don't return anything, it will set the variables of another function
* @return	Color	Return the color of various types
* @return	Font	Return the Font of various types
*/
public class UISettings {

	Color background = Color.LIGHT_GRAY;
	Color interactionFields = new Color(255, 255, 255);
	Color isReadyColor = new Color(94, 195, 105);
//Light Player Colors
	Color cRedPlayer = new Color(240, 165, 165);
	Color cBluePlayer = new Color(150, 170, 240);
	Color cGreenPlayer = new Color(150, 240, 170);
	Color cYellowPlayer = new Color(240, 240, 150);
	
	Color textColor = new Color(50, 50, 50);
	
//Fonts
	Font generalFont = new Font("Serif",Font.PLAIN,15);
	Font labelFont = new Font("Serif",Font.PLAIN,12);
}
