package client;

import java.awt.Dimension;
import java.awt.Toolkit;

public class GetScreenData {
	
	Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
	int width = (int) screensize.getWidth();
	int height = (int) screensize.getHeight();
	
}
