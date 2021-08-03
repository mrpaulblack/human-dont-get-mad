package game;

//since August 2nd 2021
public class Player {
	private Figure[] figures = {new Figure(0), new Figure(1), new Figure(2), new Figure(3)};
	private PlayerColor color;
	private String name;
	private String clientName;
	private Float clientVersion;
	private Boolean ready = false;
	private Boolean isBot = false;
	
	//constructor set client attributes
	public Player(PlayerColor color, String name, String clientName, Float clientVersion, Boolean isBot) {
		this.color = color;
		this.name = name;
		this.clientName = clientName;
		this.clientVersion = clientVersion;
		this.isBot = isBot;
	}
	
	//get color from player
	protected PlayerColor getColor() {
		return color;
	}
}
