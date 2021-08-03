package game;

import org.json.JSONObject;

//since August 02nd 2021
public class Figure {
	//TODO implement position and position change
	private GamePosition positionType = GamePosition.HOME;
	private Integer positionIndex;
	
	public Figure(Integer startIndex) {
		this.positionIndex = startIndex;
	}
	
	//return position as json in maedn spec
	protected JSONObject getJSON() {
		JSONObject json = new JSONObject();
		json.put("type", positionType.toString().toLowerCase());
		json.put("index", positionIndex);
		return json;
	}
}
