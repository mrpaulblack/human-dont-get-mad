package server;

import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import game.Log;
import game.LogController;

/**
* <h1>Server</h1>
* <p>Main Class that has the static main that gets invoked when starting the server.
* It basically starts the server socket and listen for connections indefinitely. Each time a client
* connects to the server in creates a new thread for that client through a executer pool.</p>
* <b>Note:</b> You cannot run multiple server objects on the same port, but it should be possible
* to run two or more servers at the same time on different ports.
*
* @author  Paul Braeuning
* @version 1.0
* @since   2021-07-23
*/
public class Server {
	private ServerSocket serverSocket;
	private static Integer port = 2342;

	/**
	 * <h1><i>main</i></h1>
	 * <p>Static void main methods that gets called when starting the server.
	 * It creates a server object, sets the global log level and starts the server.</p>
	 * @param args - String[] with the called arguments
	 */
	public static void main(String[] args) throws Exception {
		for (Integer i = 0; i < args.length; i++) {
			if (args[i].toLowerCase().equals("-h") || args[i].toLowerCase().equals("--help")) {
				System.out.println("Human dont get mad server supporting the MAEDN protocol.");
				System.out.println("The server is listening on 0.0.0.0:2342 by default.\n");
				System.out.println("The following arguments are supported:");
				System.out.println("Print this help screen: -h or --help");
				System.out.println("Set Log Level: -l or --log [error] [info] [debug] [trace]");
				System.out.println("Set a server port: -p or --port [port]");
				System.exit(0);
			}
			else if ((args[i].toLowerCase().equals("-l") || args[i].toLowerCase().equals("--log")) && i +1 < args.length) {
				if (args[i +1].toLowerCase().equals("error")) {
					LogController.setGlobalLogLvl(Log.ERROR);
				}
				else if (args[i +1].toLowerCase().equals("info")) {
					LogController.setGlobalLogLvl(Log.INFO);
				}
				else if (args[i +1].toLowerCase().equals("debug")) {
					LogController.setGlobalLogLvl(Log.DEBUG);
				}
				else if (args[i +1].toLowerCase().equals("trace")) {
					LogController.setGlobalLogLvl(Log.TRACE);
				}
			}
			else if ((args[i].toLowerCase().equals("-p") || args[i].toLowerCase().equals("--port")) && i +1 < args.length) {
				port = Integer.parseInt(args[i+1]);
			}
		}

		Server server = new Server();
		server.start(port);
	}

	/**
	 * <h1><i>start</i></h1>
	 * <p>This method starts a server that listens on a given port. It then
	 * creates a executer pool and initiates a ClientThread for each connecting client
	 * in that executer pool. If more clients connected that get FIFO until there is a availible
	 * thread for that socket.</p>
	 * @param port - Integer of the port the server listens on.
	 */
	private void start(Integer port) {
		try {
			serverSocket = new ServerSocket(port);
			LogController.log(Log.INFO, "Running: " + serverSocket);
			ServerController controller = new ServerController();
			ExecutorService  pool = Executors.newFixedThreadPool(8);
			while (true) {
				pool.execute(new ClientThread(serverSocket.accept(), controller));
			}
		}
		catch (Exception e) { LogController.log(Log.ERROR, e.toString()); }
	}

}
