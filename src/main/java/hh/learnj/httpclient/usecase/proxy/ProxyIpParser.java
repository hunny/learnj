/**
 * 
 */
package hh.learnj.httpclient.usecase.proxy;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import hh.learnj.httpclient.usecase.qichacha.DB;
import hh.learnj.httpclient.usecase.qichacha.DB.Hander;

/**
 * @author huzexiong
 *
 */
public class ProxyIpParser implements Parser {

	@Override
	public void parse(String html) {
		Document doc = Jsoup.parse(html);
		Elements elements = doc.select("div.arc > div.content > p");
		List<String[]> list = new ArrayList<String[]>();
		for (Element element : elements) {
			String text = element.text();
			String[] ipstr = text.split("@")[0].split(":");
			list.add(ipstr);
		}
		ExecutorService executorService = Executors.newFixedThreadPool(5);
		final CountDownLatch countDownLatch = new CountDownLatch(list.size());
		for (final String[] arr : list) {
			if (checkExisted(arr)) {
				countDownLatch.countDown();
				continue;
			}
			executorService.execute(new Runnable() {
				@Override
				public void run() {
					try {
						long start = System.currentTimeMillis();
						if (CheckProxy.check(arr[0], Integer.parseInt(arr[1]))) {
							final Map<String, String> map = new HashMap<String, String>();
							long time = (System.currentTimeMillis() - start);
							System.out
									.println(String.format("IP:%s,P:%s,T:%dms", arr[0], arr[1], time));
							map.put("ip", arr[0]);
							map.put("port", arr[1]);
							map.put("timeout", String.format("%dms", time));
							map.put("dateCreated", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
							try {
								DB.handle(new Hander() {
									@Override
									public String getTableName() {
										return "proxy";
									}
									@Override
									public void handle(Statement stmt) throws SQLException {
										DB.insert(getTableName(), map, stmt);
									}
								});
							} catch (Exception e) {
								e.printStackTrace();
							}
						} else {
							System.err
									.println(String.format("IP:%s,PORT:%s,T:%dms", arr[0], arr[1], (System.currentTimeMillis() - start)));
						}
					} catch (Exception e) {
						System.err.println(e.getMessage());
					} finally {
						countDownLatch.countDown();
					}
				}
			});
		}
		try {
			countDownLatch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		executorService.shutdown();
	}
	
	/**
	 * @param arr
	 */
	public static boolean checkExisted(final String[] arr) {
		try {
			DB.handle(new Hander() {
				@Override
				public String getTableName() {
					return "proxy";
				}
				@Override
				public void handle(Statement stmt) throws SQLException {
					ResultSet resultSet = stmt.executeQuery(String.format("select * from %s where ip = '%s' and port = %s", getTableName(), arr[0], arr[1]));
					if (resultSet.next()) {
						throw new UnsupportedOperationException(String.format("%s已经存在", arr[0]));
					}
				}
			});
		} catch (UnsupportedOperationException e) {
			System.err.println(e.getMessage());
			return true;
		}
		return false;
	}

}
