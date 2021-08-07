package game;

import org.json.JSONObject;

/**
* <h1>Figure</h1>
* <p>A simple figure class that can store the position for a simple figure
* on the board.</p>
* <b>Note:</b> The class can return its position in JSON.
*
* @author  Paul Braeuning
* @version 1.0
* @since   2021-07-23
* @apiNote MAEDN 3.0
*/
public class Figure {
	private GamePosition positionType = GamePosition.START;
	private Integer positionIndex;

	/**
	 * <h1><i>Figure</i></h1>
	 * <p>This Constructor just sets the start position for that figure.</p>
	 * @param startIndex - Integer of the index on the start field
	 */
	public Figure(Integer startIndex) {
		this.positionIndex = startIndex;
	}

	/**
	 * <h1><i>getType</i></h1>
	 * <p>Returns the current position type of that figure.</p>
	 * @return GamePosition - current position type
	 */
	protected GamePosition getType() {
		return positionType;
	}

	/**
	 * <h1><i>setType</i></h1>
	 * <p>This method sets the new position type of a figure.</p>
	 * @param newType - GamePosition is the new position type
	 */
	protected void setType(GamePosition newType) {
		positionType = newType;
	}

	/**
	 * <h1><i>getIndex</i></h1>
	 * <p>Returns the current position index of that figure.</p>
	 * @return Integer - current index in the position type
	 */
	protected Integer getIndex() {
		return positionIndex;
	}

	/**
	 * <h1><i>setIndex</i></h1>
	 * <p>This method sets the new position index of a figure.</p>
	 * @param newIndex - GamePosition is the new position index
	 */
	protected void setIndex(Integer newIndex) {
		positionIndex = newIndex;
	}

	/**
	 * <h1><i>getJSON</i></h1>
	 * <p>This method returns the position of the figure on the field
	 * as JSON in MAEDN specification.</p>
	 * @return JSONObject - returns the position as JSON
	 */
	protected JSONObject getJSON() {
		JSONObject json = new JSONObject();
		json.put("type", positionType.toString());
		json.put("index", positionIndex);
		return json;
	}
}
