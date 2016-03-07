package hh.learnj.testj.db.translate;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import hh.learnj.httpclient.usecase.googletranslate.HttpClientGoogleTranslate;

public class DataTranslate {

	// JDBC driver name and database URL
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://192.168.9.243:3306/cubi-life";
	static final String TRANSLATE_URL = "https://translate.google.com/translate_a/single?client=t&sl=zh-CN&tl=en&hl=en&dt=bd&dt=ex&dt=ld&dt=md&dt=qca&dt=rw&dt=rm&dt=ss&dt=t&dt=at&ie=UTF-8&oe=UTF-8&otf=2&srcrom=1&ssel=4&tsel=0&kc=2&tco=2&tk=866774.725188&q=";

	// Database credentials
	static final String USER = "root";
	static final String PASS = "123456";

	public static void main(String[] args) {
		Connection conn = null;
		Statement stmt = null;
		HttpClientGoogleTranslate google = new HttpClientGoogleTranslate();
		try {
			// STEP 2: Register JDBC driver
			Class.forName(JDBC_DRIVER);

			// STEP 3: Open a connection
			System.out.println("Connecting to database...");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);

			// STEP 4: Execute a query
			System.out.println("Creating statement...");
			stmt = conn.createStatement();
			String sql = "select * from shopnc_goods_class limit 10";
			ResultSet rs = stmt.executeQuery(sql);

			// STEP 5: Extract data from result set
			while (rs.next()) {
				// Retrieve by column name
				int id = rs.getInt("gc_id");
				String name = rs.getString("gc_name");

				// Display values
				System.out.print("ID: " + id);
				System.out.print(", name: " + name);
				System.out.println();
				
//				Statement tmp = conn.createStatement();
//				ResultSet mResult = tmp.executeQuery("select * from shopnc_i18n where table_name = 'goods_class' and column_name = 'gc_name' and locale = 'en-US' and ref_id = " + id);
//				if (!mResult.next()) {
					String result = google.googleTranslate(TRANSLATE_URL + URLEncoder.encode(name, "UTF-8"), true);
					if (null != result) {
						System.out.println("[1.][+]" + result);
						String tmps = result.split(",")[0];
						System.out.println("[2.][+]" + tmps);
						if (null != tmps) {
							tmps = tmps.replaceFirst("[[[\"", "");
							System.out.println("[3.][+]" + tmps);
							tmps = tmps.replaceFirst("\"$", "");
							System.out.println("[4.][+]" + tmps);
						}
					} else {
						System.out.println(name + " request error.");
					}
//				} else {
//					System.out.println(id + " existed.");
//				}
//				// STEP 6: Clean-up environment
//				mResult.close();
//				tmp.close();
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
