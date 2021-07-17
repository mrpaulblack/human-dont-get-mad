package client;

import java.util.Scanner;
import java.net.Socket;
import java.io.IOException;
import java.io.PrintWriter;

public class Main {
//private attributes
private static int port = 2342;
private static String init = "127.0.0.1";

	public static void main(String[] args) throws IOException {
		try (Socket socket = new Socket(init, port)) {
            System.out.println("Enter lines of text then Ctrl+D or Ctrl+C to quit");
            Scanner scanner = new Scanner(System.in);
            Scanner in = new Scanner(socket.getInputStream());
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            while (scanner.hasNextLine()) {
                out.println(scanner.nextLine());
                System.out.println(in.nextLine());
            }
        }

	}

}
