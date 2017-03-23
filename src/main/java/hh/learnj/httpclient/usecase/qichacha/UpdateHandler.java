/**
 * 
 */
package hh.learnj.httpclient.usecase.qichacha;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

import hh.learnj.httpclient.usecase.qichacha.DB.Hander;

/**
 * @author huzexiong
 *
 */
public class UpdateHandler implements Hander {

	private List<Map<String, String>> values = null;

	public UpdateHandler(List<Map<String, String>> values) {
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
		for (Map<String, String> value : values) {
			DB.update(getTableName(), value, "name", stmt);
		}
	}

}
