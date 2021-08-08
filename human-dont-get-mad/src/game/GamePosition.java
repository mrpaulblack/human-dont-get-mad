package game;

/**
* <h1>GamePosition</h1>
* <p>ENUM that contains the the first part of the figure
* position defined in position in the MAEDN protocol. A valid position would
* be for example: [GamePosition.start,2] or [GameState.field, 24].</p>
*
* @version 1.0
* @since   2021-07-23
*/
public enum GamePosition {
	START,
	HOME,
	FIELD;
	
	/**
	 * <h1><i>toString</i></h1>
	 * <p>This method is converting the ENUM to strings that are supported
	 * by the MAEDN specification.</p>
	 * @return String - return string supported by MAEDN specification
	 */
	@Override
	public String toString() {
		switch(this) {
		case START: return "start";
		case HOME: return "home";
		case FIELD: return "field";
		default: return "";
		}
	}
}
