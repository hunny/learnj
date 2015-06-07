package hh.learnj.database.jdbc.sqlite;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JdbcSqliteGuide {

	// JDBC driver name and database URL
	static final String JDBC_DRIVER = "org.sqlite.JDBC";
	static final String DB_URL = "jdbc:sqlite:/Users/hunnyhu/Desktop/sqlite.db";

	public static void main(String[] args) {
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
			String sql = "select * from sqlite_master";
			ResultSet rs = stmt.executeQuery(sql);

			// STEP 5: Extract data from result set
			while (rs.next()) {
				// Retrieve by column name
				String type = rs.getString("type");
				String name = rs.getString("name");
				String tbl_name = rs.getString("tbl_name");
				String ssql = rs.getString("sql");

				// Display values
				System.out.print("type: " + type);
				System.out.print(", name: " + name);
				System.out.print(", tbl_name: " + tbl_name);
				System.out.print(", ssql: " + ssql);
				System.out.println();
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
		System.out.println("Goodbye!");
	}

}
