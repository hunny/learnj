/**
 * 
 */
package hh.learnj.httpclient.usecase.www51jobcom;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author huzexiong
 *
 */
public class DB {
	private static final Logger logger = LoggerFactory.getLogger(DB.class);

	// JDBC driver name and database URL
	public static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	public static final String DB_URL = "jdbc:mysql://localhost:3306/w51job?useUnicode=yes&characterEncoding=utf-8&useSSL=no";

	// Database credentials
	public static final String USER = "dfm";
	public static final String PASS = "dfm";

	public static void handle(List<Map<String, String>> values, Hander handler) {
		if (null == handler) {
			return;
		}
		Connection conn = null;
		Statement stmt = null;
		try {
			// STEP 2: Register JDBC driver
			Class.forName(JDBC_DRIVER);

			// STEP 3: Open a connection
			logger.info("Connecting to database...");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);

			// STEP 4: Execute a query
			logger.info("Creating statement...");
			stmt = conn.createStatement();

			logger.info("Handle business...");
			// STEP 5: Business
			handler.handle(values, stmt);

			logger.info("Clean-up environment...");
			// STEP 6: Clean-up environment
			stmt.close();
			conn.close();
		} catch (Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
		} finally {
			// finally block used to close resources
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException se2) {
			} // nothing we can do
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			} // end finally try
		} // end try
		logger.info("Goodbye!");
	}

	public interface Hander {

		String getTableName();

		void handle(List<Map<String, String>> values, Statement stmt) throws SQLException;
	}

	public static void insert(String table, Map<String, String> insert, Statement stmt) {
		StringBuilder builder = new StringBuilder();
		builder.append("INSERT INTO ");
		builder.append(table);
		builder.append(" (");
		List<String> values = new ArrayList<String>();
		for (Map.Entry<String, String> map : insert.entrySet()) {
			builder.append(map.getKey());
			builder.append(", ");
			values.add("'" + map.getValue().replaceAll("'", "''") + "'");
		}
		builder.append(") ");
		builder.append("VALUES (");
		builder.append(StringUtils.join(values.toArray(new String[values.size()]), ","));
		builder.append(")");
		String sql = builder.toString().replaceAll(",\\s*\\)", ")");
		logger.info(sql);
		try {
			stmt.execute(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
