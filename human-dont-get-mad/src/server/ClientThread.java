package server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientThread implements Runnable {
	private Socket socket;
	private Controller controller;
	private Scanner in;
	private PrintWriter out;

	ClientThread(Socket socket, Controller controller) {
		this.socket = socket;
		this.controller = controller;
	}

	//setup and recieve
	@Override
	public void run() {
		//setup
		Server.log(Log.info, "Client connected: " + socket);
		try {
			in = new Scanner(socket.getInputStream());
			out = new PrintWriter(socket.getOutputStream(), true);
	        controller.sendWelcome(this);
			
			while (in.hasNextLine()) {
				controller.deciverInput(this, in.nextLine());
			}
		}
		catch (Exception e) { Server.log(Log.error, "Error: " + socket); }
		finally {
			try {
				in.close();
				out.close();
				socket.close();
			}
			catch (IOException e) { Server.log(Log.error, "Error: " + socket); }
			Server.log(Log.info, "Client disconnect: " + socket);
		}
	}
	    
	//transmission / output to client
	public void out(String data) {
		out.println(data);
	}
}
