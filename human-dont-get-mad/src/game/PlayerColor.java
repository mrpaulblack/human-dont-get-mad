package game;

import java.util.HashMap;
import java.util.Map;

/**
* <h1>PlayerColor</h1>
* <p>This ENUM defines the available player colors. It is also defining the
* order of the players while playing and giving out colors at the start.</p>
* <b>Note:</b> The availColor attribute is static so that every color is
* only used once.
*
* @author  Paul Braeuning
* @version 1.0
* @since   2021-08-03
*/
public enum PlayerColor {
	RED(0),
	BLUE(1),
	GREEN(2),
	YELLOW(3);
	
	private int value;
    private static Map<Integer, PlayerColor> map = new HashMap<Integer, PlayerColor>();
    private static Boolean[] availColor = {true, true, true, true};
    
    static {
        for (PlayerColor PlayerColor : PlayerColor.values()) {
            map.put(PlayerColor.value, PlayerColor);
        }
    }

    /**
	 * <h1><i>PlayerColor</i></h1>
	 * <p>This constructor is called when creating a PlayerColor ENUM.
	 * It is a workaround to map and integer value to each member of the ENUM.</p>
	 * @param value - Integer of the index of the color
	 */
    private PlayerColor(Integer value) {
        this.value = value;
    }

    /**
	 * <h1><i>valueOf</i></h1>
	 * <p>This method uses the static Hash Map to return a player color
	 * for each integer index.</p>
	 * @param playerColor - Integer of the player color
	 * @return PlayerColor - returns the player color with that index
	 */
    public static PlayerColor valueOf(Integer PlayerColor) {
        return (PlayerColor) map.get(PlayerColor);
    }

    /**
	 * <h1><i>getValue</i></h1>
	 * <p>This method returns the integer of a player color from the ENUM.</p>
	 * @return Integer - return value of a specific player color
	 */
    public Integer getValue() {
        return value;
    }
    
    /**
	 * <h1><i>getAvail</i></h1>
	 * <p>This method checks if the requested color is still available and returns
	 * an assigned Color to the player.</p>
	 * @param requestedColor - PlayerColor either null or requested Color
	 * @return PlayerColor - returns the selected color for the player
	 */
    public static PlayerColor getAvail(PlayerColor requestedColor) {
    	if (requestedColor != null && availColor[requestedColor.getValue()]) {
    		availColor[requestedColor.getValue()] = false;
    		return requestedColor;
    	}
    	else {
    		for (Integer i = 0; i < availColor.length; i++) {
    			if (availColor[i]) {
    				availColor[i] = false;
    				return PlayerColor.valueOf(i);
    			}
    		}
        	return null;
    	}
    }
}
