package hh.learnj.testj.pin.code.sqlite;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

public class JdbcSqliteUtil {

	public static final String REAVER_WASH = "REAVER_WASH";
	public static final String REAVER_CRACKED = "REAVER_CRACKED";
	
	private static Logger logger = Logger.getLogger(JdbcSqliteUtil.class);

	// STEP 1: JDBC driver name and database URL
	static final String JDBC_DRIVER = "org.sqlite.JDBC";
	static final String DB_URL = "jdbc:sqlite:";

	public static void main(String[] args) {
		JdbcSqliteUtil jdbc = new JdbcSqliteUtil();
		List<Map<String, Object>> list = jdbc
				.query("select * from sqlite_master");
		for (Map<String, Object> map : list) {
			System.out.println("====>" + map);
		}
		logger.info(jdbc.createTabalReaverWash());
		logger.info(jdbc.createTabalReaverCracked());
//		Map<String, Object> values = new HashMap<String, Object>();
//		values.put("BSSID", "D0:C7:C0:C8:00:CE");
//		values.put("ESSID", "FAST_00CE");
//		jdbc.save("REAVER_WASH", values);
//		Map<String, Object> wheres = new HashMap<String, Object>();
//		wheres.put("ID", 1);
//		jdbc.update("REAVER_WASH", values, wheres);
	}

	public String createTabalReaverWash() {
		StringBuffer buffer = new StringBuffer();
		buffer.append(" CREATE TABLE ");
		buffer.append(" ");
		buffer.append(REAVER_WASH);
		buffer.append(" ");
		buffer.append(" ( ");
		buffer.append(" ID integer primary key autoincrement");
		buffer.append(" ,");
		buffer.append(" BSSID varchar(20) default null");
		buffer.append(" ,");
		buffer.append(" ESSID varchar(50) default null");
		buffer.append(" ,");
		buffer.append(" PIN varchar(8) default null");
		buffer.append(" ,");
		buffer.append(" COLTD varchar(50) default null");
		buffer.append(" ,");
		buffer.append(" DATE_CREATED timestamp default null");
		buffer.append(" ,");
		buffer.append(" LAST_UPDATED timestamp default null");
		buffer.append(" )");
		return buffer.toString();
	}
	
	public String createTabalReaverCracked() {
		StringBuffer buffer = new StringBuffer();
		buffer.append(" CREATE TABLE ");
		buffer.append(" ");
		buffer.append(REAVER_CRACKED);
		buffer.append(" ");
		buffer.append(" ( ");
		buffer.append(" ID integer primary key autoincrement");
		buffer.append(" ,");
		buffer.append(" BSSID varchar(20) default null");
		buffer.append(" ,");
		buffer.append(" ESSID varchar(50) default null");
		buffer.append(" ,");
		buffer.append(" PIN varchar(8) default null");
		buffer.append(" ,");
		buffer.append(" PASSWORD varchar(50) default null");
		buffer.append(" ,");
		buffer.append(" COLTD varchar(50) default null");
		buffer.append(" ,");
		buffer.append(" CHANNEL varchar(2) default null");
		buffer.append(" ,");
		buffer.append(" RSSI varchar(3) default null");
		buffer.append(" ,");
		buffer.append(" WPS_VERSION varchar(10) default null");
		buffer.append(" ,");
		buffer.append(" WPS_LOCKED varchar(10) default null");
		buffer.append(" ,");
		buffer.append(" REMARK varchar(200) default null");
		buffer.append(" ,");
		buffer.append(" DATE_CREATED timestamp default null");
		buffer.append(" ,");
		buffer.append(" LAST_UPDATED timestamp default null");
		buffer.append(" )");
		return buffer.toString();
	}

