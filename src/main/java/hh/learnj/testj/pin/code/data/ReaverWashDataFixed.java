package hh.learnj.testj.pin.code.data;

import hh.learnj.testj.pin.code.WpsPinUtil;
import hh.learnj.testj.pin.code.sqlite.JdbcSqliteUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

public class ReaverWashDataFixed {
	
	private static Logger logger = Logger.getLogger(ReaverWashDataFixed.class);
	
	private JdbcSqliteUtil db = new JdbcSqliteUtil();

	public static void main(String[] args) {
		new ReaverWashDataFixed().fixed();

	}
	
	public void fixed() {
		List<Map<String, Object>> list = db.query("select * from " + JdbcSqliteUtil.REAVER_WASH);
		for (Map<String, Object> m : list) {
			String BSSID = (String)m.get("BSSID");
			if (!WpsPinUtil.isIllegalBSSID(BSSID)) {
				BSSID = WpsPinUtil.handleBSSID(BSSID);
				logger.info(m + "|" + BSSID);
				m.put("BSSID", BSSID);
				Map<String, Object> wheres = new HashMap<String, Object>();
				wheres.put("ID", m.get("ID"));
				db.updateToWashReaver(m, wheres);
			}
		}
	}

}
