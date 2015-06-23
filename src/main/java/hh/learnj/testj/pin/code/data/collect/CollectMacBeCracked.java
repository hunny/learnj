package hh.learnj.testj.pin.code.data.collect;

import hh.learnj.testj.pin.code.sqlite.JdbcSqliteUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

public class CollectMacBeCracked {
	
	private static Logger logger = Logger.getLogger(ReadExcelPin.class);
	private JdbcSqliteUtil util = null;
	
	public CollectMacBeCracked() {
		util = new JdbcSqliteUtil();
	}

	public static void main(String[] args) {
		CollectMacBeCracked macBeCracked = new CollectMacBeCracked();
		macBeCracked.readLine();
	}
	
	public void readLine() {
		String path = "/Users/hunnyhu/Downloads/BSSID";
		File file = new File(path);
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(file));
			String line = null;
			while ((line = reader.readLine()) != null) {
				if (null != line && line.indexOf(":") > 0) {
					String [] tmp = line.replaceAll("\\s+", " ").split(" ");
					String BSSID = tmp[0];
					String CHANNEL = tmp[1];
					String RSSI = tmp[2];
					String WPS_VERSION = tmp[3];
					String WPS_LOCKED = tmp[4];
					String ESSID = tmp[5];
					Map<String, Object> values = new HashMap<String, Object>();
					values.put("BSSID", BSSID);
					values.put("CHANNEL", CHANNEL);
					values.put("RSSI", RSSI);
					values.put("WPS_VERSION", WPS_VERSION);
					values.put("WPS_LOCKED", WPS_LOCKED);
					values.put("ESSID", ESSID);
					values.put("REMARK", "湾流汇南边");
					List<Map<String, Object>> list = util.query("select * from " + JdbcSqliteUtil.REAVER_CRACKED + " where bssid = '" + BSSID + "'");
					if (null == list || list.isEmpty()) {
						logger.info("[+][-] " + ESSID + " [-] " + BSSID + " [-] saving....");
						util.saveToReaverCracked(values);
						logger.info("[+][-] insert info  [-]" + values);
					} else {
						for (Map<String, Object> m : list) {
							Map<String, Object> wheres = new HashMap<String, Object>();
							wheres.put("ID", m.get("ID"));
							logger.info("[+][-] " + ESSID + " [-] " + BSSID + " [-] has existed. Will be updated by ID = " + m.get("ID"));
							logger.info("[+][-] update info  [-]" + m);
							logger.info("[+][-] update value [-]" + values);
							util.updateToReaverCracked(values, wheres);
						}
					}
					logger.info("\n");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != reader) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