	/**
	 * Original Implements.
	 * 
	 * @return
	 */
	protected Object exeSqliteMaster() {
		return createStatement(new SqliteConnect<Object>() {
			@Override
			public Object doConnect(Connection connect) {
				String sql = "select * from sqlite_master";
				try {
					Statement statement = connect.createStatement();
					ResultSet rs = statement.executeQuery(sql);
					// Extract data from result set
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
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				return null;
			}
		});
	}
	
	public Integer saveToReaverCracked(final Map<String, Object> values) {
		return save(REAVER_CRACKED, values);
	}
	
	public Integer updateToReaverCracked(final Map<String, Object> values, final Map<String, Object> wheres) {
		return update(REAVER_CRACKED, values, wheres);
	}
	
	public Integer saveToWashReaver(final Map<String, Object> values) {
		return save(REAVER_WASH, values);
	}
	
	public Integer updateToWashReaver(final Map<String, Object> values, final Map<String, Object> wheres) {
		return update(REAVER_WASH, values, wheres);
	}
	
	public Integer update(final String table, final Map<String, Object> values, final Map<String, Object> wheres) {
		values.put("LAST_UPDATED", new Date());
		return executeUpdate(table, values, new SqliteUpdate() {

			protected String sql = null;
			protected List<Object> list = null;
			private void init() {
				if (null == list) {
					list = new ArrayList<Object>();
				}
				StringBuffer update = new StringBuffer();
				update.append(" update ");
				update.append(table);
				update.append(" set ");
				for (Map.Entry<String, Object> m : values.entrySet()) {
					update.append(m.getKey());
					update.append(" = ");
					update.append("?,");
					list.add(m.getValue());
				}
				update.append("where 1 = 1");
				for (Map.Entry<String, Object> m : wheres.entrySet()) {
					update.append(" and ");
					update.append(m.getKey());
					update.append(" = ? ");
					list.add(m.getValue());
				}
				sql = update.toString().replaceAll(",where", " where");
			}
			
			@Override
			public String getSql() {
				if (null == sql) {
					init();
				}
				return sql;
			}
			
			@Override
			public List<Object> getList() {
				if (null == list) {
					init();
				}
				return list;
			}
		});
	}
	
	public Integer save(final String table, final Map<String, Object> values) {
		values.put("DATE_CREATED", new Date());
		return executeUpdate(table, values, new SqliteUpdate() {
			protected String sql = null;
			protected List<Object> list = null;
			private void init() {
				if (null == list) {
					list = new ArrayList<Object>();
				}
				StringBuffer insert = new StringBuffer();
				insert.append(" insert into ");
				insert.append(table);
				insert.append(" (");
				StringBuffer value = new StringBuffer();
				value.append(" values (");
				for (Map.Entry<String, Object> m : values.entrySet()) {
					insert.append(m.getKey());
					insert.append(",");
					value.append("?,");
					list.add(m.getValue());
				}
				insert.append(") ");
				value.append(") ");
				insert.append(value);
				sql = insert.toString().replaceAll(",\\)", ")");
			}
			@Override
			public String getSql() {
				if (null == sql) {
					init();
				}
				return sql;
			}
			@Override
			public List<Object> getList() {
				if (null == list) {
					init();
				}
				return list;
			}
		});
	}

	public Integer executeUpdate(final String table,
			final Map<String, Object> values, final SqliteUpdate sqliteUpdate) {
		return createStatement(new SqliteConnect<Integer>() {
			@Override
			public Integer doConnect(Connection connect) {
				PreparedStatement statement = null;
				Integer result = 0;
				try {
					String sql = sqliteUpdate.getSql();
					List<Object> list = sqliteUpdate.getList();
					logger.debug(sql);
					statement = connect.prepareStatement(sql);
					for (int i = 1; i <= list.size(); i++) {
						statement.setObject(i, list.get(i - 1));
					}
					result = statement.executeUpdate();
				} catch (SQLException e) {
					e.printStackTrace();
				} finally {
					try {
						if (statement != null) {
							statement.close();
						}
					} catch (SQLException se2) {
					}// nothing we can do
				}
				return result;
			}
		});
	}

	public List<Map<String, Object>> query(final String sql) {
		return createStatement(new SqliteConnect<List<Map<String, Object>>>() {
			@Override
			public List<Map<String, Object>> doConnect(Connection connect) {
				List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
				Statement statement = null;
				try {
					statement = connect.createStatement();
					ResultSet rs = statement.executeQuery(sql);
					statement.setMaxRows(50000);
					ResultSetMetaData meta = rs.getMetaData();
					List<String> columns = new ArrayList<String>();
					for (int i = 1; i <= meta.getColumnCount(); i++) {
						columns.add(meta.getColumnName(i));
					}
					// Extract data from result set
					while (rs.next()) {
						Map<String, Object> map = new HashMap<String, Object>();
						for (String column : columns) {
							Object o = rs.getObject(column);
							if (null != o) {
								map.put(column, o);
							}
						}
						if (!map.isEmpty()) {
							result.add(map);
						}
					}
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				} finally {
					try {
						if (statement != null) {
							statement.close();
						}
					} catch (SQLException se2) {
					}// nothing we can do
				}
				return result;
			}
		});
	}

	protected <T> T createStatement(SqliteConnect<T> statement) {
		String path = JdbcSqliteUtil.class.getResource("/")
				.getPath().replaceFirst("target/classes/", "readme/sqlite.db");
			path = "/Users/hunnyhu/work/workspace/database/wifi-crack/sqlite.db";
		return createStatement(statement, path);
	}

	protected <T> T createStatement(SqliteConnect<T> connection, String path) {
		Connection conn = null;
		T result = null;
		try {
			// STEP 2: Register JDBC driver
//			System.out.println("[+] Register JDBC driver " + JDBC_DRIVER);
			Class.forName(JDBC_DRIVER);

			// STEP 3: Open a connection
//			System.out.println("[+] Connecting to database " + path);
			conn = DriverManager.getConnection(DB_URL + path);

			// STEP 4: Execute a query
//			System.out.println("[+] Creating statement...");

			// STEP 5: Execute statement
			result = connection.doConnect(conn);

			// STEP 6: Clean-up environment
//			System.out.println("[+] Clean-up environment...");

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
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}// end finally try
		}// end try
//		System.out.println("[+] Goodbye!");
		return result;
	}

}
