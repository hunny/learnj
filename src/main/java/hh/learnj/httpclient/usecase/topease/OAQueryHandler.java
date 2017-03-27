package hh.learnj.httpclient.usecase.topease;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.ClientProtocolException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hh.learnj.httpclient.usecase.qichacha.DB.Hander;

public class OAQueryHandler implements Hander {
	
	private static final Logger logger = LoggerFactory.getLogger(OAQueryHandler.class);

	protected void debug(String format, Object... arguments) {
		if (logger.isDebugEnabled()) {
			logger.debug(format, arguments);
		}
	}
	
	@Override
	public String getTableName() {
		return "company";
	}

	@Override
	public void handle(Statement stmt) throws SQLException {
		int offset = 0;
		int limit = 10;
		TopeaseHttp http = new TopeaseHttp();
		do {
			List<String> names = query(stmt, offset, limit);
			if (names.isEmpty()) {
				break;
			}
			for (String name : names) {
				netGet(http, name);
			}
			offset++;
		} while (true);
	}

	public void netGet(TopeaseHttp http, final String name) {
		String url = url(name);
		debug("公司[{}]地址[{}]", name, url);
		if (StringUtils.isNotBlank(url)) {
			try {
				// Delay to fetch
				TimeUnit.MILLISECONDS.sleep(new Random().nextInt(500));
				http.get(url, "UTF-8", new OAHttpParser(name));
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public List<String> query(Statement stmt, int offset, int limit) throws SQLException {
		String sql = String.format("select id, name from %s where mobile is not null order by id asc limit %d, %d",
				getTableName(), limit * offset, limit);
		ResultSet resultSet = stmt.executeQuery(sql);
		List<String> names = new ArrayList<String>();
		while (resultSet.next()) {
			String name = resultSet.getString("name");
			String id = resultSet.getString("id");
			logger.debug("id[{}], name[{}]", id, name);
			names.add(name);
		}
		return names;
	}

	protected String url(String name) {
		try {
			StringBuilder builder = new StringBuilder();
			builder.append("http://oa.topease.cn/Customer/VailCusResult.bee?id=-1&companyName=");
			builder.append(URLEncoder.encode(name, "UTF-8"));
			builder.append("&callback=callback0615496108&_=");
			builder.append(new Date().getTime());
			return builder.toString();
		} catch (UnsupportedEncodingException e) {
		}
		return null;
	}

}
