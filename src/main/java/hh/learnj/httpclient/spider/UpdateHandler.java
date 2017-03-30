/**
 * 
 */
package hh.learnj.httpclient.spider;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

import hh.learnj.httpclient.usecase.qichacha.DB;
import hh.learnj.httpclient.usecase.qichacha.DB.Hander;

/**
 * @author huzexiong
 *
 */
public class UpdateHandler implements Hander {

	private Map<String, String> values = null;

	public UpdateHandler(Map<String, String> values) {
		this.values = values;
	}

	@Override
	public String getTableName() {
		return "company";
	}

	@Override
	public void handle(Statement stmt) throws SQLException {
		if (null == values || values.isEmpty()) {
			return;
		}
		DB.update(getTableName(), values, "name", stmt);
	}

}
