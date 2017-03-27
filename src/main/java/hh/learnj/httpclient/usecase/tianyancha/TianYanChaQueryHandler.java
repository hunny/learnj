/**
 * 
 */
package hh.learnj.httpclient.usecase.tianyancha;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.ClientProtocolException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hh.learnj.httpclient.usecase.qichacha.DB.Hander;

/**
 * @author huzexiong
 *
 */
public class TianYanChaQueryHandler implements Hander {

	private static final Logger logger = LoggerFactory.getLogger(TianYanChaQueryHandler.class);

	@Override
	public String getTableName() {
		return "company";
	}

	@Override
	public void handle(Statement stmt) throws SQLException {
		int offset = 0;
		int limit = 5;
		TianYanChaHttp http = new TianYanChaHttp();
		do {
			String sql = String.format("select id, name from %s where mobile is not null order by id asc limit %d, %d",
					getTableName(), limit * offset, limit);
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
			for (final String name : names) {
				String url = url(name);
				System.out.println(String.format("公司[%s]地址[%s]", name, url));
				if (StringUtils.isNotBlank(url)) {
					try {
//						TimeUnit.MILLISECONDS.sleep(new Random().nextInt(500));
						http.get(url, "UTF-8", null, null);
						Map<String, String> header = new HashMap<String, String>();
						header.put("Referer", url);
						header.put("Tyc-From", "normal");
						http.get(url1(name), "UTF-8", null, header);
						url = url2(name);
						http.get(url, "UTF-8", new TianYanChaListParser(header), header);
					} catch (ClientProtocolException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			offset++;
		} while (true);
	}

	protected String url(String name) {
		try {
			StringBuilder builder = new StringBuilder();
			builder.append("http://www.tianyancha.com/search?key=");
			builder.append(URLEncoder.encode(name, "UTF-8"));
			builder.append("&checkFrom=searchBox");
			return builder.toString();
		} catch (UnsupportedEncodingException e) {
		}
		return null;
	}
	
	protected String url1(String name) {
		try {
			StringBuilder builder = new StringBuilder();
			builder.append("http://pv.tianyancha.com/pv?url=/search?key=");
			builder.append(URLEncoder.encode(name, "UTF-8"));
			builder.append("&checkFrom=searchBo");
			return builder.toString();
		} catch (UnsupportedEncodingException e) {
		}
		return null;
	}
	
	protected String url2(String name) {
		try {
			StringBuilder builder = new StringBuilder();
			builder.append("http://www.tianyancha.com/v2/search/");
			builder.append(URLEncoder.encode(name, "UTF-8"));
			builder.append(".json?");
			return builder.toString();
		} catch (UnsupportedEncodingException e) {
		}
		return null;
	}

}
