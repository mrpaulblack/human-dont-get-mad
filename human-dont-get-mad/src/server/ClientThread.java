package server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import game.Log;
import game.LogController;
import game.MsgType;

/**
* <h1>ClientThread</h1>
* <p>The ClientThread class implements the interface Runnable and
* is therefore meant to be run on a extra thread. After the object is instantiated
* the run method is called.</p>
* <b>Note:</b> This gets called every time a client joins and every ClientThread
* uses the same ServerController object
*
* @author  Paul Braeuning
* @version 1.0
* @since   2021-07-23
*/
public class ClientThread implements Runnable {
	private Socket socket;
	private ServerController controller;
	private Scanner in;
	private PrintWriter out;
	private String bufferIn;
	private MsgType state = MsgType.WELCOME;

	/**
	 * <h1><i>ClientThread</i></h1>
	 * <p>This Constructor saves the socket object of the client the
	 * thread is getting created for and the ServerController object for 
	 * interaction with game logic.</p>
	 * @param socket - Socket of the connected TCP client
	 * @param controller - ServerController the controller for the running server
	 */
	ClientThread(Socket socket, ServerController controller) {
		this.socket = socket;
		this.controller = controller;
	}

	/**
	 * <h1><i>run</i></h1>
	 * <p>This method gets called after the object gets created on a separate
	 * thread. It setups the connection to the client and starts the handshake
	 * by sending a welcome message. After that it just listen for data from the client and
	 * sends it to the controller. It runs until the TCP connection gets killed.</p>
	 */
	@Override
	public void run() {
		LogController.log(Log.INFO, "Client connected: " + socket);
		try {
			in = new Scanner(socket.getInputStream());
			out = new PrintWriter(socket.getOutputStream(), true);
	        controller.sendWelcome(this);
			
			while (in.hasNextLine()) {
				bufferIn = in.nextLine();
				LogController.log(Log.TRACE, "RX: " + bufferIn);
				controller.decoder(this, bufferIn);
			}
		}
		catch (Exception e) { LogController.log(Log.ERROR, e.toString()); }
		finally {
			try {
				in.close();
				out.close();
				socket.close();
			}
			catch (IOException e) { LogController.log(Log.ERROR, e.toString()); }
			LogController.log(Log.INFO, "Client disconnect: " + socket);
			controller.disconnect(this);
		}
	}

	/**
	 * <h1><i>out</i></h1>
	 * <p>This method just sends a string to the client.</p>
	 * @param data - String with the payload
	 */
	protected void out(String data) {
		LogController.log(Log.TRACE, "TX: " + data);
		out.println(data);
	}
	
	/**
	 * <h1><i>setState</i></h1>
	 * <p>This methods sets the state the client is currently in, so that the client
	 * needs to go through the handshake properly before being part of the game.</p>
	 * @param newState - MsgType of the new state for this client
	 */
	protected void setState(MsgType newState) {
		state = newState;
	}
	
	/**
	 * <h1><i>getState</i></h1>
	 * <p>this method returns the current game so that the ServerController
	 * can verify the order of the received data.</p>
	 * @return state - MsgType returns the handshake state of the client.
	 */
	protected MsgType getState() {
		return state;
	}
}
