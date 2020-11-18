import java.util.*;
import java.io.*;

/**
* Print today date and day of the week and random text.
*/
public class MessageOfTheDay {
	public static void main(String [] args) throws IOException {
		BufferedReader inputContent = new BufferedReader(new FileReader("randomContent.txt"));
		ArrayList<String> arrayList = new ArrayList<>();
		String line;
	
		while((line = inputContent.readLine()) != null)
			arrayList.add(line);
		Random random = new Random();
		int randomListSizeIndex = random.nextInt(arrayList.size());
        String todaysMessage = new TodayMessage().getTodayMessage();
        System.out.println(todaysMessage);
        System.out.println(arrayList.get(randomListSizeIndex));
	}
}
