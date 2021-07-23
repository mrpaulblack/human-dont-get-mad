package client;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import game.Log;
import game.LogController;

public class Client {
	private static int port = 2342;
	private static String init = "127.0.0.1";
	private Socket socket;
	private Scanner in;
	private PrintWriter out;

	public static void main(String[] args) {
		Client client = new Client();
		LogController.setGlobalLogLvl(Log.debug);
		client.start();
	}
	
	//start client socket
	private void start() {
		try {
			socket = new Socket(init, port);
			in = new Scanner(socket.getInputStream());
			out = new PrintWriter(socket.getOutputStream(), true);
			ClientController controller = new ClientController(this);
			while (in.hasNextLine()) {
				controller.decipher(in.nextLine());
			}
		}
		catch (Exception e) { LogController.log(Log.error, e.toString()); }
	}
	
	//transmission / output to server
	public void out(String data) {
		out.println(data);
	}

}
