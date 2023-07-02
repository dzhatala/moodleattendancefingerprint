package moodle_login_01;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.TimeZone;
import java.util.Vector;
import java.sql.Date;
import java.sql.Timestamp;

import javax.swing.WindowConstants;

import cpintar.Launcher;

/**
 * 
 * @author Zulkarnaen Hatala
 * 
 */

public class FingerDatePair implements Cloneable {

	Vector TSs = new Vector(); // TODO performance LIST <> ?
	// public String

	// the date timestamp in long format GMT timestamp
	private long PRINT_TS = -1;
	// user local id to search for finger print
	public long localid = -1;
	PRINT_STATUS_CODE printStatus = PRINT_STATUS_CODE.PRINT_NOT_TAKEN;

	// public long fingerDate;
	public long remoteid;

	public String firstName = null;// for cache/consistency check ?
	public String lastName = null;// for cache/consistency check?
	public String localInfo;

	public long getPRINT_TS() {
		return PRINT_TS;
	}

	/**
	 * change print_DATE also
	 * 
	 * @param pRINT_TS
	 */
	public void setPRINT_TS(long pRINT_TS) {
		this.PRINT_TS = pRINT_TS;

	}

	// return caches version , value change to PRINT_TS
	public LocalDateTime getPRINT_date() {

		// System.out.println(TimeZone.getDefault());
		return Instant.ofEpochMilli(PRINT_TS).atZone(ZoneId.systemDefault())
				.toLocalDateTime();

	}

	public PRINT_STATUS_CODE getPrintStatus() {
		return printStatus;
	}

	public void setPrintStatus(PRINT_STATUS_CODE printStatus) {
		this.printStatus = printStatus;
	}

	public long getRemoteid() {
		return remoteid;
	}

	public void setRemoteid(long remoteid) {
		this.remoteid = remoteid;
	}

	public static enum PRINT_STATUS_CODE {

		PRINT_TAKEN, PRINT_NOT_TAKEN
	};

	public FingerDatePair() {
		printStatus = PRINT_STATUS_CODE.PRINT_NOT_TAKEN;
	}

	public long getLocalid() {
		return localid;
	}

	public void setLocalid(long localid) {
		this.localid = localid;
	}

	/*
	 * public long getFingerDate() { // WindowConstants.EXIT_ON_CLOSE; return
	 * fingerDate; }
	 * 
	 * public void setFingerDate(long fingerDate) { this.fingerDate =
	 * fingerDate; }
	 */

	// public Timestamp timestamp=null; java.sql.Timestamp BUG

	public void setLocalInfo(String localInfo) {
		this.localInfo = localInfo;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@SuppressWarnings("deprecation")
	public String toString() {

		// java.sql.Timestamp BUG
		// if (timestamp != null)
		// return timestamp.toLocaleString();
		String ret=Launcher.dateFormatZone.format(new Date(getPRINT_TS()));
		return ret;
	}

	public String getLocalInfo() {
		// TODO Auto-generated method stub
		return localInfo;
	}

	/**
	 * 
	 * @param print_TS2
	 *            java.sql.Timestamp BUG
	 */
	public void addTimestamp(Long print_TS2) {
		// TODO Auto-generated method stub
		TSs.add(print_TS2);

	}

	/***
	 * get additional timestamps TODO java.sql.Timestamp BUG use Long
	 * 
	 * @return
	 */
	// TODO performance
	public Long[] getTimeStamps() {
		Object[] os = TSs.toArray();

		Long []ret = new Long[os.length];
		for (int i = 0; i < os.length; i++) {
			ret[i] = (Long) os[i];
		}
		return ret;

	}

	public Object clone() throws CloneNotSupportedException {

		// Object o = super.clone();

		FingerDatePair ret = new FingerDatePair();

		ret.printStatus=this.printStatus;
		ret.remoteid=this.remoteid;
		ret.firstName = this.firstName;
		ret.lastName = this.lastName;
		ret.localid = this.localid;
		ret.localInfo = this.localInfo;
		ret.PRINT_TS = PRINT_TS;

		return ret;
	}

	public void removeTimeStamp(Long long1) {
		// TODO Auto-generated method stub
		TSs.remove(long1);
		
	}

}