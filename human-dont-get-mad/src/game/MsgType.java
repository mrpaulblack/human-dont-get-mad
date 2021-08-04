package game;

/**
* <h1>MsgType</h1>
* <p>ENUM that contains the valid types of messages defined in
* update in the MAEDN protocol.</p>
*
* @author  Paul Braeuning
* @version 1.0
* @since   2021-07-23
*/
public enum MsgType {
	REGISTER,
	READY,
	MOVE,
	WELCOME,
	ASSIGNCOLOR,
	UPDATE,
	TURN,
	PLAYERDISCONNECTED,
	MESSAGE,
	ERROR;
	
	/**
	 * <h1><i>toString</i></h1>
	 * <p>This method is converting the ENUM to strings that are supported
	 * by the MAEDN specification.</p>
	 * @return String - return string supported by MAEDN specification
	 */
	@Override
	public String toString() {
		switch(this) {
		case REGISTER: return "register";
		case READY: return "ready";
		case MOVE: return "move";
		case WELCOME: return "welcome";
		case ASSIGNCOLOR: return "assignColor";
		case UPDATE: return "update";
		case TURN: return "turn";
		case PLAYERDISCONNECTED: return "playerDisconnected";
		case MESSAGE: return "message";
		case ERROR: return "error";
		default: return "";
		}
	}
}
