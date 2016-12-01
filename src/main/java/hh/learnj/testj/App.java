package hh.learnj.testj;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Hello world!
 *
 */
public class App {

	private static final Logger logger = LoggerFactory.getLogger(App.class);

	public static void main(String[] args) throws IOException {

		command("dir/w", "ls");

		command("svn --help", "svn --help");

		String input = "12.0,89,98,-10,-12.0,0.9";
		System.out.println("Hello World=" + input.matches("^(((-?\\d+)|((-?\\d+)(\\.\\d+))),{0,1})+$"));

		String src = "http://test.cdp-life.com/mobile/wx_authorize.php";
		try {
			System.out.println(java.net.URLEncoder.encode(src, "utf-8"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println();
		test("abccdefcfk");
		test("qwertyuiopasdfgfdsfdsfsdfhjklzxcvbnmERTYDFGHJASDFGHJKLzxcvbnmwertdfghcvbnRfgfdgdfgTYFGH");
	}

	public static void command(String command1, String command2) throws IOException {
		boolean windows = isWindows();
		List<String> lines = IOUtils.readLines( //
				Runtime.getRuntime().exec(windows ? "cmd.exe /c \"" + command1 + "\"" : command2).getInputStream(),
				Charset.forName(windows ? "GBK" : "UTF-8")); //
		for (String s : lines) {
			logger.info(s);
		}
	}

	public static List<String> divideString(String str) {
		if (null == str) {
			return Collections.emptyList();
		}
		List<String> result = new ArrayList<String>();
		if (str.length() == 1) {
			result.add(str);
			return result;
		}
		for (int i = 1; i < str.length(); i++) {
			String substr = str.substring(0, i);
			char c = str.charAt(i);
			int offset = substr.indexOf(c);
			if (offset >= 0) {
				System.out.println("offset" + offset + "=>i" + i);
				result.add(substr);
				String endstr = str.substring(i, str.length());
				result.addAll(divideString(endstr));
				// result.addAll(divideString(str.substring(0, offset + 1)));
				result.addAll(divideString(str.substring(offset + 1, str.length())));
				break;
			}
			if (str.length() == (i + 1)) {
				result.add(str);
			}
		}
		return result;
	}

	public static void test(String str) {
		List<String> list = divideString(str);
		String max = null;
		List<String> maxList = new ArrayList<String>();
		for (String s : list) {
			if (null == max) {
				max = s;
			}
			System.out.println("[+] " + s);
			if (s.length() >= max.length()) {
				if (s.length() > max.length()) {
					maxList.clear();
				}
				max = s;
				maxList.add(max);
			}
		}
		System.out.println("max strings:");
		for (String s : maxList) {
			System.out.println("[+][MAX]" + s);
		}
	}

	public static String getHostName() {
		String hostName = null;
		try {
			List<String> lines = IOUtils.readLines(Runtime.getRuntime()
					.exec(isWindows() ? "cmd.exe /c \"set computername\"" : "hostname").getInputStream());
			for (String s : lines) {
				logger.info(s);
			}
			if (CollectionUtils.isNotEmpty(lines) && StringUtils.isNotBlank(lines.get(0))) {
				hostName = lines.get(0);

				if (hostName.indexOf('=') > 0) {
					hostName = hostName.substring(hostName.indexOf('=') + 1);
				}
			}
		} catch (Throwable ex) {
			logger.error("获取机器名失败！", ex);
		}

		return hostName;
	}

	public static boolean isWindows() {
		return StringUtils.indexOfIgnoreCase(System.getProperty("os.name"), "Windows") >= 0;
	}

	public static String getIp() {
		try {
			Enumeration<NetworkInterface> nis = NetworkInterface.getNetworkInterfaces();
			while (nis.hasMoreElements()) {
				NetworkInterface ni = nis.nextElement();
				if (!ni.isUp())
					continue;
				if (ni.isLoopback())
					continue;
				if (ni.isPointToPoint())
					continue;
				if (ni.isVirtual())
					continue;

				Enumeration<InetAddress> ias = ni.getInetAddresses();
				while (ias.hasMoreElements()) {
					InetAddress ia = ias.nextElement();
					if (ia instanceof Inet4Address) {
						return ia.getHostAddress();
					}
				}
			}
		} catch (Exception ex) {
			logger.error("获取IP失败！", ex);
		}
		return null;
	}
}
