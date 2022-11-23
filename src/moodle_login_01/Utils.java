package moodle_login_01;

import java.text.SimpleDateFormat;
import java.util.Calendar;
//import java.util.Timestamp;
//import java.sql.Timestamp;
import java.util.Date;

public class Utils {

	static SimpleDateFormat df = new SimpleDateFormat(
			"dd/MM/yyyy HH:mm:ss");

	/**
	 * return timestamps in mili seconds for the beginning and end of the day
	 * ret[0] is beginning of ts, ret[1] is end of ts
	 * @param anydate
	 * @param begin_day
	 * @param end_day
	 * @return
	 */
	public static long[] TSDateStartEnd(Date anydate, int begin_day,
			int end_day) {
		long[] ret = new long[] { anydate.getTime() / 1000,
				anydate.getTime() / 1000 };
		Calendar c = Calendar.getInstance();
		c.setTime(anydate);
		c.set(Calendar.HOUR_OF_DAY, begin_day);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		ret[0] = c.getTimeInMillis() ;
		c.set(Calendar.HOUR_OF_DAY, end_day);
		ret[1] = c.getTimeInMillis() ;

		return ret;
	}
	
	
	public static void main(String args[]){
		Date now=new Date();
		long[] moodleTSes=TSDateStartEnd(now, 5,19);
		System.out.println(df.format(now));
		
		System.out.println(df.format(new Date(moodleTSes[0])));
		System.out.println(df.format(new Date(moodleTSes[1])));
	}

}
