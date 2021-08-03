package game;

import java.util.HashMap;
import java.util.Map;

//TODO needs doc
public enum PlayerColor {
	RED(0),
	BLUE(1),
	GREEN(2),
	YELLOW(3);
	
	private int value;
    private static Map<Integer, PlayerColor> map = new HashMap<Integer, PlayerColor>();
    private static Boolean[] availColor = {true, true, true, true};

    //constructor
    private PlayerColor(Integer value) {
        this.value = value;
    }

    //setup haspmap for player color on startup
    static {
        for (PlayerColor PlayerColor : PlayerColor.values()) {
            map.put(PlayerColor.value, PlayerColor);
        }
    }

    //get PlyerColor object from Integer
    public static PlayerColor valueOf(Integer PlayerColor) {
        return (PlayerColor) map.get(PlayerColor);
    }

    //get int value of color
    public Integer getValue() {
        return value;
    }
    
    //returns first avail color
    public static PlayerColor getAvail() {
    	for (Integer i = 0; i < availColor.length; i++) {
			if (availColor[i]) {
				availColor[i] = false;
				return PlayerColor.valueOf(i);
			}
		}
    	return null;
    }
    
    //returns requested color or avail.
    public static PlayerColor getAvail(PlayerColor requestedColor) {
    	if (availColor[requestedColor.getValue()]) {
    		availColor[requestedColor.getValue()] = false;
    		return requestedColor;
    	}
    	else { return PlayerColor.getAvail(); }
    }
}
