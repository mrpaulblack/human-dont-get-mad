package server;

import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import game.Log;
import game.LogController;

/**
* <h1>Server</h1>
* <p>Main Class that has the static main that gets invoked when starting the server.
* It basically starts the server socket and listen for connections indef. Each time a client
* connects to the server in creats a new thread for that client through a executer pool.</p>
* <b>Note:</b> You cannot run multiple server objects on the same port, but it should be possible
* to run two or more servers at the same time on different ports.
*
* @author  Paul Braeuning
* @version 1.0
* @since   2021-07-23
*/
public class Server {
	private ServerSocket serverSocket;
	
	/**
	 *	<h1><i>main</i></h1>
	 * <p>Static void main methods that gets called when starting the server.
	 * It creates a server object, sets the global log level and starts the server.</p>
	 * @param args - String[] with the called arguments (ignored for now)
	 */
	public static void main(String[] args) {
		Server server = new Server();
		LogController.setGlobalLogLvl(Log.debug);
		server.start(2342);
	}

	/**
	 *	<h1><i>start</i></h1>
	 * <p>This method starts a server that listens on a given port. It then
	 * creates a executer pool and initiates a ClientThread for each connecting client
	 * in that executer pool. If more clients connected that get FIFO until there is a availible
	 * thread for that socket.</p>
	 * @param port - Integer of the port the server listens on.
	 */
	private void start(Integer port) {
		try {
			serverSocket = new ServerSocket(port);
			LogController.log(Log.info, "Running: " + serverSocket);
			ServerController controller = new ServerController();
			ExecutorService  pool = Executors.newFixedThreadPool(8);
			while (true) {
				pool.execute(new ClientThread(serverSocket.accept(), controller));
			}
		}
		catch (Exception e) { LogController.log(Log.error, e.toString()); }
	}

}
