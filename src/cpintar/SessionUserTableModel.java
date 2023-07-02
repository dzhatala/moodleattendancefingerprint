package cpintar;

import java.awt.Component;
import java.util.Date;
import java.util.Hashtable;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import moodle_login_01.FingerDatePair;
import moodle_login_01.RemotePair;
import json.AttendanceStatusInfo;
import json.AttendanceStudentInfo;
import json.SessionDetail;

/**
 * 
 * @author Zulkarnaen Hatala
 * 
 */
public class SessionUserTableModel extends DefaultTableModel {
	public static final String FINGER_NOT_TAKEN = "NOT_TAKEN"; // NOT TAKEN for
																// finger print
	private SessionDetail _detail;

	Hashtable<String, AttendanceStudentInfo> studentId2log = new Hashtable(); // put
																				// remoteid
																				// vs
																				// AttendanceStatusInfo
	Hashtable studentId2localId = new Hashtable(); // remote to local id

	/**
	 * also store original value for backup
	 */
	Hashtable studentId2FingerPair = new Hashtable();

	AttendanceStudentInfo[] attInfos = null; // cache
	AttendanceStatusInfo[] statusInfo = null; // cache

	// COL_NAME position must synchron with XXX in COL_?? = XXX
	public static final String[] COL_NAME = new String[] { "localid",
			"remote_id", "firstname", "lastname", "status", "finger_date" };
	public static final int COL_LOCALID = 0;
	public static final int COL_REMOTEID = 1;
	public static final int COL_FIRSTNAME = 2;
	public static final int COL_LASTNAME = 3;
	public static final int COL_STATUS = 4;
	public static final int COL_FINGERDATE = 5;

	public SessionUserTableModel(SessionDetail d, RemotePair[] rlPairs,
			FingerDatePair[] fdPairs) {
		super(COL_NAME, 0);
		// addRow(COL_NAME);
		this._detail = d;
		attInfos = _detail.attendance_log;

		if (attInfos != null) {
			for (int i = 0; i < attInfos.length; i++) {
				AttendanceStudentInfo info = attInfos[i];
				// studentId2log.put(info.studentid + "", info.id); //
				studentId2log.put(info.studentid + "", info); //
				/*
				 * System.out.println("table put() studentid: #" +
				 * info.studentid + "#, statusid" + info.statusid);
				 * System.out.println("table retest get: " + info.studentid +
				 * " -> " + studentId2log.get(info.studentid + ""));
				 */

			}
		}

		statusInfo = _detail.statuses;

		// remote <-> local mapping
		if (rlPairs != null) {
			if (rlPairs.length > 0) {
				for (int i = 0; i < rlPairs.length; i++) {
					studentId2localId.put(rlPairs[i].getRemote() + "",
							rlPairs[i].getLocal() + "");
					// if(studentId2localId.)
				}
			}
		}

		System.out.println("sutm adding remote user total:"
				+ _detail.users.length);

		if (fdPairs != null) {
			System.out.println("sutdm fdPAris hash: " + fdPairs.hashCode()
					+ ", length:" + fdPairs.length);
			if (fdPairs.length > 0) {
				for (int i = 0; i < fdPairs.length; i++) {
					Object old = studentId2FingerPair.put(
							fdPairs[i].getRemoteid() + "", fdPairs[i]);
					// System.out.println("sutm put:" + fdPairs[i].getRemoteid()
					// + " -> timestamp:" + fdPairs[i].getPRINT_date());

					// altering existing ..
					if (old != null) {
						System.out.println(" sutm Addition timestamp detected "
								+ fdPairs[i].getFirstName() + " " + old);
						if (old instanceof FingerDatePair) {
							FingerDatePair fdo = (FingerDatePair) old;

							// if closer we must change the timestamp
							// if nOT closer just addTimestamp();
							// but we can do HERE, since we don't know ROW
							// we can add in initValues();
							fdo.addTimestamp(fdPairs[i].getPRINT_TS());

							// REPUT OLD which is added
							studentId2FingerPair.put(fdPairs[i].getRemoteid()
									+ "", fdo);

							// System.out.println("sutm put:" +
							// fdPairs[i].getRemoteid()
							// + " -> timestamp:" + fdo);
						}
					}
				}
			}
		}

		// if(false)
		for (int r = 1; r < _detail.users.length + 1; r++) {
			Vector rv = new Vector();
			for (int col = 0; col < COL_NAME.length; col++) {
				rv.add(initValues(r, col));
			}
			addRow(rv);
		}

	}

	/*
	 * public int getColumnCount() { return COL_NAME.length; }
	 * 
	 * public int getRowCount() { if (_detail != null) { return
	 * _detail.users.length + 1; } return 1; }
	 * 
	 * void test() { // super.get // super.getRowCount(); }
	 * 
	 * // must synchronized with getColumnName public String getColumnName(int
	 * column) { return COL_NAME[column]; }
	 */

	// must synchronized with getValueAt

