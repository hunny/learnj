package hh.learnj.database.jdbc.mysql;

import java.sql.SQLException;
import java.sql.Statement;

public interface Connector {
	
	public void handleStatement(Statement statement, String sql) throws SQLException;

}
