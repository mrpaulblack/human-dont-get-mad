package client;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import game.Log;
import game.LogController;

public class Client {
	public static void main(String[] args) {
		Client client = new Client();
		LogController.setGlobalLogLvl(Log.debug);
		client.start();
	}
	
	private static Integer port = 2342;
	private static String host = "127.0.0.1";

	private Socket socket;
	private Scanner in;
	private PrintWriter out;
	private String bufferIn;
	private ClientController controller;
	
	//start client socket
	private void start() {
		try {
			socket = new Socket(host, port);
			LogController.log(Log.info, "Connected to server: " + socket);
			in = new Scanner(socket.getInputStream());
			out = new PrintWriter(socket.getOutputStream(), true);
			controller = new ClientController(this);
			while (in.hasNextLine()) {
				bufferIn = in.nextLine();
				LogController.log(Log.debug, "RX: " + bufferIn);
				controller.decoder(bufferIn);
			}
		}
		catch (Exception e) { LogController.log(Log.error, e.toString()); }
		finally {
			try {
				in.close();
				out.close();
				socket.close();
			}
			catch (IOException e) { LogController.log(Log.error, e.toString()); }
			LogController.log(Log.info, "Disconnected from server: " + socket);
		}
	}
	
	//transmission / output to server
	public void out(String data) {
		out.println(data);
		LogController.log(Log.debug, "TX: " + data);
	}

}
