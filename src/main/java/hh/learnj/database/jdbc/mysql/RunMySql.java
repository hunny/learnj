package hh.learnj.database.jdbc.mysql;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

public class RunMySql {
	
	// JDBC driver name and database URL
	private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	private static final String DB_ADDRESS = "localhost";
	private static final String DB_PORT = "3306";
	private static final String DB_USERNAME = "root";
	private static final String DB_PASSWORD = "root";
	private static final String DB_URL = "jdbc:mysql://" + DB_ADDRESS + ":" + DB_PORT + "?useUnicode=yes&characterEncoding=utf-8&useSSL=no";

	ExecutorService executors = Executors.newFixedThreadPool(20);
	final CompletionService<Long> completionService = new ExecutorCompletionService<Long>(executors);
	
	@Test
	public void testCreateIndex() throws InterruptedException, ExecutionException {
		final StringBuilder sql = new StringBuilder();
		sql.append(" CREATE INDEX idx_2 ON db{db}.MyTabel (column1, column2, column3);");
		
		for (int i = 0; i < 20; i ++) {
			final Integer index = i;
			completionService.submit(new Callable<Long>() {
				@Override
				public Long call() throws Exception {
					long start = System.nanoTime();
					dbQuery(new Connector() {
						@Override
						public void handleStatement(Statement statement, String sql) throws SQLException {
							try {
								String [] arr = sql.split(";");
								for (String mSql : arr) {
									if (StringUtils.isBlank(mSql)) {
										continue;
									}
									System.out.println("[+][E][SQL]" + mSql);
									boolean rs = statement.execute(mSql);
									System.err.print("结果：" + rs);
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}, sql.toString().replaceAll("\\{db\\}", String.valueOf(index)));
					long end = System.nanoTime() - start;
					System.out.println(index + "耗时：" + end + "ns, " + (end / 1000000) + "ms");
					return end;
				}
			});
		}
		BigDecimal total = BigDecimal.ZERO;
		int count = 10;
		for (int i = 0; i < count; i++) {
			total = total.add(BigDecimal.valueOf(completionService.take().get()));
		}
		System.err.println("平均时间：" + total.divide(BigDecimal.valueOf(count * 1000000)) + " ms");
		executors.shutdown();
	}
	
	@Test
	public void testQuery() throws InterruptedException, ExecutionException {
		final StringBuilder sql = new StringBuilder();
		sql.append("SELECT SUM(p.column1) AS amount, p.column2");
		sql.append(" FROM db{db}.MyTableP p INNER JOIN db{db}.MyTableS s ON s.id = p.id");
		sql.append(" WHERE s.id = (select id from db{db}.MyTableS limit 1) ");
		sql.append(" AND s.date_created >= '2016-07-01'");
		sql.append(" AND s.date_created < '2016-08-07'");
		sql.append(" AND p.column1 <> 0");
		sql.append(" GROUP BY p.column2 ");
		
		for (int i = 0; i < 20; i ++) {
			final Integer index = i;
			completionService.submit(new Callable<Long>() {
				@Override
				public Long call() throws Exception {
					long start = System.nanoTime();
					dbQuery(new Connector() {
						@Override
						public void handleStatement(Statement statement, String sql) throws SQLException {
							ResultSet rs = statement.executeQuery(sql);
							while (rs.next()) {
								// Retrieve by column name
								BigDecimal amount = rs.getBigDecimal("amount");
								String column2 = rs.getString("column2");
								// Display values
								System.out.println("amount: " + amount + ", name: " + column2);
							}
							rs.close();
						}
					}, sql.toString().replaceAll("\\{db\\}", String.valueOf(index)));
					long end = System.nanoTime() - start;
					System.out.println(index + "耗时：" + end + "ns," + (end / 1000000) + "ms");
					return end;
				}
			});
		}
		
		BigDecimal total = BigDecimal.ZERO;
		int count = 10;
		for (int i = 0; i < count; i++) {
			total = total.add(BigDecimal.valueOf(completionService.take().get()));
		}
		System.err.println("平均时间：" + total.divide(BigDecimal.valueOf(count * 1000000)) + " ms");
		executors.shutdown();
	}
	
	public void dbQuery(Connector connector, String sql) {
		Connection conn = null;
		Statement stmt = null;
		try {
			// STEP 2: Register JDBC driver
			Class.forName(JDBC_DRIVER);

			// STEP 3: Open a connection
			System.out.println(Thread.currentThread().getName() + " Connecting to database...");
			conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);

			// STEP 4: Execute a query
			System.out.println(Thread.currentThread().getName() + " Creating statement...");
			stmt = conn.createStatement();
//			ResultSet rs = stmt.executeQuery(connector.query());
			connector.handleStatement(stmt, sql);
			// STEP 6: Clean-up environment
//			rs.close();
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
		System.out.println(Thread.currentThread().getName() + " Goodbye!");
	}
	
}
