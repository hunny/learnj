package hh.learnj.testj;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

public class BadIpAnalysis {

	protected final String BASE_DIR = "D://work";
	protected final Pattern BAD_IP_PATTERN = Pattern.compile("bad IP:\\s+\\(\\'((\\d{1,3}\\.{0,1}){4})\\',\\s+443\\)");
	protected final Pattern IP_PATTERN = Pattern.compile("(\\d{1,3}\\.{1}){3}\\d{1,3}");

	protected Set<String> readBadIps(String fileName) {
		Set<String> result = new HashSet<String>();
		BufferedReader bufferedReader = null;
		try {
			File file = new File(fileName);
			bufferedReader = new BufferedReader(new FileReader(file));
			String line = null;
			while ((line = bufferedReader.readLine()) != null) {
				String badIp = checkBadIp(line);
				if (null != badIp) {
					result.add(badIp);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != bufferedReader) {
				try {
					bufferedReader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}

	protected Set<String> readIpsFromFile(String fileName) {
		Set<String> result = new HashSet<String>();
		BufferedReader bufferedReader = null;
		try {
			File file = new File(fileName);
			bufferedReader = new BufferedReader(new FileReader(file));
			String line = null;
			while ((line = bufferedReader.readLine()) != null) {
				result.addAll(readIpsFromString(line));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != bufferedReader) {
				try {
					bufferedReader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}

	protected Set<String> readIpsFromString(String str) {
		Set<String> result = new HashSet<String>();
		if (null != str) {
			String[] ips = str.split("\\|");
			if (null == ips || ips.length == 0) {
				return result;
			}
			for (String ip : ips) {
				Matcher matcher = IP_PATTERN.matcher(ip);
				if (matcher.find()) {
					result.add(ip);
				}
			}
		}
		return result;
	}

	protected Set<String> getIpListFromProp(String fileName) {
		Set<String> result = new HashSet<String>();
		Properties prop = new Properties();
		InputStream inputStream = null;
		try {
			inputStream = new FileInputStream(new File(fileName));
			prop.load(inputStream);
			result.addAll(readIpsFromString(prop.getProperty("google_hk")));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != inputStream) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}

	protected String checkBadIp(String str) {
		if (null == str) {
			return null;
		}
		Matcher matcher = BAD_IP_PATTERN.matcher(str);
		if (matcher.find()) {
			return matcher.group(1);
		}
		return null;
	}

	protected void showResult(Set<String> result) {
		if (null == result) {
			return;
		}
		for (String str : result) {
			System.out.println("[+]" + str);
		}
	}

	protected String catIps(Set<String> ips) {
		StringBuffer buffer = new StringBuffer();
		for (String ip : ips) {
			buffer.append(ip);
			buffer.append("|");
		}
		return buffer.toString().replaceFirst("\\|$", "");
	}

	@Test
	public void testBadIps() {
		showResult(readBadIps(BASE_DIR + "20160719.log"));
	}

	@Test
	public void testReadIps() {
		showResult(getIpListFromProp(BASE_DIR + "proxy.ini"));
	}

	@Test
	public void testClearBadIps() {
		Set<String> myIps = getIpListFromProp(BASE_DIR + "proxy.ini");
		Set<String> goodIps = readIpsFromFile(BASE_DIR + "goodips.log");
		myIps.addAll(goodIps);
		System.out.println("清洗前IP数：" + myIps.size());
		Set<String> badIps = readBadIps(BASE_DIR + "20160719.log");
		for (String badIp : badIps) {
			myIps.remove(badIp);
		}
		System.out.println("清洗后IP数：" + myIps.size());
		System.out.println(catIps(myIps));
	}

}
