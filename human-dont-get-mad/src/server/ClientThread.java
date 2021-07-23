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
		System.out.println("Connected: " + socket);
		try {
			in = new Scanner(socket.getInputStream());
			out = new PrintWriter(socket.getOutputStream(), true);
	        controller.sendWelcome(this);
			
			//recieve messages till socket closes
			while (in.hasNextLine()) {
				//execute on new input
			}
		}
		catch (Exception e) { System.out.println("Error:" + socket); }
		//close connection
		finally {
			try {
				socket.close();
			}
			catch (IOException e) { System.out.println(e); }
			System.out.println("Closed: " + socket);
		}
	}
	    
	//transmission / output to client
	public void out(String data) {
		out.println(data);
	}
}
