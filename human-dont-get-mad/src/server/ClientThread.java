package server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import game.Log;
import game.LogController;

public class ClientThread implements Runnable {
	private Socket socket;
	private ServerController controller;
	private Scanner in;
	private PrintWriter out;
	private String bufferIn;

	ClientThread(Socket socket, ServerController controller) {
		this.socket = socket;
		this.controller = controller;
	}

	//setup and recieve
	@Override
	public void run() {
		//setup
		LogController.log(Log.info, "Client connected: " + socket);
		try {
			in = new Scanner(socket.getInputStream());
			out = new PrintWriter(socket.getOutputStream(), true);
	        controller.sendWelcome(this);
			
			while (in.hasNextLine()) {
				bufferIn = in.nextLine();
				LogController.log(Log.debug, "RX: " + bufferIn);
				controller.decipher(this, bufferIn);
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
			LogController.log(Log.info, "Client disconnect: " + socket);
		}
	}
	    
	//transmission / output to client
	public void out(String data) {
		out.println(data);
		LogController.log(Log.debug, "TX: " + data);
	}
}
