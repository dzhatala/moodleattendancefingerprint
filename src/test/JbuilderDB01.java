package test;

import com.borland.dx.dataset.*;
import com.borland.dx.sql.dataset.*;

import java.sql.*;

public class JbuilderDB01 {
	public static void main(String[] args) throws DataSetException, ClassNotFoundException {
		System.out.println("JbuilderDB01");
		Class.forName("com.mysql.jdbc.Driver");
		Database db1 = new Database();
		/*db1.setConnection(new ConnectionDescriptor(
				"jdbc:mysql://localhost:3306/absensi",
				"root", "wrong_pwd", false, "com.mysql.jdbc.Driver"));*/
		
		db1.setConnection(new ConnectionDescriptor(
				"jdbc:mysql://localhost:3306/absensi",
				"root", "", false, "com.mysql.jdbc.Driver"));
		
		QueryDataSet qds = new QueryDataSet();
		qds.setQuery(new com.borland.dx.sql.dataset.QueryDescriptor(db1, "SELECT* FROM THETABLE", null, true, Load.ALL));
		db1.openConnection();
		System.out.println("login succesfull");
		db1.closeConnection();
		/*try {
			database1.executeStatement("DROP PROCEDURE GET_COUNTRIES");
		} catch (Exception ex) {
		}
		;
		try {
			database1.executeStatement("DROP PROCEDURE UPDATE_COUNTRY");
		} catch (Exception ex) {
		}
		;
		try {
			database1.executeStatement("DROP PROCEDURE INSERT_COUNTRY");
		} catch (Exception ex) {
		}
		;
		try {
			database1.executeStatement("DROP PROCEDURE DELETE_COUNTRY");
		} catch (Exception ex) {
		}
		;
		database1.executeStatement(getCountriesProc);
		database1.executeStatement(updateProc);
		database1.executeStatement(deleteProc);
		database1.executeStatement(insertProc);
		database1.closeConnection();*/
	}

	static final String getCountriesProc = "CREATE PROCEDURE GET_COUNTRIES RETURNS ( /r/n"
			+ " COUNTRY VARCHAR(15), /r/n"
			+ " CURRENCY VARCHAR(10) ) AS /r/n"
			+ "BEGIN /r/n"
			+ " FOR SELECT c.country, c.currency /r/n"
			+ " FROM country c /r/n"
			+ " INTO :COUNTRY,:CURRENCY /r/n"
			+ " DO /r/n"
			+ " BEGIN /r/n"
			+ " SUSPEND; /r/n"
			+ " END /r/n"
			+ "END;";
	static final String updateProc = "CREATE PROCEDURE UPDATE_COUNTRY( /r/n"
			+ " OLD_COUNTRY VARCHAR(15), /r/n"
			+ " NEW_COUNTRY VARCHAR(15), /r/n"
			+ " NEW_CURRENCY VARCHAR(20) ) AS /r/n" + "BEGIN /r/n"
			+ " UPDATE country /r/n" + " SET country = :NEW_COUNTRY /r/n"
			+ " WHERE country = :OLD_COUNTRY; /r/n" + "END;";
	static final String insertProc = "CREATE PROCEDURE INSERT_COUNTRY( /r/n"
			+ " NEW_COUNTRY VARCHAR(15), /r/n"
			+ " NEW_CURRENCY VARCHAR(20) ) AS /r/n" + "BEGIN /r/n"
			+ " INSERT INTO country(country,currency) /r/n"
			+ " VALUES (:NEW_COUNTRY,:NEW_CURRENCY); /r/n" + "END;";
	static final String deleteProc = "CREATE PROCEDURE DELETE_COUNTRY( /r/n"
			+ " OLD_COUNTRY VARCHAR(15) ) AS /r/n" + "BEGIN /r/n"
			+ " DELETE FROM country /r/n"
			+ " WHERE country = :OLD_COUNTRY; /r/n" + "END;";

}