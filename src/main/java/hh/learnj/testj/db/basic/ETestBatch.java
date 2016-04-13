package hh.learnj.testj.db.basic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class ETestBatch {

	public static void main(String[] args) {
		Connection connection = null;
		Statement statement = null;
		PreparedStatement preparedStatement = null;
		try {
			//step 1
			Class.forName("com.mysql.jdbc.Driver");
			//step 2
			connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:4306/test", "root", "mysqlpasswd");
			
			preparedStatement = connection.prepareStatement("insert into t_user (username, password, nick_name) values (?, ? ,?)");
			
			connection.setAutoCommit(false);
			int random = (int)(Math.random() * 100);
			System.out.println(random);
			preparedStatement.setString(1, "username" + random);
			preparedStatement.setString(2, "passwd" + random);
			preparedStatement.setString(3, "nickname" + random);
			preparedStatement.addBatch();
			
			random = (int)(Math.random() * 100);
			System.out.println(random);
			preparedStatement.setString(1, "username" + random);
			preparedStatement.setString(2, "passwd" + random);
			preparedStatement.setString(3, "nickname" + random);
			preparedStatement.addBatch();
			
			preparedStatement.executeBatch();
			connection.commit();
			connection.setAutoCommit(true);
		} catch (Exception e) {
			e.printStackTrace();
			try {
				if (null != connection) {
					connection.rollback();
					connection.setAutoCommit(true);
				}
			} catch (Exception ee) {
				ee.printStackTrace();
			}
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
			if (null != preparedStatement) {
				try {
					preparedStatement.close();
					preparedStatement = null;
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
