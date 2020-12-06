package menu;

import java.io.IOException;
import java.net.URL;
import java.util.Scanner;

/**
 * The type Message of the day.
 * @author Jeffrey
 */
public class MessageOfTheDay {


    /**
     * Read web page string.
     *
     * @return the string
     * @throws IOException the io exception
     */
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

    /**
     * Decodes the scrambled message from the website
     *
     * @return string
     * @throws IOException the io exception
     */
    public static String encode() throws IOException {
			String puzzle = MessageOfTheDay.readWebPage();
			String prefix = "CS-230";
			Boolean pointer = false;
			int counter = 0;
			String finalString = "";
			char current;

			for (int i = 0; i < puzzle.length(); i++) {
				counter++;
				current = puzzle.charAt(i);
				int ascii = 0;

				if (pointer == true) {
					ascii = current + counter;
				} else {
					ascii = current - counter;
				}

				finalString += (char) solvePuzzleASCII(ascii);
				pointer = !pointer;

			}
			return (prefix + finalString + (prefix.length() + counter));
		}

    /**
     * Solves an ASCII puzzle
     *
     * @param ascii the ascii
     * @return int
     */
    public static int solvePuzzleASCII(int ascii) {
			char start = 'A';
			char end = 'Z';

			if (ascii < start) {
				ascii = end - (start - ascii - 1);
			} else if (ascii > end) {
				ascii = start + ascii - end - 1;
			}
			return ascii;
		}

    /**
     * Returns the final daily message
     *
     * @return string
     * @throws IOException the io exception
     */
    public static String finalMessage() throws IOException {
			String solvedCode = MessageOfTheDay.encode();
			String requestURL = "http://cswebcat.swansea.ac.uk/message?solution=";

			URL url = new URL(requestURL + solvedCode);
			Scanner scanWeb = new Scanner(url.openStream());
			StringBuffer buffer = new StringBuffer();
			while (scanWeb.hasNext()) {
				buffer.append(scanWeb.next() + " ");
			}
			String result = buffer.toString();
	        return result;
		}
}
