import java.net.*;
import java.io.*;

public class ReaderClientTCP2 {
	private static final String HOST = "127.0.0.1";
	private static final int PORT = 1001;
	private static final String EXIT = "CLOSE";

	public static void main(String args[]) throws IOException {

		Socket dataSocket = new Socket(HOST, PORT);

		InputStream is = dataSocket.getInputStream();
		BufferedReader in = new BufferedReader(new InputStreamReader(is));
		OutputStream os = dataSocket.getOutputStream();
		PrintWriter out = new PrintWriter(os, true);

		System.out.println("Connection to " + HOST + " established");

		String inmsg, outmsg;

		while (true) { // gia kathe aithma pelath to outmsg periexei to mynhma pou erxetai apo ton pelath
			
			ClientProtocol app = new ClientProtocol();
			outmsg = app.prepareRequest();

			out.println(outmsg);
			inmsg = in.readLine();
			app.processReply(inmsg);

			// dataSocket.close();
			// System.out.println("Data Socket closed");
		}
	}
}
