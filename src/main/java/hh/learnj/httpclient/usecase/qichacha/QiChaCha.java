/**
 * 
 */
package hh.learnj.httpclient.usecase.qichacha;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

/**
 * @author huzexiong
 *
 */
public class QiChaCha {

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		System.setProperty(org.slf4j.impl.SimpleLogger.DEFAULT_LOG_LEVEL_KEY, "INFO");
		DB.handle(new QueryHander());
	}

	/**
	 * 
	 */
	public static void updateSign() {
		final List<Map<String, String>> values = new ArrayList<Map<String, String>>();
		ExcelReader.read("d:/300万美金以下客户.xlsx", new ExcelReader.Handler() {
			@Override
			public void handle(int row, List<String> vals) {
				String name = vals.get(0);
				if (StringUtils.isNotBlank(name)) {
					Map<String, String> value = new HashMap<String, String>();
					value.put("name", vals.get(0));
					value.put("sign", "Y");
					values.add(value);
				}
			}
		}, 1);
		DB.handle(new UpdateHandler(values));
	}

	/**
	 * 初始化数据
	 */
	public static void init() {
		final List<Map<String, String>> values = new ArrayList<Map<String, String>>();
		ExcelReader.read("d:/300万美金以下客户.xlsx", new ExcelReader.Handler() {
			@Override
			public void handle(int row, List<String> vals) {
				Map<String, String> value = new HashMap<String, String>();
				value.put("city", vals.get(0));
				value.put("name", StringUtils.trim(vals.get(1)));
				value.put("code", vals.get(2));
				value.put("outAmount", vals.get(3));
				values.add(value);
			}
		}, 0);
		DB.handle(new InsertHandler(values));
	}
	

}
