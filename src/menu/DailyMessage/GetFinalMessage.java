package menu.DailyMessage;

import java.io.IOException;
import java.net.URL;
import java.util.Scanner;

public class GetFinalMessage {
	public static void main(String[] args) throws IOException {
		String solvedCode = Encode.encode();
		String requestURL = "http://cswebcat.swansea.ac.uk/message?solution=";

		URL url = new URL(requestURL + solvedCode);
		Scanner scanWeb = new Scanner(url.openStream());
		StringBuffer buffer = new StringBuffer();
		while (scanWeb.hasNext()) {
			buffer.append(scanWeb.next() + " ");
		}
		String result = buffer.toString();
		System.out.print(result);
	}
}
