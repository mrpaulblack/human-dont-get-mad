package client;

import java.awt.Dimension;
import java.awt.Toolkit;

/**
* Get the Monitors screensize and set 2 global variables
* <p>
* This Method will be executed when a new window is in its init Phase
*
* @param  
* @return	width	(int)	The width of the Screen 
* @return	height	(int)	The height of the Screen 
*/
public class GetScreenData {
	
	Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
	Integer width = (int) screensize.getWidth();
	Integer height = (int) screensize.getHeight();
	
	//Add Geter
}
