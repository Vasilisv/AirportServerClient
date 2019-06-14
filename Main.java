import java.net.*;
import java.util.concurrent.ConcurrentHashMap;
import java.io.*;

public class Main {
	private static final int PORT = 1001;

	public static void main(String args[]) throws IOException {

		ServerSocket connectionSocket = new ServerSocket(PORT);
		ConcurrentHashMap<String, Info> hashMap = new ConcurrentHashMap<>();
		while (true) {
			System.out.println("Server is listening to port: " + PORT);
			Socket dataSocket = connectionSocket.accept();
			System.out.println("Received request from " + dataSocket.getInetAddress());

			ServerThread sthread = new ServerThread(dataSocket, hashMap);
			sthread.start(); // Ksekiname ena serverthread se kathe syndesh
		}
	}
}
