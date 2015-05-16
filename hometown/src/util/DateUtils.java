/**
 * 
 */
package util;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * @author Kaleb
 *
 */
public class DateUtils {

	public static String Now(String dateFormat){
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		return sdf.format(cal.getTime());
	}
}
