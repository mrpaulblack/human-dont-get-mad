package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import game.Log;
import game.LogController;

public class Server {
	public static void main(String[] args) throws IOException {
		Server server = new Server();
		LogController.setGlobalLogLvl(Log.debug);
		server.start();
	}
	
	//private attributes
	private ServerSocket serverSocket;
	private static int port = 2342;

	//start server socket
	private void start() {
		try {
			serverSocket = new ServerSocket(port);
			LogController.log(Log.info, "Running: " + serverSocket);
			ExecutorService  pool = Executors.newFixedThreadPool(20);
			ServerController controller = new ServerController();
			while (true) {
				pool.execute(new ClientThread(serverSocket.accept(), controller));
			}
		}
		catch (Exception e) { LogController.log(Log.error, e.toString()); }
	}

}
