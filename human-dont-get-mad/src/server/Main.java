package server;

import java.io.*;
import java.net.*;

public class Main {
private static int port = 2342;

	public static void main(String[] args) {
		System.out.println("First server socket impl");
		
		try {
			ServerSocket serverSocket = new ServerSocket(port);
			System.out.println("waiting for client connection on port " + port);
			Socket clientSocket = serverSocket.accept();
			System.out.println("client conencted: " + clientSocket.getInetAddress());
			PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
			BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}

	}

}
