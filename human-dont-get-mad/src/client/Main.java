package client;

import java.util.Scanner;
import java.net.Socket;
import java.io.IOException;

public class Main {
//private attributes
private static int port = 2342;

	public static void main(String[] args) throws IOException {
        Socket socket = new Socket("127.0.0.1", port);
        Scanner in = new Scanner(socket.getInputStream());
        System.out.println("Server response: " + in.nextLine());

	}

}
