package hh.learnj.database.jdbc.spring;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Original Jdbc Implement Above The Spring Framework
 * @author Hunny.Hu
 */
public class SpringJdbcGuide {

	public static void main(String[] args) {
		// Get the Spring Context
		// By Reference URL:http://www.tutorialspoint.com/spring/spring_jdbc_example.htm
		ClassPathXmlApplicationContext ctx = null;
		DataSource dataSource = null;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ctx = new ClassPathXmlApplicationContext(
					"spring-database-postgresql.xml");
			dataSource = ctx.getBean("dataSource", DataSource.class);
			con = dataSource.getConnection();
			ps = con.prepareStatement("select * from c_tm_c_000 limit ?");
			ps.setInt(1, 10);
			rs = ps.executeQuery();
			while (rs.next()) {
				System.out.println("" + rs.getInt("tm_000_id") + rs);
			}
			System.out.println("Query Result Operator Finished.");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				ps.close();
				con.close();
				ctx.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
