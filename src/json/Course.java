package json;

public class Course {
	public String shortname;
	public String fullname;

	public AttendanceInstance[] attendance_instances;

	public String toString() {

		return  fullname;
	}

	public AttendanceInstance[] getAttendance_instances() {
		return attendance_instances;
	}

	public void setAttendance_instances(
			AttendanceInstance[] attendance_instances) {
		this.attendance_instances = attendance_instances;
	}
}
