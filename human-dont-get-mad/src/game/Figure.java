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
	//TODO implement position and position change
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

	//TODO write doc
	protected GamePosition getType() {
		return positionType;
	}

	//TODO write doc
	protected void setType(GamePosition newType) {
		positionType = newType;
	}

	//TODO write doc
	protected Integer getIndex() {
		return positionIndex;
	}

	//TODO write doc
	protected void setIndex(Integer newIndex) {
		//TODO check if index is valid based on position type
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
