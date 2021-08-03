package game;

/**
* <h1>GamePosition</h1>
* <p>ENUM that contains the the first part of the figure
* position defined in position in the MAEDN protocol. A valid position would
* be for example: [GamePosition.start,2] or [GameState.field, 24].</p>
*
* @author  Paul Braeuning
* @version 1.0
* @since   2021-07-23
*/
public enum GamePosition {
	START,
	HOME,
	FIELD;
}
