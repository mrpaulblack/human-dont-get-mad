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
	 * <h1><i>remove</i></h1>
	 * <p>This method removes a player from the game. It does not matter if the game
	 * is already running or not, since it is just going to replace the player with
	 * a BOT while running or replacing the player with a new one while queuing.
	 * It does also make the color of the player available again.</p>
	 * @param color - PlayerColor of the removed player
	 */
	public void remove(PlayerColor color);

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
	 * <h1><i>getState</i></h1>
	 * <p>This method returns the current game state.</p>
	 * @return gameState - returns the current game state
	 */
	public GameState getState();

	/**
	 * <h1><i>currentPlayer</i></h1>
	 * <p>This method returns the current player in the game logic.</p>
	 * @return PlayerColor - color of the current player
	 */
	public PlayerColor currentPlayer();

	/**
	 * <h1><i>toJSON</i></h1>
	 * <p>This method is like a toString() and returns the current game state and all players
	 * with their attributes and figure positions as JSON. It supports the MAEDN protocol
	 * and is the main way to update the board on the client.</p>
	 * @return JSONObject - returns the game as JSON
	 */
	public JSONObject toJSON();
	
	/**
	 * <h1><i>turn</i></h1>
	 * <p>This method has two modes:
	 * Dry run, which is called with null and returns the turn options for
	 * the current player and second, execute, which is called by an integer starting with
	 * -1 up to the amount of turn options. After execution it returns OK for successful or finished, if
	 * the player won the game</p>
	 * @param selected - Integer with the selected option or null
	 * @return JSONObject - returns the turn as JSON or if execute was successful
	 */
	public JSONObject turn(Integer selected);
}
