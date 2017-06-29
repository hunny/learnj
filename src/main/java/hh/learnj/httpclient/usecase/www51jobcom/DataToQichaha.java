/**
 * 
 */
package hh.learnj.httpclient.usecase.www51jobcom;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author huzexiong
 *
 */
public class DataToQichaha {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		hh.learnj.httpclient.usecase.www51jobcom.DB.handle(null, new hh.learnj.httpclient.usecase.www51jobcom.DB.Hander() {
			@Override
			public String getTableName() {
				return "company";
			}

			@Override
			public void handle(List<Map<String, String>> values, Statement stmt) throws SQLException {
				ResultSet set = stmt.executeQuery("select name, address from company");
				while (set.next()) {
					final String name = set.getString("name");
					final String address = set.getString("address");
					hh.learnj.httpclient.usecase.qichacha.DB.handle(new hh.learnj.httpclient.usecase.qichacha.DB.Hander() {
						@Override
						public String getTableName() {
							return "company";
						}
						@Override
						public void handle(Statement stmt) throws SQLException {
							ResultSet set = stmt.executeQuery(String.format("select name from company where name = '%s' ", name));
							Map<String, String> values = new HashMap<String, String>();
							values.put("name", name);
							values.put("address", address);
							DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
							values.put("lastUpdated", format.format(new Date()));
							if (set.next()) {
								hh.learnj.httpclient.usecase.qichacha.DB.update(getTableName(), values, "name", stmt);
							} else {
								values.put("dateCreated", format.format(new Date()));
								hh.learnj.httpclient.usecase.qichacha.DB.insert(getTableName(), values, stmt);
							}
						}
						
					});
				}
			}

		});
	}

}
