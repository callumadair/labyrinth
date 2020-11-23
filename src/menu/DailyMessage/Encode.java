import java.io.IOException;

public class Encode {
	public static String encode() throws IOException {
		String puzzle = ReadingWebPage.readWebPage();
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
}
