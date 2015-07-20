package hh.learnj.httpclient.usecase.googleips;

import hh.learnj.testj.pin.code.sqlite.JdbcSqliteUtil;
import hh.learnj.testj.pin.code.sqlite.SqliteConnect;

import java.util.Map;

/**
 * 取Google的ip数据
 * @author Hunny.Hu
 */
public class GoogleIpsJdbcSqliteUtil extends JdbcSqliteUtil {
	
	public static final String GOOGLE_IPS = "GOOGLE_IPS";
	
	protected <T> T createStatement(SqliteConnect<T> statement) {
		String path = JdbcSqliteUtil.class.getResource("/")
				.getPath().replaceFirst("target/classes/", "readme/sqlite.db");
		path = "D:/code/testg/sqlite.db";
		return createStatement(statement, path);
	}
	
	public Integer saveToGoogleIps(final Map<String, Object> values) {
		return save(GOOGLE_IPS, values);
	}
	
	public Integer updateToGoogleIps(final Map<String, Object> values, final Map<String, Object> wheres) {
		return update(GOOGLE_IPS, values, wheres);
	}
	
	public String createTabalGoogleIps() {
		StringBuffer buffer = new StringBuffer();
		buffer.append(" CREATE TABLE ");
		buffer.append(" ");
		buffer.append(GOOGLE_IPS);
		buffer.append(" ");
		buffer.append(" ( ");
		buffer.append(" ID integer primary key autoincrement");
		buffer.append(" ,");
		buffer.append(" AREA varchar(50) default null");
		buffer.append(" ,");
		buffer.append(" IP varchar(20) default null");
		buffer.append(" ,");
		buffer.append(" DATE_CREATED timestamp default null");
		buffer.append(" ,");
		buffer.append(" LAST_UPDATED timestamp default null");
		buffer.append(" )");
		return buffer.toString();
	}

}
