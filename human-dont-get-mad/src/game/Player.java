package game;

import java.net.Socket;

public class Player {
	private Socket socket;
	private PlayerColor color;
	private String name;
	private String clientName;
	private Float clientVersion;
	private Boolean ready = false;
	private Boolean isBot = false;
	private Figure[] figures = new Figure[4];
	
	//constructor set client attributes
	public Player(PlayerColor color, String name, String clientName, Float clientVersion, Boolean isBot) {
		this.color = color;
		this.name = name;
		this.clientName = clientName;
		this.clientVersion = clientVersion;
		this.isBot = isBot;

		for (Integer i = 0; i < figures.length; i++) {
			figures[i] = new Figure();
		}
	}
	
	//get color from player
	public PlayerColor getColor() {
		return color;
	}
}
