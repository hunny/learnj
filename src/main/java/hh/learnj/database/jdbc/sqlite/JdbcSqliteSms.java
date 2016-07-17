package hh.learnj.database.jdbc.sqlite;

import hh.learnj.testj.xml.SmsInfo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class JdbcSqliteSms {

	// JDBC driver name and database URL
	static final String JDBC_DRIVER = "org.sqlite.JDBC";
	static final String DB_URL = "jdbc:sqlite:/Users/hunnyhu/Desktop/sms/mmssms.db";

	public List<SmsInfo> getSmsInfo() throws Exception {
		List<SmsInfo> list = new ArrayList<SmsInfo>();
		Connection conn = null;
		Statement stmt = null;
		try {
			// STEP 2: Register JDBC driver
			Class.forName(JDBC_DRIVER);

			// STEP 3: Open a connection
			System.out.println("Connecting to database...");
			conn = DriverManager.getConnection(DB_URL);

			// STEP 4: Execute a query
			System.out.println("Creating statement...");
			stmt = conn.createStatement();
			String sql = "select * from sms order by date desc";
			ResultSet rs = stmt.executeQuery(sql);
//			ResultSetMetaData metaData = rs.getMetaData();
//			List<String> columns = new ArrayList<String>();
//			for (int i = 0; i < metaData.getColumnCount(); i++) {
//				columns.add(metaData.getColumnName(i + 1));
//			}
//			for (int i = 0; i < columns.size(); i++) {
//				String columnName = columns.get(i);
//				sql = "select count(" + columnName + ") from sms group by " + columnName;
//				System.out.println("[" + sql + "]");
//				rs = stmt.executeQuery(sql);
//				while (rs.next()) {
//					System.out.print("[" + columnName + "][");
//					System.out.println(rs.getLong(1) + "]");
//				}
//			}
			// STEP 5: Extract data from result set
			while (rs.next()) {
				// Retrieve by column name
				String address = rs.getString("address");
				String date = rs.getString("date");
				String type = rs.getString("type");
				String body = rs.getString("body");

				// Display values
//				System.out.print("[" + address);
//				System.out.print("], [" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(Long.parseLong(date))));
//				System.out.print("], [" + type);
//				System.out.print("], [" + body + "]");
//				System.out.println();
				SmsInfo smsInfo = new SmsInfo(address, date, type, body);
				list.add(smsInfo);
			}
			// STEP 6: Clean-up environment
			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
		} catch (Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
		} finally {
			// finally block used to close resources
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException se2) {
			}// nothing we can do
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}// end finally try
		}// end try
		System.out.println("Database Goodbye!");
		return list;
	}

}
