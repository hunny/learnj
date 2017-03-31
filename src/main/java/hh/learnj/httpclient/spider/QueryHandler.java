package hh.learnj.httpclient.spider;

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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hh.learnj.httpclient.usecase.qichacha.DB.Hander;

public class QueryHandler implements Hander {

	private final Logger logger = LoggerFactory.getLogger(QueryHandler.class);
	private final int limit = 5;

	private PhantomjsSpider spider;
	private ListResultHandler listHandler;

	public QueryHandler(PhantomjsSpider spider, ListResultHandler listHandler) {
		this.spider = spider;
		this.listHandler = listHandler;
	}

	@Override
	public String getTableName() {
		return "company";
	}

	@Override
	public void handle(Statement stmt) throws SQLException {
		String id = "0";
		do {
			String sql = String.format(
					"select id, name from %s where id > %s and mobile is null order by id asc limit %d",
					getTableName(), id, limit);
			ResultSet resultSet = stmt.executeQuery(sql);
			List<String> names = new ArrayList<String>();
			while (resultSet.next()) {
				String name = resultSet.getString("name");
				id = resultSet.getString("id");
				debug("id[{}], name[{}]", id, name);
				names.add(name);
			}
			if (names.isEmpty()) {
				debug("抓取完毕。");
				return;
			}
			for (String name : names) {
				String url = url(name);
				debug(String.format("公司[%s]地址[%s]", name, url));
				if (StringUtils.isBlank(url)) {
					continue;
				}
				try {
					TimeUnit.MILLISECONDS.sleep(new Random().nextInt(1000));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				listHandler.setSpider(spider);
				listHandler.getDetailHandler().setName(name);
				spider.justDoIt(url, listHandler);
			}
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
		return null;//"http://www.whoishostingthis.com/tools/user-agent/";
	}

	protected void debug(String format, Object... arguments) {
		if (logger.isDebugEnabled()) {
			logger.debug(format, arguments);
		}
	}

}
