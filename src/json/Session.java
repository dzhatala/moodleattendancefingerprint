package json;

import com.j256.ormlite.dao.RuntimeExceptionDao;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.j256.ormlite.field.DatabaseField;


public class Session {
	@DatabaseField
	public long id;
	@DatabaseField
	public int attendanceid;
	public int groupid;
	public long sessdate; // unix timestamp in seconds, for moodle server time ..
	public int duration;
	public long lastaken;
	public int lasttakenby;
	public long timemodified;
	public int studentscanmark;
	public String statusset;
	public SessionDetail detail; // need another REST CALL to be filled
	static SimpleDateFormat dateFormat = new SimpleDateFormat(
			"dd/MM/yyyy HH:mm");

	public Session(){
		
	}
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
