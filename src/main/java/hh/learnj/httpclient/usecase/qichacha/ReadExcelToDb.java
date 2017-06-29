/**
 * 
 */
package hh.learnj.httpclient.usecase.qichacha;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hh.learnj.httpclient.usecase.qichacha.DB.Hander;
import hh.learnj.httpclient.usecase.qichacha.ExcelReader.Handler;

/**
 * @author huzexiong
 *
 */
public class ReadExcelToDb {
	
	private static final Logger logger = LoggerFactory.getLogger(ReadExcelToDb.class);

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		logger.info("准备启动程序");
		ExcelReader.read("D:/qxe/上海一百五十万以上企业_部分_修正_需要抓取联系方式的.xlsx", new Handler() {
			@Override
			public void handle(int row, List<String> values) {
				if (1 == row) {
					return;
				}
				logger.info("{}", values);
				final String companyName = values.get(0);
				final String businesser = values.get(1);
				final String address = values.get(2);
				if (StringUtils.isNotBlank(companyName)) {
					DB.handle(new Hander() {
						@Override
						public String getTableName() {
							return "company";
						}

						@Override
						public void handle(Statement stmt) throws SQLException {
							ResultSet resultSet = stmt
									.executeQuery(String.format("select * from %s where name = '%s'", getTableName(), companyName));
							DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
							String date = dateFormat.format(new Date());
							if (resultSet.next()) {
								Map<String, String> update = new HashMap<String, String>();
								update.put("name", companyName);
								update.put("lastUpdated", date);
								DB.update(getTableName(), update, "name", stmt);
							} else {
								Map<String, String> insert = new HashMap<String, String>();
								insert.put("name", companyName);
								if (StringUtils.isNotBlank(businesser)) {
									insert.put("businesser", businesser);
								}
								if (StringUtils.isNotBlank(address)) {
									insert.put("address", address);
								}
								insert.put("lastUpdated", date);
								insert.put("dateCreated", date);
								DB.insert(getTableName(), insert, stmt);
							}
						}
					});
				}
			}
		}, 0);
	}

}
