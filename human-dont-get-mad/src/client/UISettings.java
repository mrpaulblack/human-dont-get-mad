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
	
//Fonts
	Font generalFont = new Font("Serif",Font.PLAIN,15);
	Font labelFont = new Font("Serif",Font.PLAIN,12);
}
