package hh.learnj.testj;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.junit.Test;

public class BadIpAnalysis {

	protected final String BASE_DIR = "D:/work/";
	protected final Pattern BAD_IP_PATTERN = Pattern.compile("bad IP:\\s+\\(\\'((\\d{1,3}\\.{0,1}){4})\\',\\s+443\\)");
	protected final Pattern IP_PATTERN = Pattern.compile("(\\d{1,3}\\.{1}){3}\\d{1,3}");
	protected final Logger logger = Logger.getLogger(this.getClass());

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
		return getIpListFromProp(fileName, new String[] {"google_hk"});
	}

	protected Set<String> getIpListFromProp(String fileName, String... keys) {
		Set<String> result = new HashSet<String>();
		if (null == keys || keys.length == 0) {
			return result;
		}
		Properties prop = new Properties();
		InputStream inputStream = null;
		try {
			inputStream = new FileInputStream(new File(fileName));
			prop.load(inputStream);
			for (String key : keys) {
				result.addAll(readIpsFromString(prop.getProperty(key)));
			}
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
			logger.info(str);
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
	
	protected String backupFileToDirectory(String fileName, String dir) {
		File srcFile = new File(fileName);
		if (!srcFile.exists()) {
			return null;
		}
		File dirFile = new File(dir);
		if (!dirFile.exists()) {
			dirFile.mkdirs();
		}
		String backup = "backup." + new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date()) + ".";
		File destFile = new File(dirFile.getAbsolutePath() + File.separator + backup + srcFile.getName());
		try {
			FileUtils.copyFile(srcFile, destFile);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return destFile.getAbsolutePath();
	}
	
	protected String checkReplacedLine(String line, Map<String, String> map, Map<String, Pattern> mapPattern) {
		if (null == line) {
			return line;
		}
		for (Map.Entry<String, String> m : map.entrySet()) {
			Pattern p = mapPattern.get(m.getKey());
			Matcher matcher = p.matcher(line);
			if (matcher.find()) {
				String nLine = matcher.group(1) + m.getValue();
				logger.info("[S]" + line);
				logger.info("[M]" + nLine);
				return nLine;
			}
		}
		return line;
	}
	
	public boolean replaceFileLineInfo(Map<String, String> map, String srcFileName, String distFileName) {
		BufferedReader bufferedReader = null;
		BufferedWriter bufferedWriter = null;
		Map<String, Pattern> mapPattern = new HashMap<String, Pattern>();
		for (Map.Entry<String, String> m : map.entrySet()) {
			mapPattern.put(m.getKey(), Pattern.compile("^(" + m.getKey() + "\\s?=\\s?)"));
		}
		try {
			File srcFile = new File(srcFileName);
			File distFile = new File(distFileName);
			bufferedReader = new BufferedReader(new FileReader(srcFile));
			bufferedWriter = new BufferedWriter(new FileWriter(distFile, false));
			String line = null;
			while ((line = bufferedReader.readLine()) != null) {
				bufferedWriter.write(checkReplacedLine(line, map, mapPattern) + "\r\n");
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
			if (null != bufferedWriter) {
				try {
					bufferedWriter.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return true;
	}
	
	protected boolean restartApplication() {
//		try {
//			Runtime.getRuntime().exec("taskkill /F /IM goagent.exe");
//			Runtime.getRuntime().exec(BASE_DIR + File.separator + "startup.bat");
////			new ProcessBuilder("taskkill", "/F /IM goagent.exe").start();
//		} catch (Exception e) {
//			e.printStackTrace();
//			return false;
//		}
		return true;
	}
	
	@Test
	public void testBadIps() {
		showResult(readBadIps(BASE_DIR + "badips.log"));
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
		logger.info("Before clean IP count:" + myIps.size());
		Set<String> badIps = readBadIps(BASE_DIR + "badips.log");
		logger.info("Bad IP count：" + badIps.size());
		for (String badIp : badIps) {
			myIps.remove(badIp);
		}
		logger.info("After clean IP count：" + myIps.size());
		String newIps = catIps(myIps);
		logger.info("[N]newIps = " + newIps);
		String backupFileName = backupFileToDirectory(BASE_DIR + "proxy.ini", BASE_DIR + File.separator + "backup");
		if (null != backupFileName) {
			logger.info("backup success.");
			Map<String, String> values = new HashMap<String, String>();
			values.put("google_hk", newIps);
			if (replaceFileLineInfo(values, backupFileName, BASE_DIR + "proxy.ini")) {
				logger.info("Remove Bad IPs success.");
				if (restartApplication()) {
					logger.info("Restart Application success!");
				}
			}
		}
	}

}
