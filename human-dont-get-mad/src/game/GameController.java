package game;

import org.json.JSONObject;

/**
* <h1>GameController</h1>
* <p>This is a layer of abstraction between the Game Class and the ServerController.
* The Class defines a set of methods through which the ServerController can put the
* parsed information through to the game. It also defines methods to return the game
* state to the caller.</p>
*
* @author  Paul Braeuning
* @version 1.0
* @since   2021-08-03
* @apiNote MAEDN 3.0
*/
public interface GameController {
	/**
	 * <h1><i>register</i></h1>
	 * <p>This method generates a new player and assigns a color to that player.
	 * It also checks if there are 4 players and if so just returns null without creating a
	 * actual player.</p>
	 * @param requestedColor - PlayerColor can either be null or a requested color
	 * @param name - String with the name of the player
	 * @param clientName - String with the name of the client
	 * @param clientVersion - Float with the version of the client
	 * @return PlayerColor - returns the selected color for the player
	 */
	public PlayerColor register(PlayerColor requestedColor, String name, String clientName, Float clientVersion);
	
	/**
	 * <h1><i>ready</i></h1>
	 * <p>This Constructor saves the socket object of the client the
	 * thread is getting created for and the ServerController object for 
	 * interaction with game logic.</p>
	 * @param color - PlayerColor of the client that is ready
	 * @param isReady - Boolean if player is ready or not
	 * @return Boolean - returns true if all players are ready; false otherwise
	 */
	public Boolean ready(PlayerColor color, Boolean isReady);
	
	/**
	 *	<h1><i>getGameState</i></h1>
	 * <p>This method returns the current game state.</p>
	 * @return gameState - returns the current game state
	 */
	public GameState getGameState();
	
	/**
	 * <h1><i>toJSON</i></h1>
	 * <p>This method is like a toString() and returns the current game state and all players
	 * with their attributes and figure positions as JSON. It supports the MAEDN protocol
	 * and is the main way to update the board on the client.</p>
	 * @return JSONObject - returns the game as JSON
	 */
	public JSONObject toJSON();
}
