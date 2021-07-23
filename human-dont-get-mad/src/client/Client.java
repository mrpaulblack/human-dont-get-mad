package client;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
	private static int port = 2342;
	private static String init = "127.0.0.1";
	private static Log clientLog = Log.debug;
	private Socket socket;
	private Scanner in;
	private PrintWriter out;

	public static void main(String[] args) {
		Client client = new Client();
		client.start();
	}
	
	//start client socket
	private void start() {
		try {
			socket = new Socket(init, port);
			in = new Scanner(socket.getInputStream());
			out = new PrintWriter(socket.getOutputStream(), true);
			while (in.hasNextLine()) {
				System.out.println(in.nextLine());
			}
		}
		catch (Exception e) { System.out.println(e); }
	}
	
	//transmission / output to server
	public void out(String data) {
		out.println(data);
	}
	
	//logging facility
	public static void log(Log logLvl, String log) {
		//error lvl
		if (clientLog == Log.error && logLvl == Log.error) {
			System.out.println("[" + logLvl + "] " + log);
		}
		//info lvl
		else if (clientLog == Log.info && (logLvl == Log.error || logLvl == Log.info)) {
			System.out.println("[" + logLvl + "] " + log);
		}
		//debug lvl
		else if (clientLog == Log.debug && (logLvl == Log.error || logLvl == Log.info || logLvl == Log.debug)) {
			System.out.println("[" + logLvl + "] " + log);
		}
	}
}
