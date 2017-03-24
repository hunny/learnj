/**
 * 
 */
package hh.learnj.testj.proxy;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author huzexiong
 *
 */
public class FindIps {
	
	public static final Pattern IP_PATTERN = Pattern.compile("(\\d{1,3}\\.{1}){3}\\d{1,3}");
	public static final Logger logger = LoggerFactory.getLogger(FindIps.class);
	public static final Integer THREAD_COUNT = 10;
	
	private static ExecutorService executorService = Executors.newFixedThreadPool(THREAD_COUNT, new ThreadFactory() {
		public Thread newThread(Runnable r) {
			return new Thread(r, "LaborThreadFactory");
		}
	});
	
	public static void main(String[] args) {
		final String BASE_DIR = "D:/work/software/goagent-goagent-v3.2.3/local/";
		Set<String> goodIps = readIpsFromFile(BASE_DIR + "goodips.log");
		final Queue<String> queue = new ConcurrentLinkedQueue<String>();
		queue.addAll(goodIps);
		for (int i = 0; i < THREAD_COUNT; i++) {
			executorService.submit(new Runnable() {
				public void run() {
					String ip = queue.poll();
					while (null != ip) {
						findIp(ip);
					}
				}
			});
		}
	}

	private static void findIp(String ip) {
		HttpGet httpReq = new HttpGet("https://" + ip + "/?" + UUID.randomUUID().toString());
		HttpResponse response = null;
		try {
			logger.info("Ready to get:{}", ip);
			response = HttpConnect.execute(httpReq);
			if (response.getStatusLine().getStatusCode() == 200) {
				String serverName = response.getLastHeader("Server").getValue();
				logger.info("serverName:{}", serverName);
				if (serverName.equals("gws")) {
					System.out.print(ip + "|");
				}
			}
		} catch (IOException e) {
			logger.error("检测IP[{}]异常:{}", ip, e.getMessage());
		}
	}
	
	public static Set<String> readIpsFromFile(String fileName) {
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

	public static Set<String> readIpsFromString(String str) {
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

}
