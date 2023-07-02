package json;

import java.text.SimpleDateFormat;
import java.util.Date;

import moodle_login_01.Utils;

public class Session {
	public int id;
	int attendanceid;
	int groupid;
	long sessdate; // unix timestamp in seconds, for moodle server time ..
	int duration;
	long lastaken;
	int lasttakenby;
	long timemodified;
	int studentscanmark;
	String statusset;
	public SessionDetail detail; // need another REST CALL to be filled
	static SimpleDateFormat dateFormat = new SimpleDateFormat(
			"dd/MM/yyyy HH:mm");

	public String toString() {
		// String ret = "id: " + id;
		String ret = "";
		if (detail != null) {
			ret += detail.description + " ";

		}
		// ret += " sessdate: " + sessdate + "  " +
		// Utils.moodleTSToDate(sessdate);
		ret += " duration: " + (duration / 60) + " minute(s)";
		if (detail != null) {
			ret += " ,log:" + detail.attendance_log.length;
			// ret +=" ,statuses:" +detail.statuses.length;
			ret += " , " + dateFormat.format(new Date(detail.sessdate * 1000));
			// ret += ", "+detail.description;
		} else {
			ret += " (no detail) ";
		}
		return ret;

	}
}
