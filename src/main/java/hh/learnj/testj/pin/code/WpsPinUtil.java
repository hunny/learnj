package hh.learnj.testj.pin.code;

public class WpsPinUtil {
	
	public static Boolean isIllegalBSSID(String BSSID) {
		if (BSSID.matches("^[0-9a-fA-F]{2}([:][0-9a-fA-F]{2}){1,5}$")) {
			return true;
		}
		return false;
	}
	
	public static String handleBSSID(String BSSID) {
		BSSID = BSSID.replaceAll("\\s", "");
		BSSID = BSSID.replaceAll("-+", ":");
		BSSID = BSSID.replaceAll("：", ":");
		BSSID = BSSID.replaceAll("&QU0T;", ":");
		BSSID = BSSID.toUpperCase();
		BSSID = BSSID.replaceAll("\\*", "");
		BSSID = BSSID.replaceAll("O", "0");
		BSSID = BSSID.replaceAll("o", "0");
		BSSID = BSSID.replaceAll("#", "");
		BSSID = BSSID.replaceAll(";", ":");
		BSSID = BSSID.replaceAll("X", "");
		BSSID = BSSID.replaceAll(":+", ":");
		BSSID = BSSID.replaceAll("–", "");
		BSSID = BSSID.replaceAll("_", "");
		if (BSSID.indexOf(":") < 0) {
			String tmp = "";
			int len = BSSID.length();
			for (int i = 0; i < len / 2; i++) {
				tmp += BSSID.substring(i * 2, (i + 1) * 2) + ":";
			}
			BSSID = tmp.replaceFirst(":$", "");
		}
		String [] arr = BSSID.split(":");
		StringBuffer buffer = new StringBuffer();
		for (String s : arr) {
			int size = s.length();
			if (size / 2 > 1) {
				for (int n = 0; n < size; n++) {
					buffer.append(s.substring(n, n + 2));
					buffer.append(":");
					n++;
				}
			} else {
				buffer.append(s);
				buffer.append(":");
			}
		}
		BSSID = buffer.toString();
		BSSID = BSSID.replaceFirst(":$", "");
		return BSSID;
	}

}
