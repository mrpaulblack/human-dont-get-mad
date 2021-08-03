package game;

/**
* <h1>MsgType</h1>
* <p>ENUM that contains the different error types supported by
* server and client.</p>
*
* @author  Paul Braeuning
* @version 1.0
* @since   2021-07-23
*/
public enum MsgError {
	UNSUPPORTEDMESSAGETYPE,
	UNSUPPORTEDPROTOCOLVERSION,
	SERVERFULL,
	ILLEGALMOVE,
	NOTYOURTURN,
	UNKNOWN;
	
	/**
	 * <h1><i>toString</i></h1>
	 * <p>This method is converting the ENUM to strings that are supported
	 * by the MAEDN specification.</p>
	 * @return String - return string supported by MAEDN specification
	 */
	@Override
	public String toString() {
		switch(this) {
		case UNSUPPORTEDMESSAGETYPE: return "unsupportedMessageType";
		case UNSUPPORTEDPROTOCOLVERSION: return "unsupportedProtocolVersion";
		case SERVERFULL: return "serverFull";
		case ILLEGALMOVE: return "illegalMove";
		case NOTYOURTURN: return "notYourTurn";
		case UNKNOWN: return "unknown";
		default: return "";
		}
	}
}
