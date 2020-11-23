import java.io.IOException;
import java.net.URL;
import java.util.Scanner;

public class ReadingWebPage {
	public static String readWebPage() throws IOException {
		// Instantiating the URL class
		URL url = new URL("http://cswebcat.swansea.ac.uk/puzzle");
		// Retrieving the contents of the specified page
		Scanner scanWeb = new Scanner(url.openStream());
		// Instantiating the StringBuffer class to hold the result
		StringBuffer buffer = new StringBuffer();
		while (scanWeb.hasNext()) {
			buffer.append(scanWeb.next());
		}
		String result = buffer.toString();
		return result;
	}
}