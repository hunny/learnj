package hh.learnj.testj.db.basic;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Types;

public class DTestProc {

	public static void main(String[] args) {
		Connection connection = null;
		CallableStatement statement = null;
		try {
			//step 1
			Class.forName("com.mysql.jdbc.Driver");
			//step 2
			connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:4306/test", "root", "mysqlpasswd");
			//step 3
			statement = connection.prepareCall("(call p(?, ?, ?, ?))");
			//step 4
			statement.registerOutParameter(3,  Types.INTEGER);
			statement.registerOutParameter(4,  Types.INTEGER);
			statement.setInt(1, 12);
			statement.setInt(2, 4);
			statement.setInt(4, 20);
			statement.execute();
			System.out.println(statement.getInt(3));
			System.out.println(statement.getInt(4));
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			//step 6
			if (null != statement) {
				try {
					statement.close();
					statement = null;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (null != connection) {
				try {
					connection.close();
					connection = null;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

}
