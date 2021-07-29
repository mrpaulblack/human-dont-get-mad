package game;

/**
* <h1>MsgType</h1>
* <p>Enum that contains the valid types of messages defined in
* update in the maedn protocol.</p>
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
	ERROR
}
