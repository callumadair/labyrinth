import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
/**
* Get the date and day of the week
* @return The date and day of the week
*/
public class TodayMessage {
	public String getTodayMessage(){
		   LocalDate today = LocalDate.now(Config.MY_TIMEZONE);
		   DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("EEEE, MMMM dd");
		   String todayDate = today.format(dateFormatter);
		   return String.format(			       
			       "Today is %s",
			       todayDate
			   );
	}
	/**
	* Set timezone to London
	*/
	public static class Config {
		   public static final ZoneId MY_TIMEZONE = ZoneId.of("Europe/London");
		}
}
