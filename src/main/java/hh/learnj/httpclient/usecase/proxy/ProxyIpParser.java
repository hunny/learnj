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
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
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

	private ExecutorService executorService = Executors.newFixedThreadPool(10);

	@Override
	public void parse(String html) {
		Document doc = Jsoup.parse(html);
		Elements elements = doc.select("div.arc > div.content > p");
		List<String[]> list = new ArrayList<String[]>();
		for (Element element : elements) {
			String text = element.text();
			String[] ipstr = text.split("@")[0].split(":");
			if (null == ipstr || ipstr.length < 2) {
				continue;
			}
			list.add(ipstr);
		}
		if (list.isEmpty()) {
			System.out.println("没有查询到IP地址。");
			return;
		}
		CyclicBarrier barrier = new CyclicBarrier(list.size());
		for (final String[] arr : list) {
			if (checkExisted(arr)) {
				continue;
			}
			executorService.execute(new Checker(arr[0], arr[1], barrier));
		}
		executorService.shutdown();
	}

	class Checker implements Runnable {

		private String ip = null;
		private String port = null;
		private CyclicBarrier barrier = null;

		public Checker(String ip, String port, CyclicBarrier barrier) {
			this.ip = ip;
			this.port = port;
			this.barrier = barrier;
		}

		@Override
		public void run() {
			try {
				checker(this.ip, this.port);
				barrier.await();
			} catch (InterruptedException e) {
				System.err.println(e.getMessage());
			} catch (BrokenBarrierException e) {
				System.err.println(e.getMessage());
			}
		}

		protected void checker(String ip, String port) {
			long start = System.currentTimeMillis();
			if (CheckProxy.check(ip, Integer.parseInt(port))) {
				System.err.println(String.format("IP:%s,PORT:%s,T:%dms", ip, port, (System.currentTimeMillis() - start)));
				return;
			}
			final Map<String, String> map = new HashMap<String, String>();
			long time = (System.currentTimeMillis() - start);
			System.out.println(String.format("IP:%s,P:%s,T:%dms", ip, port, time));
			map.put("ip", ip);
			map.put("port", port);
			map.put("timeout", String.format("%dms", time));
			map.put("validate", "Y");
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
				System.err.println(e.getMessage());
			}
		}

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
					ResultSet resultSet = stmt.executeQuery(
							String.format("select * from %s where ip = '%s' and port = %s", getTableName(), arr[0], arr[1]));
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
