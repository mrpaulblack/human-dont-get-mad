package server;

import java.net.*;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
	public static void main(String[] args) throws IOException {
		Server server = new Server();
		server.start();
	}
	
	//private attributes
	private ServerSocket serverSocket;
	private static Log serverLog = Log.debug;
	private static int port = 2342;

	//start server socket
	private void start() {
		try {
			serverSocket = new ServerSocket(port);
			Server.log(Log.info, "Running: " + serverSocket);
			ExecutorService  pool = Executors.newFixedThreadPool(20);
			Controller controller = new Controller();
			while (true) {
				pool.execute(new ClientThread(serverSocket.accept(), controller));
			}
		}
		catch (Exception e) { Server.log(Log.error, e.toString()); }
	}
	
	//logging facility
	public static void log(Log logLvl, String log) {
		//error lvl
		if (serverLog == Log.error && logLvl == Log.error) {
			System.out.println("[" + logLvl + "] " + log);
		}
		//info lvl
		else if (serverLog == Log.info && (logLvl == Log.error || logLvl == Log.info)) {
			System.out.println("[" + logLvl + "] " + log);
		}
		//debug lvl
		else if (serverLog == Log.debug && (logLvl == Log.error || logLvl == Log.info || logLvl == Log.debug)) {
			System.out.println("[" + logLvl + "] " + log);
		}
	}

}