	public Object initValues(int row, int col) {
		switch (row) {
		case 0:
			return getColumnName(col);
		default:
			if (row > 0)// not header
				if (_detail != null) {
					switch (col) {
					case 1: // remote_id
						return _detail.users[row - 1].id;
					case 2: //
						return _detail.users[row - 1].firstname;
					case 3: //
						return _detail.users[row - 1].lastname;
					case COL_STATUS: // status set
						String key = _detail.users[row - 1].id + "";
						// System.out.println("table to get: #" + key + "#");
						Object o = studentId2log.get(key);
						// System.out.println("... " + o);
						if (o != null) {
							// return o;
							// return
							// statusId2Description(Integer.parseInt(o.toString()));
							AttendanceStudentInfo info = (AttendanceStudentInfo) o;
							return AttendanceStatusInfo.ID2Description(
									_detail.statuses, info.statusid);
						} else {
							return "id:" + _detail.users[row - 1].id
									+ " not set ?";
						}
					case 0: // local id
						key = _detail.users[row - 1].id + "";
						// System.out.println("local to get: #" + key + "#");
						o = studentId2localId.get(key);
						// System.out.println("local : " + o);
						if (o != null) {
							return o;
							// return statusId2Description(new Integer((String)
							// o)
							// .intValue());
						} else {
							return "rid:" + _detail.users[row - 1].id
									+ " no local ";
						}
						// return _detail.users[row - 1].lastname;
					case COL_FINGERDATE:

						key = _detail.users[row - 1].id + "";
						// System.out.println("initvalues local to get: #" + key
						// + "#");
						o = studentId2FingerPair.get(key);

						// //debug
						// String p = ("local : " + o);
						// if (o != null)
						// p += ", class :" + o.getClass();
						// if (o instanceof FingerDatePair) {
						// p += " timestamp: "
						// + ((FingerDatePair) o).getPRINT_date();
						// p += " longts: "
						// + ((FingerDatePair) o).getPRINT_TS();
						// }
						// System.out.println(p);

						if (o != null) {
							// TODO sorting here for the closest
							// absolute/magnitude to session start start

							if (o instanceof FingerDatePair) {
								FingerDatePair fdp = (FingerDatePair) o;
								long sessionStart = this._detail.sessdate;
								long sessEnd = this._detail.duration * 60
										+ sessionStart;
								long magnitude = fdp.getPRINT_TS() / 1000
										- sessionStart;
								if (magnitude < 0)
									magnitude *= -1;

								Long[] TSs = fdp.getTimeStamps();
								long cek1 = fdp.getPRINT_TS();// just
																// initialization
								long lowestNow = fdp.getPRINT_TS();

								boolean earlier = false;
								boolean later = false;
								for (int i = 0; i < TSs.length; i++) {
									cek1 = TSs[i] / 1000 - sessionStart; // later
									earlier = cek1 < 0; // problem Elsa pbo 5a
														// 21
														// desember 2022
									later = TSs[i] / 1000 > sessEnd; // must cek
																		// earlier
																		// and
									// later
									if (cek1 < 0)
										cek1 *= -1;
									if (!earlier && !later && cek1 < magnitude) {
										fdp.removeTimeStamp(TSs[i]); // remove
																		// lowest
										fdp.addTimestamp(fdp.getPRINT_TS()); // add
																				// curent
																				// to
																				// extra
										fdp.setPRINT_TS(TSs[i].longValue());
										magnitude = cek1; // update magnitude
									}
								}

							}

							// System.out.println("initvalues timestamp:" + o);
							return o;
							// return statusId2Description(new Integer((String)
							// o)
							// .intValue());
						} else {
							return "NOT TAKEN";
						}

					default:
					}
				}

			return "NO-IMPL";
		}
	}

	public String statusId2Description(int id) {
		if (statusInfo != null) {
			for (int i = 0; i < statusInfo.length; i++) {
				if (statusInfo[i].id == id)
					return statusInfo[i].description;
			}
		} else {

		}

		return "SUTM: WRONG STATUS id " + id;
	}

	/**
	 * which row has status changed ? can be used to synch to moodle server
	 * 
	 * @return true if changed AND valid....
	 */
	public boolean isStatusChanged(int row) {

		String newStatus = getValueAt(row, COL_STATUS) + "";

		AttendanceStatusInfo[] master = _detail.statuses;

		int newid = AttendanceStatusInfo.descriptionToID(master, newStatus);
		AttendanceStudentInfo info = getRowStatusOldValues(row);
		if (info == null)
			if (newid != -1) {
				return true;
			}
			// not exist and new is valid mean CHANGES
			else {
				return false; // newid not valid
			}

		int old_st_id = info.statusid;

		if (newid != -1)
			if (old_st_id != newid)
				return true; // change mean not same !=
		// System.out.println("row " + row +
		// ", 	sSTC not match returning false");
		System.out.println("row " + row + ", 	sSTC old_id:#" + old_st_id
				+ "#, nwSts description:#" + newStatus + "#");
		return false;
	}

	/**
	 * return null if not known or no cache
	 * 
	 * @return
	 */
	public AttendanceStudentInfo getRowStatusOldValues(int row) {

		String id = getValueAt(row, COL_REMOTEID) + "";
		return studentId2log.get(id);

	}

	public int getSessionId() {
		if (_detail != null)
			return _detail.id;
		return -1;
	}

	public String getStatusset() {
		// TODO Auto-generated method stub
		if (_detail != null)
			return _detail.statusset;
		return null;
	}

	public AttendanceStatusInfo[] getStatusInfos() {
		if (_detail != null)
			return _detail.statuses;
		return null;
	}

	public String getRemotefirstName(int row) {
		String ret = null;

		ret = (String) getValueAt(row, COL_FIRSTNAME);

		return ret;

	}

	public String getRemoteLastname(int row) {
		String ret = null;

		ret = (String) getValueAt(row, COL_LASTNAME);

		return ret;

	}

	public FingerDatePair getFingerPair(int row) {
		FingerDatePair ret = null;

		Object o = getValueAt(row, COL_FINGERDATE);
		if (o instanceof FingerDatePair)
			ret = (FingerDatePair) o;
		return ret;
	}

	Object[] getRowvalues(int row) {
		Object[] ret = null;
		ret = new Object[getColumnCount()];
		for (int i = 0; i < ret.length; i++) {
			ret[i] = getValueAt(row, i);
		}
		return ret;
	}

	public SessionDetail getJSONSessionetail() {
		return _detail;
	}

}
