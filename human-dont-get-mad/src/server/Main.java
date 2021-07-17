package server;

import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
//private attributes
private static int port = 2342;

//static main
public static void main(String[] args) throws IOException {
	try (ServerSocket serverSocket = new ServerSocket(port)) {
        System.out.println("The server is running...");
        ExecutorService  pool = Executors.newFixedThreadPool(20);
        while (true) {
            pool.execute(new ClientThread(serverSocket.accept()));
        }
    }
}


private static class ClientThread implements Runnable {
    private Socket socket;

    ClientThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        System.out.println("Connected: " + socket);
        try {
            Scanner in = new Scanner(socket.getInputStream());
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            while (in.hasNextLine()) {
                out.println(in.nextLine() + " recieved sucessfully");
            }
        }
        catch (Exception e) { System.out.println("Error:" + socket); }
        finally {
            try {
                socket.close();
            }
            catch (IOException e) { System.out.println(e); }
            System.out.println("Closed: " + socket);
        }
    }
}


}
