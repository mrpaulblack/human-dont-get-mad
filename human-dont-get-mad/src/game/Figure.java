package game;

//since August 02nd 2021
public class Figure {
	//TODO implement position and position change
	private GamePosition positionType = GamePosition.HOME;
	private Integer positionIndex;
	
	public Figure(Integer startIndex) {
		this.positionIndex = startIndex;
	}
}
