package json;

public class AttendanceStatusInfo {
	// statuses":[{"id":5,"attendanceid":1,"acronym":"P","description":"Present","grade":2,"visible":1,"deleted":0,"setnumber":0}

	public int id;
	public int attendanceid;
	public String acronym;
	public String description;
	public float grade;
	public int visible;
	public int deleted;
	public int setnumber;

	public String toString() {
//		return id + ":" + description;
		 return description;
	}
	
	/**
	 * 
	 * @param master
	 * @param d
	 * @return -1 if status/description d not valid
	 */

	public static int descriptionToID(AttendanceStatusInfo[] master, String d) {
		int ret = -1;
		for (int i = 0; i < master.length; i++) {
			if (d.equalsIgnoreCase(master[i].description)) {
				ret = master[i].id;
				break;
			}
		}
		// System.out.println("ASI dTID() desc:" + d + " ret:" + ret);
		if (ret == -1) {// for detail check
			for (int i = 0; i < master.length; i++) {
				System.out.println("ASI dTID() master[" + i + "] :#"
						+ master[i].description + "# desc:#" + d + "#");

			}
		}
		return ret;

	}
	
	public static String ID2Description(AttendanceStatusInfo[] master, int id) {
		String ret = null;
		for (int i = 0; i < master.length; i++) {
			if (id==master[i].id) {
				ret = master[i].description;
				break;
			}
		}
		return ret;

	}


	public static String getStatusSet(AttendanceStatusInfo[] master) {
		String ret = "";
		if (master != null) {
			for (int i = 0; i < master.length; i++) {
				if (master.length > 1 & i < master.length & i > 0)
					ret += ",";
				ret += master[i].id; //
			}
		}

		return ret;
	}
}
