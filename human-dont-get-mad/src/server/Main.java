package server;

import java.io.*;
import java.net.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
private static int port = 2342;

//static main
public static void main(String[] args) throws IOException {
	try (ServerSocket serverSocket = new ServerSocket(port)) {
        System.out.println("Running: " + serverSocket);
        ExecutorService  pool = Executors.newFixedThreadPool(20);
        Controller controller = new Controller();
        while (true) {
            pool.execute(new ClientThread(serverSocket.accept(), controller));
        }
    }
}

}
