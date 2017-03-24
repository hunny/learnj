/**
 * 
 */
package hh.learnj.testj;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author huzexiong
 *
 */
public class TestFunc {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Integer hesitateDays = 0;
		Calendar calendar = Calendar.getInstance();
	    calendar.setTime(dateFormat.parse("2016-12-31 19:56:18"));

	    calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + hesitateDays + 1);
	    System.out.println(calendar.getTime());
	}

}
