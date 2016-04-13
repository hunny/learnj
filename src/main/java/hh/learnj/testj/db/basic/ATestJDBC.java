package hh.learnj.testj.db.basic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ATestJDBC {

	public static void main(String[] args) {
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			//step 1
			Class.forName("com.mysql.jdbc.Driver");
			//step 2
			connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:4306/test", "root", "mysqlpasswd");
			//step 3
			statement = connection.createStatement();
			//step 4
			resultSet = statement.executeQuery("select * from t_user");
			//step 5
			while (resultSet.next()) {
				System.out.print(resultSet.getInt("id"));
				System.out.print("|");
				System.out.println(resultSet.getString("username"));
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			//step 6
			if (null != resultSet) {
				try {
					resultSet.close();
					resultSet = null;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
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
