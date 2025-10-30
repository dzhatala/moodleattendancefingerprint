package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Date;

import moodle.FingerDatePair;
import util.Logger;
import json.Course;
import json.MoodleUser;
import json.Session;
import json.SessionDetail;

import com.borland.dx.dataset.TableDataSet;
import com.borland.dx.sql.dataset.ConnectionDescriptor;
import com.borland.dx.sql.dataset.Database;
import com.borland.dx.sql.dataset.Load;
import com.borland.dx.sql.dataset.QueryDataSet;

import cpintar.CachedDate;
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
	private String password="";
	private String dbname="absensi_2024";
	private String username="root";
	private String hostname="localhost";
	private ConnectionDescriptor mySQLDescriptor = new ConnectionDescriptor(
			"jdbc:mysql://"+hostname+":3306/"+dbname, username, password, false,
			"com.mysql.jdbc.Driver"); // TODO fix , get from Preferences
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
		
		if(remotes.length<=0)throw new Exception ("Can't get pairs from empty students");

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

		 System.out.println("qds q is:" + fp_q);
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
		if (!database.isOpen()) {
			database.setConnection(mySQLDescriptor);
			database.openConnection();
		}

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
		if (qds != null)
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

	private void _openDB() {
		if (!database.isOpen()) {
			database.setConnection(mySQLDescriptor);
			database.openConnection();
		}
	}

	/**
	 * normalized all date to single date only .. hours,minutes,seconds is set
	 * 0,0,1 '1 sec only after midnight
	 * 
	 * @param remoteid
	 *            teacher remote id/login
	 * @param d
	 * @return
	 */
	public int addTeachersDatesCached(String remoteid, CachedDate d) {

		int ret = -1;

		if (d == null)
			return -1;

		d.setHours(0);
		d.setMinutes(0);
		d.setSeconds(1);

		_openDB();
		if (getTeachersDateCached(d) != null) {
			util.Logger.log("Teachers Date:" + d + "already in cache ");
			return -1;
		}

		String fp_q = "select * from DateCache";
		if (qds.isOpen())
			qds.close();
		qds.setQuery(new com.borland.dx.sql.dataset.QueryDescriptor(database,
				fp_q, null, true, Load.ALL));
		qds.open();
		qds.setReadOnly(false);
		qds.insertRow(false);
		qds.setString("REMOTEID", remoteid);
		qds.setTimestamp("retrievedate", d.getTime());
		qds.setString("coursefullname", d.getCoursefullname());
		qds.saveChanges();
		ret = qds.getInt("id");
		util.Logger.log("Date d:" + d + " cached");
		qds.close();

		return ret;
	}

	public void deleteTeachersDatesCached(String remoteid, Date d)
			throws SQLException {
		// TODO Auto-generated method stub

		_openDB();

		long unix_ts = d.getTime() / 1000;
		String fp_q = "select * from DateCache where UNIX_TIMESTAMP(retrievedate)="
				+ unix_ts;
		util.Logger.log(this, "delete date fp_q : " + fp_q);
		if (qds.isOpen())
			qds.close();
		qds.setQuery(new com.borland.dx.sql.dataset.QueryDescriptor(database,
				fp_q, null, true, Load.ALL));
		qds.open();

		if (qds.rowCount() <= 0)
			return;
		util.Logger.log(this, "delete caches, date count to be deleted : "
				+ qds.rowCount());

		Connection con = DriverManager.getConnection(
				"jdbc:mysql://localhost:3306/" + dbname, "root", "");
		// here sonoo is database name, root is username and password
		Statement stmt = con.createStatement();
		fp_q = "delete from DateCache where UNIX_TIMESTAMP(retrievedate)="
				+ unix_ts;
		// ResultSet rs = stmt.executeQuery(fp_q);
		int result = stmt.executeUpdate(fp_q);
		util.Logger.log("Date d:" + d + " deleted");
		stmt.close();
		con.close();
	}

	/**
	 * getall for this users
	 * 
	 * @return
	 */
	public Date getTeachersDateCached(Date d) {

		// String fp_q = "select * from datecache where retrievedate='" + cd
		// + "' and sessionid=" + id;
		// if (qds.isOpen())
		// qds.close();
		// qds.setQuery(new com.borland.dx.sql.dataset.QueryDescriptor(database,
		// fp_q, null, true, Load.ALL));
		// qds.open();
		// qds.setReadOnly(true);
		// if (qds.rowCount() > 0) {
		// Session ret = new Session();
		// ret.id = qds.getLong("sessionid");
		// ret.sessdate = qds.getTimestamp("sessiondate").getTime();
		// // ret.detail = new SessionDetail();
		// // ret.detail.sessdate = qds.getLong("sessdate")/1000;
		// return ret;
		// }

		return null;

	}

	public CachedDate[] getTeachersDatesCached(String remoteid) {
		_openDB();
		CachedDate[] ret = null;
		String fp_q = "select distinct id,retrievedate,coursefullname from datecache where remoteid='"
				+ remoteid + "' " + "group by retrievedate";
		util.Logger.log("getchache fp_q:" + fp_q);
		if (qds.isOpen())
			qds.close();
		qds.setQuery(new com.borland.dx.sql.dataset.QueryDescriptor(database,
				fp_q, null, true, Load.ALL));
		qds.open();
		qds.setReadOnly(true);
		int rc = qds.rowCount();
		if (rc > 0) {
			ret = new CachedDate[rc];
			qds.first();
			for (int iq = 0; iq < rc; iq++) {
				CachedDate d = new CachedDate();

				Timestamp ts = qds.getTimestamp("retrievedate");
				d.setTime(ts.getTime());
				d.setCoursefullname(qds.getString("coursefullname"));
				ret[iq] = d;
				qds.next();
			}
			return ret;
		}
		return null;
	}

	/**
	 * add session to 2 database ?? session timestamp conversion ?
	 * moodle_timestamp = java.sql.timestamp/1000
	 * 
	 * @param d
	 * 
	 */
	public int addTeachersSessionCache(String remoteid, Session s) {

		int ret = -1;
		/*
		 * String fp_q="select * from testborland"; if(qds.isOpen())qds.close();
		 * qds.setQuery(new com.borland.dx.sql.dataset.QueryDescriptor(database,
		 * fp_q, null, true, Load.ALL)); qds.open(); qds.setReadOnly(false);
		 * 
		 * qds.insertRow(false); qds.setString("vc01", new
		 * Date().toGMTString()); qds.saveChanges(); qds.close();
		 */
		_openDB();
		if (TeachersgetCachedSession(remoteid, s.id) != null) {
			util.Logger.log("Sessid:" + s.id + "already in cached for "
					+ remoteid);
			return -1;
		}

		String fp_q = "select * from sessioncache";
		if (qds.isOpen())
			qds.close();
		qds.setQuery(new com.borland.dx.sql.dataset.QueryDescriptor(database,
				fp_q, null, true, Load.ALL));
		qds.open();
		qds.setReadOnly(false);
		qds.insertRow(false);
		qds.setLong("sessionid", s.id);
		qds.setString("REMOTEID", remoteid);
		qds.setTimestamp("sessiondate", s.sessdate * 1000);
		qds.saveChanges();
		ret = qds.getInt("id");
		util.Logger.log("Session:" + s.id + " cached");
		qds.close();

		return ret;
	}

	/**
	 * getall for this users
	 * 
	 * @return
	 */
	public Session TeachersgetCachedSession(String remoteid, long id) {

		String fp_q = "select * from sessioncache where remoteid='" + remoteid
				+ "' and sessionid=" + id;
		if (qds.isOpen())
			qds.close();
		qds.setQuery(new com.borland.dx.sql.dataset.QueryDescriptor(database,
				fp_q, null, true, Load.ALL));
		qds.open();
		qds.setReadOnly(true);
		if (qds.rowCount() > 0) {
			Session ret = new Session();
			ret.id = qds.getLong("sessionid");
			ret.sessdate = qds.getTimestamp("sessiondate").getTime();
			// ret.detail = new SessionDetail();
			// ret.detail.sessdate = qds.getLong("sessdate")/1000;
			return ret;
		}

		return null;

	}

	public Session[] loadSessionCaches(String remoteid) {
		// TODO Auto-generated method stub
		util.Logger.log(this, "load caches for " + remoteid);
		String fp_q = "select * from sessioncache where remoteid='" + remoteid
				+ "'";
		_openDB();
		if (qds.isOpen())
			qds.close();
		qds.setQuery(new com.borland.dx.sql.dataset.QueryDescriptor(database,
				fp_q, null, true, Load.ALL));
		qds.open();
		qds.setReadOnly(true);
		int rcount = qds.rowCount();
		if (rcount < 0)
			return null;
		qds.first();
		int row = 0;
		Session[] retS = new Session[rcount];
		while (row < rcount) {
			Session s = new Session();
			s.id = qds.getLong("sessionid");
			s.sessdate = qds.getTimestamp("sessiondate").getTime() / 1000;
			retS[row] = s;
			row++;

		}
		return retS;
	}

	/**
	 * remove from db
	 * 
	 * @param d
	 */
	/*
	 * public void TeachersDateCache_removeSession(long id) {
	 * 
	 * }
	 */
	public static void main(String args[]) throws Exception {

		MysqlConnector mc = new MysqlConnector();
		ConnectionDescriptor desc = new ConnectionDescriptor(
				"jdbc:mysql://localhost:3306/absensi", "root", "", false,
				"com.mysql.jdbc.Driver"); // TODO profile GUI editor
		mc.setMySQLDescriptor(desc);
		mc.connect();
		/*
		 * Session sess = new Session(); sess.id = 1;
		 * mc.addTeachersSessionCache("007", sess); sess.id = 2;
		 * mc.addTeachersSessionCache("007", sess);
		 */
		// testConnector(args);
		mc.addTeachersDatesCached("007", new CachedDate());

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

}
