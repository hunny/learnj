package hh.learnj.httpclient.usecase.topease.chinaexport;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

import hh.learnj.httpclient.usecase.qichacha.DB;
import hh.learnj.httpclient.usecase.qichacha.DB.Hander;

public class ExportDBHandler implements Hander {

	private Map<String, String> insert;
	
	public ExportDBHandler(Map<String, String> insert) {
		this.insert = insert;
	}
	
	@Override
	public String getTableName() {
		return "company";
	}

	@Override
	public void handle(Statement stmt) throws SQLException {
		// TODO 根据名称查询，如有，则做更新操作
		DB.insert(getTableName(), insert, stmt);
	}

}
