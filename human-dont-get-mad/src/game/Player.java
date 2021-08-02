package game;


public class Player {
	private String color;
	private String name;
	private String clientName;
	private Float clientVersion;
	private Boolean ready = false;
	private Boolean isBot = false;
	private Figure[] figures = new Figure[4];
	
	//constructor set client attributes
	public Player(String color, String name, String clientName, Float clientVersion) {
		this.color = color;
		this.name = name;
		this.clientName = clientName;
		this.clientVersion = clientVersion;
	}
}
