package test;

import java.sql.*;

class JDBC01 {
	public static void main(String args[]) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/absensi", "root", "");
			// here sonoo is database name, root is username and password
			Statement stmt = con.createStatement();
			String query="select IDENTIFIED1N.*,fpinfo.person_id,mdl_user.username,mdl_user.username,"
					+ "mdl_user.firstname,mdl_user.lastname from IDENTIFIED1N, fpinfo,mdl_user where "
					+ "((IDENTIFIED1N.fpid=fpinfo.RIGHT_INDEX) OR "+
" (IDENTIFIED1N.fpid=fpinfo.RIGHT_THUMB)OR "+
" (IDENTIFIED1N.fpid=fpinfo.RIGHT_INDEX)OR"+
" (IDENTIFIED1N.fpid=fpinfo.RIGHT_MIDDLE)OR   "+
" (IDENTIFIED1N.fpid=fpinfo.RIGHT_RING)OR"+
" (IDENTIFIED1N.fpid=fpinfo.RIGHT_PINKY)OR"+
" (IDENTIFIED1N.fpid=fpinfo.LEFT_THUMB)OR   "+
" (IDENTIFIED1N.fpid=fpinfo.LEFT_INDEX)OR   "+
" (IDENTIFIED1N.fpid=fpinfo.LEFT_MIDDLE)OR   "+
" (IDENTIFIED1N.fpid=fpinfo.LEFT_RING)OR   "+
" (IDENTIFIED1N.fpid=fpinfo.LEFT_PINKY)OR   "+
" 0) AND person_id=mdl_user.id  order by trialdate desc;" ;
			//ResultSet rs = stmt.executeQuery("select * from identified1n");
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next())
				System.out.println(rs.getInt(1) + "  " + rs.getString(2) + "  "
						+ rs.getString(3));
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}