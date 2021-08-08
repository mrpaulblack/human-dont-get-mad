package client;

import java.awt.Dimension;
import java.awt.Toolkit;

/**
* <h1>GetScreenData</h1>
* <p>Gets with the help of a Toolkit the Screensize 
* Just get called if the app first starts</p>
* <b>Note:</b> Dont Support changing screensizes, if the window got on a new screen 
* while it is running it wont adjust it self automatically
*
* @author  Tim Menzel
* @version 1.0
* @since   2021-08-2
*/
public class GetScreenData {
	
	Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
	Integer width = (int) screensize.getWidth();
	Integer height = (int) screensize.getHeight();
}
