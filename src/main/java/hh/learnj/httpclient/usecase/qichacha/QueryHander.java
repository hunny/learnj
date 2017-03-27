/**
 * 
 */
package hh.learnj.httpclient.usecase.qichacha;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.ClientProtocolException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hh.learnj.httpclient.usecase.qichacha.DB.Hander;

/**
 * @author huzexiong
 *
 */
public class QueryHander implements Hander {

	private static final Logger logger = LoggerFactory.getLogger(QueryHander.class);
	
	@Override
	public String getTableName() {
		return "company";
	}

	@Override
	public void handle(Statement stmt) throws SQLException {
		int offset = 0;
		int limit = 10;
		final QiChachaHttp http = new QiChachaHttp();
//		ExecutorService userExecutorService = Executors.newFixedThreadPool(10);
		do {
			String sql = String.format("select id, name from %s where mobile is not null order by id asc limit %d, %d", getTableName(), limit * offset, limit);
			ResultSet resultSet = stmt.executeQuery(sql);
			List<String> names = new ArrayList<String>();
			while (resultSet.next()) {
				final String name = resultSet.getString("name");
				String id = resultSet.getString("id");
				logger.debug("id[{}], name[{}]", id, name);
				names.add(name);
			}
			if (names.isEmpty()) {
				break;
			}
//			final CountDownLatch countDown = new CountDownLatch(names.size());
			for (final String name : names) {
//				userExecutorService.execute(new Runnable() {
//					@Override
//					public void run() {
//						try {
							String url = url(name);
							System.out.println(String.format("公司[%s]地址[%s]", name, url));
							if (StringUtils.isNotBlank(url)) {
								try {
									TimeUnit.MILLISECONDS.sleep(new Random().nextInt(500));
									http.get(url(name), "UTF-8", new QiChaChaListParser());
									
//									new QiChaChaListParser().parse(CheckProxy.html(url(name), "111.7.174.198", 80));
								} catch (ClientProtocolException e) {
									e.printStackTrace();
								} catch (IOException e) {
									e.printStackTrace();
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
//						} catch (Exception e) {
//							e.printStackTrace();
//						} finally {
//							countDown.countDown();
//						}
//					}
//				});
			}
//	    try {
//				countDown.await();
//			} catch (InterruptedException e) {
//				throw new RuntimeException(e);
//			}
			offset ++;
		} while (true);
	}
	
	protected String url(String name) {
		try {
			StringBuilder builder = new StringBuilder();
			builder.append("http://www.qichacha.com/search?key=");
			builder.append(URLEncoder.encode(name, "UTF-8"));
			builder.append("#index:2&");
			return builder.toString();
		} catch (UnsupportedEncodingException e) {
		}
		return null;
	}

}
