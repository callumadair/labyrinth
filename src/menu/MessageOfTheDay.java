package menu;

import java.io.IOException;
import java.net.URL;
import java.util.Scanner;

/**
 * Message Of The Day fetches and decodes a string from the CS-230 webpage.
 *
 * @author Jeffrey
 */
public class MessageOfTheDay {


    /**
     * Read scrambled string from the web page.
     *
     * @return the string
     * @throws IOException the io exception
     */
    public static String readWebPage() throws IOException {
        URL url = new URL("http://cswebcat.swansea.ac.uk/puzzle");
        Scanner scanner = new Scanner(url.openStream());
        StringBuilder buffer = new StringBuilder();
        while (scanner.hasNext()) {
            buffer.append(scanner.next());
        }
        return buffer.toString();
    }

    /**
     * Decodes the scrambled message from the website
     *
     * @return returns the decoded string.
     * @throws IOException the io exception
     */
    public static String decode() throws IOException {
        String puzzle = readWebPage();
        String prefix = "CS-230";
        boolean pointer = false;
        int counter = 0;
        StringBuilder finalString = new StringBuilder();
        char current;

        for (int i = 0; i < puzzle.length(); i++) {
            counter++;
            current = puzzle.charAt(i);
            int ascii = 0;

            if (pointer) {
                ascii = current + counter;
            } else {
                ascii = current - counter;
            }

            finalString.append((char) solvePuzzleASCII(ascii));
            pointer = !pointer;

        }
        return (prefix + finalString + (prefix.length() + counter));
    }

    /**
     * Solves an ASCII puzzle
     *
     * @param ascii the ascii
     * @return int int
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
     * @return string string
     * @throws IOException the io exception
     */
    public static String finalMessage() throws IOException {
        String solvedCode = decode();
        String requestURL = "http://cswebcat.swansea.ac.uk/message?solution=";

        URL url = new URL(requestURL + solvedCode);
        Scanner scanWeb = new Scanner(url.openStream());
        StringBuilder buffer = new StringBuilder();
        while (scanWeb.hasNext()) {
            buffer.append(scanWeb.next()).append(" ");
        }
        return buffer.toString();
    }
}
