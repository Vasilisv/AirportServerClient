import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.concurrent.ConcurrentHashMap;

public class ServerProtocol {
	Info info;
	String[] parts;
	String theOutput;
	int count = 0;
	String idCopy;

	public String processRequest(String theInput, ConcurrentHashMap<String, Info> hashMap)
			throws FileNotFoundException, IOException {
		System.out.println("Received message from client: " + theInput);
		theOutput = theInput;
		info = null;

		parts = theInput.split(" "); // spaei se pinaka parts auto pou diavazoume

		if (parts[0].equals("WRITE")) {
			theOutput = WRITE(hashMap); // WRITE <KWDIKOS DROMOLOGIOU> <STATUS> <DATETIME>
		}

		else if (parts[0].equals("READ")) { // READ <KWDIKOS DROMOLOGIOU>
			theOutput = READ(hashMap);
		}

		else if (parts[0].equals("EDIT")) { // EDIT <KWDIKOS DROMOLOGIOU>
			String id = parts[1];

			info = hashMap.getOrDefault(id, info);
			if (info == null) {
				theOutput = "EDITERR";
			} else {
				count = 1;
				idCopy = id;
				return theOutput = "ENTER NEW STATUS,DATETIME FOR " + id + ":";
			}
		}

		else if (count == 1) { // Exei kalestei EDIT, to count egine 1 kai ekteleite h EDIT gia kainourgio
			// status,datetime.
			theOutput = EDIT(hashMap);
		}

		else if (parts[0].equals("DELETE")) { // DELETE <KWDIKOS DROMOLOGIOU
			theOutput = DELETE(hashMap);
		}

		else
			theOutput = "ERROR I/O";

		return theOutput;
	}

	public String WRITE(ConcurrentHashMap<String, Info> hashMap) {
		if (parts.length < 4 || parts.length > 4) {
			return theOutput = "WERR";
		} // elegxos orthothtas gia WRITE

		String id = parts[1];
		String status = parts[2];
		String datetime = parts[3];
		if (datetime.equals("NOW")) { // An sto datetime o pelaths dwsei NOW apothikeuetai h wra ths stigmhs
			String timeStamp = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(Calendar.getInstance().getTime());
			datetime = timeStamp;
		}

		info = new Info(id, status, datetime);
		hashMap.put(id, info);
		return theOutput = "WOK";
	}

	public String READ(ConcurrentHashMap<String, Info> hashMap) {
		String id = parts[1];

		info = hashMap.getOrDefault(id, info);
		if (info == null) {
			return theOutput = "RERR";
		} else {
			return theOutput = hashMap.get(id).MaketoString();
		}
	}

	public String EDIT(ConcurrentHashMap<String, Info> hashMap) {
		String id = idCopy;
		String status = parts[0];
		String datetime = parts[1];

		if (datetime.equals("NOW")) {
			String timeStamp = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(Calendar.getInstance().getTime());
			datetime = timeStamp;
		}

		info = new Info(id, status, datetime);
		hashMap.put(id, info);

		count = 0;
		return theOutput = "WOK";
	}

	public String DELETE(ConcurrentHashMap<String, Info> hashMap) {
		String id = parts[1];
		info = hashMap.getOrDefault(id, info);
		if (info == null) {
			return theOutput = "REMOVEERR";
		} // den vrethike tipota gia diagrafh
		else {
			hashMap.remove(id);
			return theOutput = id + "'s INFORMATIONS REMOVED";
		}
	}

}