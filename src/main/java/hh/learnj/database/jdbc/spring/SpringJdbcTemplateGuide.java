package hh.learnj.database.jdbc.spring;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * Spring JdbcTemplate Example
 * @author Hunny.Hu
 */
public class SpringJdbcTemplateGuide {

	public static void main(String[] args) {
		// Get the Spring Context By Reference Address:
		// http://www.journaldev.com/2593/spring-jdbc-and-jdbctemplate-crud-with-datasource-example-tutorial
		ClassPathXmlApplicationContext ctx = null;
		DataSource dataSource = null;
		try {
			ctx = new ClassPathXmlApplicationContext(
					"spring-database-postgresql.xml");
			dataSource = ctx.getBean("dataSource", DataSource.class);
			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
			List<Map<String, Object>> list = jdbcTemplate
					.queryForList("select * from c_tm_c_000 limit 10");
			if (null != list && !list.isEmpty()) {
				for (Map<String, Object> elem : list) {
					System.out.println(elem);
				}
			}
			System.out.println("Query Result Operator Finished.");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				ctx.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
