package server;

import java.io.*;
import java.net.*;

public class Main {
//private attributes
private static int port = 2342;

//static main
public static void main(String[] args) throws IOException {
    try (ServerSocket listener = new ServerSocket(port)) {
        System.out.println("The server is running...");
        while (true) {
            try (Socket socket = listener.accept()) {
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                out.println(socket.toString());
               System.out.println("client " + socket.toString() + " served.");
                socket.close();
            }
        }
    }
}

}
