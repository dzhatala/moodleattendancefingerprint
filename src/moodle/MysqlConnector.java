package moodle;

import java.util.Date;

import util.Logger;
import json.MoodleUser;
import json.SessionDetail;

import com.borland.dx.dataset.TableDataSet;
import com.borland.dx.sql.dataset.ConnectionDescriptor;
import com.borland.dx.sql.dataset.Database;
import com.borland.dx.sql.dataset.Load;
import com.borland.dx.sql.dataset.QueryDataSet;

import cpintar.biometric.zkteco.NotConnectedException;
import cpintar.tts.TTSReader;

//import com.borland.dx.sql.dataset.;

/**
 * handling all communication with mysql database
 * 
 * @author Zulkarnaen Hatala
 * 
 */

public class MysqlConnector {

	private final Database database = new Database();
	/**
	 * @wbp.nonvisual location=69,409
	 */
	private ConnectionDescriptor mySQLDescriptor = new ConnectionDescriptor(
			(String) null);
	private String hostname;
	private String password;
	QueryDataSet qds = new QueryDataSet();

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDbname() {
		return dbname;
	}

	public void setDbname(String dbname) {
		this.dbname = dbname;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Database getDatabase() {
		return database;
	}

	private String dbname;
	private String username;

	public MysqlConnector(String hostname, String username, String password,
			String dbname) {
		this.setHostname(hostname);
		this.username = username;
		this.password = password;
		this.dbname = dbname;
		// this.database=

	}

	public MysqlConnector() {
		// TODO Auto-generated constructor stub
	}

	public String getHostname() {
		return hostname;
	}

	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	public ConnectionDescriptor getMySQLDescriptor() {
		return mySQLDescriptor;
	}

	public void setMySQLDescriptor(ConnectionDescriptor mySQLDescriptor) {
		this.mySQLDescriptor = mySQLDescriptor;
	}

	/**
	 * connect using current password, username, uri or conn. descriptor ...
	 * 
	 * @param remotes
	 *            mdl_user primary key
	 */
	public RemotePair[] get_remote_pairs(long[] remotes) throws Exception {

		database.setConnection(mySQLDescriptor);
		database.openConnection();

		System.out.println("database connect/login succesfull");

		String fp_q = "select id,remote_id, firstname, lastname  from mdl_user ";
		fp_q += " where remote_id in (";
		if (remotes != null) {
			fp_q += remotes[0];

			if (remotes.length > 1) { // more 1 remote is registered already in
										// local
				for (int i = 1; i < remotes.length; i++) {
					fp_q += ", " + remotes[i]; // where id in (67,9)
				}
			}
		}
		fp_q += ")";

		// System.out.println("qds q is:" + fp_q);
		qds.setQuery(new com.borland.dx.sql.dataset.QueryDescriptor(database,
				fp_q, null, true, Load.ALL));
		qds.setReadOnly(true);

		qds.open();
		System.out.println("qds row count :" + qds.getRowCount());// getlongrowcount()
																	// == 0

		RemotePair[] ret = new RemotePair[qds.getRowCount()];

		int cnt = qds.getRowCount();
		qds.first();
		for (int i = 0; i < cnt; i++) {
			ret[i] = new RemotePair();
			ret[i].local = qds.getLong("id");
			ret[i].remote = qds.getLong("remote_id");
			// System.out.println(ret[i]);
			qds.next();
		}

		qds.close();
		database.closeConnection();

		// no pair
		if (cnt > 0)
			return ret;
		return new RemotePair[0];// implicit else

	}

	/**
	 * find in local database the finger print of users with id in locals and
	 * time as specified in sess
	 * 
	 * @param locals
	 *            local id of user
	 * @param sess_starts
	 *            start finger date $param minutesduration finger date =
	 *            sess_starts + minutesduration
	 * 
	 * @return user with his fingerprint or null/FingerDatePair[0] for error or
	 *         no matching
	 */
	public FingerDatePair[] getFingerDatePairs(long[] locals, long sess_starts,
			long secs_duration, long begin_day, long end_day) {
		// sess.
		if (locals.length == 0)
			locals = new long[] { -1 }; // no local found sql query become
										// "select ...  id in (-1) " and no
										// error
		database.setConnection(mySQLDescriptor);
		database.openConnection();

		System.out.println("database connect/login succesfull");
		/*
		 * long sess_end = (sess_starts + secs_duration);
		 * if(hour_late_tolerance<0) hour_late_tolerance=0; sess_end+=
		 * hour_late_tolerance*3600; // System.out.println("sess_start:" +
		 * sess_starts + ", duration: " + secs_duration + ", sess_end:" +
		 * sess_end+" tolerance(hour): "+hour_late_tolerance); long
		 * early_sess_starts=sess_starts-hour_early*3600;
		 */
		long sess_end = end_day;
		long early_sess_start = begin_day;

		String fp_q = "select IDENTIFIED1N.*,UNIX_TIMESTAMP(trialdate) trialdatets,fpinfo.person_id,mdl_user.id,mdl_user.remote_id, "
				+ " mdl_user.username,mdl_user.firstname,   "
				+ " mdl_user.lastname from IDENTIFIED1N, fpinfo,mdl_user    "
				+ " WHERE  ((IDENTIFIED1N.fpid=fpinfo.RIGHT_INDEX)   "
				+ " OR (IDENTIFIED1N.fpid=fpinfo.RIGHT_THUMB)OR   "
				+ " (IDENTIFIED1N.fpid=fpinfo.RIGHT_INDEX) OR   "
				+ " (IDENTIFIED1N.fpid=fpinfo.RIGHT_MIDDLE) OR   "
				+ " (IDENTIFIED1N.fpid=fpinfo.RIGHT_RING) OR	"
				+ " (IDENTIFIED1N.fpid=fpinfo.RIGHT_PINKY) OR   "
				+ " (IDENTIFIED1N.fpid=fpinfo.LEFT_THUMB) OR   "
				+ " (IDENTIFIED1N.fpid=fpinfo.LEFT_INDEX) OR    "
				+ " (IDENTIFIED1N.fpid=fpinfo.LEFT_MIDDLE) OR    "
				+ " (IDENTIFIED1N.fpid=fpinfo.LEFT_RING) OR    "
				+ " (IDENTIFIED1N.fpid=fpinfo.LEFT_PINKY) OR    "
				+ " 0) AND person_id=mdl_user.id    "
				+ " AND  UNIX_TIMESTAMP(trialdate) BETWEEN "
				+ early_sess_start
				+ " AND " + sess_end;
		// + " -- AND regname like \"%progweb%\"   ";
		fp_q += " AND id in (";
		if (locals != null)
			if (locals.length > 0) {
				fp_q += locals[0];

				if (locals.length > 1) { // more 1 remote is registered already
											// in
											// local
					for (int i = 1; i < locals.length; i++) {
						fp_q += ", " + locals[i]; // where id in (67,9)
					}
				}
			}
		fp_q += ")";

		fp_q += " order by trialdate desc; ";

		System.out.println("gFDPs qds q is:" + fp_q);
		qds.setQuery(new com.borland.dx.sql.dataset.QueryDescriptor(database,
				fp_q, null, true, Load.ALL));
		qds.setReadOnly(true);

		qds.open();
		System.out.println("qds row count :" + qds.getRowCount());// getlongrowcount()
																	// == 0

		FingerDatePair[] ret = new FingerDatePair[qds.getRowCount()];

		int cnt = qds.getRowCount();
		qds.first();
		for (int i = 0; i < cnt; i++) {
			ret[i] = new FingerDatePair();
			ret[i].localid = qds.getLong("id");
			ret[i].remoteid = qds.getLong("remote_id");
			// ret[i].timestamp =
			// qds.getTimestamp("trialdate");java.sql.Timestamp BUG

			// String p = "mysqlcntr : ";
			// p += " timestamp :" + ret[i].timestamp;
			// System.out.println(p);

			ret[i].setPRINT_TS(qds.getTimestamp("trialdate").getTime()); // java.sql.Timestamp
																			// BUG
			ret[i].firstName = qds.getString("firstname");
			ret[i].lastName = qds.getString("lastname");
			ret[i].localInfo = qds.getString("regname");

			// System.out.println(ret[i].PRINT_TS);
			qds.next();
		}

		qds.close();
		database.closeConnection();

		// System.out.println("mysqlconnector return hash: " + ret.hashCode());
		return ret;

		// return null;
	}

	/**
	 * release current connection ...
	 */
	public void disconnect() {
		if(qds!=null)
		qds.close();
		database.closeConnection();
		Logger.log("MYSQL: disconnect");
	}

	public void connect() throws Exception {
		database.setConnection(mySQLDescriptor);
		database.openConnection();
		// database.sets
		util.Logger.log("MYSQL: connect succesfull");

	}

	/**
	 * open connection first
	 * 
	 * @param FPID_C
	 *            FPID in
	 * @return
	 * @throws Exception
	 */
	public MoodleUser get_mdl_user(int FPID_C) throws Exception {

		if (FPID_C <= 0)
			return null;
		if (database.isOpen() == false)
			throw new NotConnectedException("Connect() first");
		System.out.println("get_mdl_user" + FPID_C);
		String fp_q = "select fpinfo.*,mdl_user.id,mdl_user.remote_id, "
				+ " mdl_user.username,mdl_user.firstname,   "
				+ " mdl_user.lastname from  fpinfo,mdl_user    " + " WHERE  (("
				+ FPID_C
				+ "=fpinfo.RIGHT_INDEX)   "
				+ " OR ("
				+ FPID_C
				+ "=fpinfo.RIGHT_THUMB)OR   "
				+ " ("
				+ FPID_C
				+ "=fpinfo.RIGHT_INDEX) OR   "
				+ " ("
				+ FPID_C
				+ "=fpinfo.RIGHT_MIDDLE) OR   "
				+ " ("
				+ FPID_C
				+ "=fpinfo.RIGHT_RING) OR	"
				+ " ("
				+ FPID_C
				+ "=fpinfo.RIGHT_PINKY) OR   "
				+ " ("
				+ FPID_C
				+ "=fpinfo.LEFT_THUMB) OR   "
				+ " ("
				+ FPID_C
				+ "=fpinfo.LEFT_INDEX) OR    "
				+ " ("
				+ FPID_C
				+ "=fpinfo.LEFT_MIDDLE) OR    "
				+ " ("
				+ FPID_C
				+ "=fpinfo.LEFT_RING) OR    "
				+ " ("
				+ FPID_C
				+ "=fpinfo.LEFT_PINKY)     "
				+ " )     "
				+ " AND mdl_user.id=fpinfo.person_id     ";
		// + " -- AND regname like \"%progweb%\"   ";

		util.Logger.log(fp_q);
		if (qds.isOpen())
			qds.close();
		qds.setQuery(new com.borland.dx.sql.dataset.QueryDescriptor(database,
				fp_q, null, true, Load.ALL));
		qds.setReadOnly(true);

		qds.open();
		System.out.println("qds row count :" + qds.getRowCount());// getlongrowcount()
																	// == 0

		int cnt = qds.getRowCount();
		if (cnt <= 0)
			return null;
		qds.first();
		MoodleUser ret = new MoodleUser();

		ret.id = qds.getLong("id");
		ret.firstname = qds.getString("firstname");
		ret.lastname = qds.getString("lastname");
		ret.username = qds.getString("username");
		// System.out.println(ret[i]);

		qds.close();

		// no pair
		if (cnt > 0)
			return ret;
		return null;// implicit else

	}

	/**
	 * 
	 * @param FPID
	 *            fpid of mdl_user.id in table FPINFO
	 * @param regname
	 *            "something meaningfull"
	 * @param nowts
	 *            the time of occurence
	 * @return
	 */
	public int insertIdentified1N(int FPID, int score, String regname,
			Date nowts) {

		int ret = -1;
		/*
		 * String fp_q="select * from testborland"; if(qds.isOpen())qds.close();
		 * qds.setQuery(new com.borland.dx.sql.dataset.QueryDescriptor(database,
		 * fp_q, null, true, Load.ALL)); qds.open(); qds.setReadOnly(false);
		 * 
		 * qds.insertRow(false); qds.setString("vc01", new
		 * Date().toGMTString()); qds.saveChanges(); qds.close();
		 */
		if (database.isOpen() == false) {
			// util.Logger("connect mySQL first:");
			return -1;
		}
		String fp_q = "select * from identified1n";
		if (qds.isOpen())
			qds.close();
		qds.setQuery(new com.borland.dx.sql.dataset.QueryDescriptor(database,
				fp_q, null, true, Load.ALL));
		qds.open();
		qds.setReadOnly(false);
		qds.insertRow(false);
		qds.setInt("FPID", FPID);
		// java.sql.Date sd=new java.sql.Date(new Date().getTime());
		qds.setTimestamp("TRIALDATE", nowts.getTime());
		qds.setString("REGNAME", regname);
		qds.setLong("SESSION_ID", -1);// TODO SESSION_ID has no default value
		qds.setInt("SCORE", score);
		// qds.sett
		qds.saveChanges();
		ret = qds.getInt("identified1n_id");
		qds.close();

		return ret;

	}

	private static void testConnector(String args[]) throws Exception {
		// MysqlConnector mc=new MysqlConnector("localhost", "absensi", "root",
		// "");
		MysqlConnector mc = new MysqlConnector();
		ConnectionDescriptor desc = new ConnectionDescriptor(
				"jdbc:mysql://localhost:3306/absensi", "root", "", false,
				"com.mysql.jdbc.Driver"); // TODO profile GUI editor
		mc.setMySQLDescriptor(desc);
		mc.connect();
		MoodleUser user = mc.get_mdl_user(1);
		mc.insertIdentified1N(6, 100, "testing class.main[]", new Date());
		Logger.log(user);
		mc.disconnect();
	}

	public static void main(String args[]) throws Exception {
		testConnector(args);
	}

}
