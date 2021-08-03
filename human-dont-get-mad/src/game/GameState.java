package game;

/**
* <h1>GameState</h1>
* <p>ENUM that contains the valid game states defined in
* update in the MAEDN protocol.</p>
*
* @author  Paul Braeuning
* @version 1.0
* @since   2021-07-23
*/
public enum GameState {
	WAITINGFORPLAYERS,
	RUNNING,
	FINISHED;
	
	/**
	 * <h1><i>toString</i></h1>
	 * <p>This method is converting the ENUM to strings that are supported
	 * by the MAEDN specification.</p>
	 * @return String - return string supported by MAEDN specification
	 */
	@Override
	public String toString() {
		switch(this) {
		case WAITINGFORPLAYERS: return "waitingForPlayers";
		case RUNNING: return "running";
		case FINISHED: return "finished";
		default: return "";
		}
	}
}
