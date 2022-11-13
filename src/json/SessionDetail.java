package json;

public class SessionDetail {
	public int id;
	public int attendanceid;
	public int groupid;
	public long sessdate; // timestamp in seconds, for moodle server time zone
							// ..
	public int duration; // in seconds
	public long lasttaken;
	public int lasttakenby;
	public long timemodified;
	public int studentscanmark;
	public String statusset;
	public MoodleUser[] users;
	public AttendanceStudentInfo[] attendance_log;
	public AttendanceStatusInfo[] statuses;
	public String description;
	public int descriptionformat;

	public String toString() {
		String ret = "sess id: " + id;
		ret += " statusset: " + statusset + "  "; // +
		ret += " sessdate: " + sessdate + "  "; // +
												// Utils.moodleTSToDate(sessdate);
		ret += " duration: " + duration + " second(s)";
		ret += " ,log:" + attendance_log.length;
		ret += " ,statuses:" + statuses.length;
		// ret += " ,desc:" + description;

		return ret;

	}
}
