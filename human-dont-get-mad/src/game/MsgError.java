package game;

/**
* <h1>MsgType</h1>
* <p>Enum that contains the different error types supported by
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
}
