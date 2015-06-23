package hh.learnj.testj.pin.code;

import hh.learnj.testj.pin.code.data.collect.ReadExcelPin;
import hh.learnj.testj.pin.code.sqlite.JdbcSqliteUtil;
import hh.learnj.testj.pin.code.tenda.WpsPinTenda;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

public class CheckMacForPin {
	
	private static Logger logger = Logger.getLogger(ReadExcelPin.class);
	
	private JdbcSqliteUtil util = null;
	private WpsPinTenda tenda = null;
	
	public CheckMacForPin() {
		util = new JdbcSqliteUtil();
		tenda = new WpsPinTenda();
	}

	public static void main(String[] args) {
		new CheckMacForPin().readDb();
	}
	
	public void readDb() {
		List<Map<String, Object>> list = util.query("select * from reaver_cracked");
		if (null != list && !list.isEmpty()) {
			for (Map<String, Object> m : list) {
				String BSSID = (String)m.get("BSSID");
				if (doMatch(BSSID)) {
					logger.info("[+][-] Found src[-]" + m + "[-]");
					logger.info("\n");
				}
			}
		}
	}
	
	public void readLine() {
		URL url = CheckMacForPin.class.getResource(".");
		String path = url.getPath();
		path = path.replaceAll("target/classes/", "src/main/java/");
		path += "CheckMacForPin.java";
		File file = new File(path);
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(file));
			String line = null;
			while ((line = reader.readLine()) != null) {
				if (null != line && line.startsWith("//") && line.indexOf(":") > 0) {
					String [] tmp = line.split(" ");
					String BSSID = tmp[0].replaceAll("//\t*", "");
					if (doMatch(BSSID)) {
						logger.info(line);
					}
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
	
	public boolean doMatch(String BSSID) {
		boolean result = false;
//		logger.debug("Checking mac [->] " + BSSID);
		tenda.setBssid(BSSID);
		if (tenda.isTendaSpecial()) {
			logger.debug("[+][-] Found Tenda [-]" + BSSID + "[-] match pin code [-]" + tenda.getPinCode() + "[-]");
			result = true;
		}
		List<Map<String, Object>> list = util.query("select * from " + JdbcSqliteUtil.REAVER_WASH + " where bssid = '" + BSSID + "'");
		if (null == list || list.isEmpty()) {
			list = util.query("select * from " + JdbcSqliteUtil.REAVER_WASH);
		}
		for (Map<String, Object> m : list) {
			String ssid = (String)m.get("BSSID");
			ssid = ssid.toLowerCase();
			BSSID = BSSID.toLowerCase();
			if (ssid.contains(BSSID) || BSSID.contains(ssid)) {
				logger.debug("[+][-] Found match [-]" + m + "[-]");
				result = true;
			}
		}
		return result;
	}

}
