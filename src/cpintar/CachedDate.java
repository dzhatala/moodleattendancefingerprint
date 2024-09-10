package cpintar;

import i18n.LL;

import java.util.Date;

public class CachedDate extends Date {
	String coursefullname = LL.TR(this, "FN_Unknown");
	String courseshortname = LL.TR(this, "SN_Unknown");

	public CachedDate() {

	}

	public CachedDate(Date d) {
		// super();
		if (d != null)
			;
		setTime(d.getTime());
	}

	public String getCoursefullname() {
		return coursefullname;
	}

	public void setCoursefullname(String coursefullname) {
		this.coursefullname = coursefullname;
	}
}
