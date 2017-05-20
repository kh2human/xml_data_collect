package time;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class TimeChecker {
	
	public static String getTime(String format) {
		Calendar now = Calendar.getInstance();
		DateFormat dateFormat = new SimpleDateFormat(format);
		return dateFormat.format(now.getTime());
	}
	
	public static String getTime() {
		Calendar now = Calendar.getInstance();
		DateFormat format = new SimpleDateFormat("[YYYY.MM.dd]HH:mm:ss");
		return format.format(now.getTime());
	}

	public static void main(String[] args) {
//		Calendar now = Calendar.getInstance();
//		DateFormat format = new SimpleDateFormat("[YYYY.MM.dd]HH:mm:ss");
		System.out.println("now : " + getTime());
	}

}
