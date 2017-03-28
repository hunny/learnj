package hh.learnj.httpclient.usecase.topease;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.message.BasicNameValuePair;
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
//				http.get(url, "UTF-8", new OAHttpParser(name));
				http.getPage(url, "UTF-8", new OAHttpParser(name));
//				post(http, name);
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
		String sql = String.format("select id, name from %s order by id asc limit %d, %d",
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
	
	protected void post(TopeaseHttp http, String name) throws Exception {
		List<NameValuePair> postParams = new ArrayList<NameValuePair>();
		postParams.add(new BasicNameValuePair("id", "-1"));
		postParams.add(new BasicNameValuePair("companyName", escape(name)));
		postParams.add(new BasicNameValuePair("callback", "callback" + new Date().getTime()));
		postParams.add(new BasicNameValuePair("_", "" + new Date().getTime()));
		http.post("http://oa.topease.cn/Customer/VailCusResult.bee", "UTF-8", postParams, new OAHttpParser(name));
	}

	protected String url(String name) {
		try {
			StringBuilder builder = new StringBuilder();
			builder.append("http://oa.topease.cn/Customer/VailCusResult.bee?id=-1&companyName=");
			builder.append(escape(name));
			builder.append("&callback=callback0615496108&_=");
			builder.append(new Date().getTime());
			return builder.toString();
		} catch (Exception e) {
		}
		return null;
	}
	
	public static String escape(String str) {
		return escape(str, true);
	}

	public static String escape(String str, boolean escape) {
		if (StringUtils.isBlank(str)) {
			return null;
		}
		String func = escape ? "escape" : "unescape";
		ScriptEngineManager sem = new ScriptEngineManager();
		ScriptEngine engine = sem.getEngineByExtension("js");
		try {
			Object res = engine.eval(String.format("%s(\"%s\")", func, str));
			return (String)res;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

//	public static void main(String[] args) throws Exception {
//		new TopeaseHttp().getPage("http://oa.topease.cn/Customer/VailCusResult.bee?id=-1&companyName=%u4E0A%u6D77%u5409%u6B23%u8D38%u6613%u6709%u9650%u516C%u53F8", "UTF-8", new OAHttpParser(""));;
//	}
	
}
