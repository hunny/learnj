package hh.learnj.httpclient.usecase.topease.chinaexport;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hh.learnj.httpclient.usecase.qichacha.DB;
import hh.learnj.httpclient.usecase.qichacha.DB.Hander;

public class ExportDBHandler implements Hander {

	private final Logger logger = LoggerFactory.getLogger(ExportDBHandler.class);
	
	private Map<String, String> insert = new HashMap<String, String>();
	
	public ExportDBHandler(Map<String, String> insert) {
		this.insert.clear();
		this.insert.putAll(copyMap(insert));
	}
	
	@Override
	public String getTableName() {
		return "company";
	}

	@Override
	public void handle(Statement stmt) throws SQLException {
		String name = insert.get("name");
		if (StringUtils.isBlank(name)) {
			debug("名称为空,忽略[{}]", insert);
			return;
		}
		ResultSet resultSet = stmt.executeQuery(String.format("select name, code, category from %s where name = '%s'", getTableName(), name));
		if (resultSet.next()) {
			insert.put("category", select(resultSet.getString("category"), insert.get("category")));
			insert.put("code", select(resultSet.getString("code"), insert.get("code")));
			DB.update(getTableName(), insert, "name", stmt);
		} else {
			DB.insert(getTableName(), insert, stmt);
		}
	}
	
	protected void debug(String format, Object...arguments) {
		if (logger.isDebugEnabled()) {
			logger.debug(format, arguments);
		}
	}
	
	protected String select(String src, String dest) {
		if (StringUtils.isBlank(dest)) {
			return src;
		}
		if (StringUtils.isNotBlank(src)) {
			if (!src.contains(dest)) {
				dest = src + "," + dest;
			} else {
				dest = src;
			}
		}
		return dest;
	}
	
	protected Map<String, String> copyMap(Map<String, String> map) {
		if (null == map) {
			return Collections.emptyMap();
		}
		Map<String, String> tmp = new HashMap<String, String>();
		for (Map.Entry<String, String> m : map.entrySet()) {
			tmp.put(m.getKey(), m.getValue());
		}
		return tmp;
	}

}
