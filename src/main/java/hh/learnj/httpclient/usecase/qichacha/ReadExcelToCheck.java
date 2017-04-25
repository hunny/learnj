/**
 * 
 */
package hh.learnj.httpclient.usecase.qichacha;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.tools.ant.util.DateUtils;

import hh.learnj.httpclient.usecase.qichacha.DB.Hander;
import hh.learnj.httpclient.usecase.qichacha.ExcelReader.Handler;
import hh.learnj.httpclient.usecase.qichacha.ExcelWriter.WriterHandler;

/**
 * @author huzexiong
 *
 */
public class ReadExcelToCheck {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		final List<List<String>> result = new ArrayList<List<String>>();
		ExcelReader.read("D:/qxe/上海一百五十万以上企业_部分.xlsx", new Handler() {
			@Override
			public void handle(int row, List<String> values) {
				if (1 == row) {
					return;
				}
				final String companyName = values.get(0);
				final String address = values.get(2);
				String owner = values.get(1);
				if (StringUtils.isBlank(owner)) {
					final List<String> value = new ArrayList<String>();
					DB.handle(new Hander() {
						@Override
						public String getTableName() {
							return "company";
						}
						@Override
						public void handle(Statement stmt) throws SQLException {
							ResultSet resultSet = stmt.executeQuery(String.format("select * from %s where name = '%s'", getTableName(), companyName));
							if (resultSet.next()) {
								value.add(resultSet.getString("businesser"));
								value.add(resultSet.getString("address"));
								value.add(resultSet.getString("mobile"));
							} else {
								Map<String, String> insert = new HashMap<String, String>();
								insert.put("name", companyName);
								insert.put("dateCreated", DateUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
								DB.insert(getTableName(), insert, stmt);
							}
						}
					});
					if (!value.isEmpty()) {
						if (StringUtils.isBlank(owner)) {//businesser
							values.set(1, value.get(0));
						}
						if (StringUtils.isBlank(address)) {//address
							values.set(2, value.get(1));
						}
						if (StringUtils.isBlank(values.get(3))) {//mobile
							values.set(3, value.get(2));
						}
					}
				}
				result.add(values);
			}
		}, 0);
		ExcelWriter.wirte("D:/qxe/上海一百五十万以上企业_部分_修正.xlsx", new WriterHandler() {
			@Override
			public List<List<String>> getValues() {
				return result;
			}

			@Override
			public List<String> getHeaders() {
				return Arrays.asList(new String[] { "公司名称", "法人", "公司地址", "电话", "联系人", "区域", "出口总额", "分值", "是否合作企业" });
			}
		}, "部分");
	}

}
