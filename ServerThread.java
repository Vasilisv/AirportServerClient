import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

class ServerThread extends Thread {
	private Socket dataSocket;
	private InputStream is;
	private BufferedReader in;
	private OutputStream os;
	private PrintWriter out;
	private static final String EXIT = "CLOSE";
	private ConcurrentHashMap<String, Info> hashMapCopy;

	public ServerThread(Socket socket, ConcurrentHashMap<String, Info> hashMap) {
		hashMapCopy = hashMap;
		dataSocket = socket;
		try {
			is = dataSocket.getInputStream();
			in = new BufferedReader(new InputStreamReader(is));
			os = dataSocket.getOutputStream();
			out = new PrintWriter(os, true);
		} catch (IOException e) {
			System.out.println("I/O Error " + e);
		}
	}

	public void run() {
		String inmsg, outmsg;

		try {
			ServerProtocol app = new ServerProtocol();
			inmsg = in.readLine();
			outmsg = app.processRequest(inmsg, hashMapCopy); // pername sto serverprotocol to hashmap

			while (!outmsg.equals(EXIT)) {
				out.println(outmsg);
				inmsg = in.readLine();
				outmsg = app.processRequest(inmsg, hashMapCopy);
			}
			System.out.println(outmsg);

			dataSocket.close();
			System.out.println("Data socket closed");

		} catch (IOException e) {
			System.out.println("I/O Error " + e);
		}
	}
}
