/**
 * 
 */
package hh.learnj.httpclient.proxy.usecase;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpHost;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hh.learnj.httpclient.proxy.ProxyValidator;
import hh.learnj.httpclient.usecase.qichacha.DB;
import hh.learnj.httpclient.usecase.qichacha.DB.Hander;

/**
 * @author huzexiong
 *
 */
public class IpValidator {

	private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36";
	private final Logger logger = LoggerFactory.getLogger(ProxyValidator.class);

	public static void main(String[] args) {
		new IpValidator().lookupDB();
	}

	protected void debug(String format, Object... args) {
		if (logger.isDebugEnabled()) {
			logger.debug(format, args);
		}
	}

	protected void error(String format, Object... args) {
		if (logger.isErrorEnabled()) {
			logger.error(format, args);
		}
	}

	protected void info(String format, Object... args) {
		if (logger.isInfoEnabled()) {
			logger.info(format, args);
		}
	}

	public void lookupDB() {
		DB.handle(new Hander() {
			@Override
			public String getTableName() {
				return "proxy";
			}
			@Override
			public void handle(Statement stmt) throws SQLException {
				String sql = MessageFormat.format("SELECT IP, PORT FROM {0} ORDER BY ID ASC", getTableName());
				ResultSet resultSet = stmt.executeQuery(sql);
				while (resultSet.next()) {
					String ip = resultSet.getString("IP");
					int port = resultSet.getInt("PORT");
					if (port <= 0 || StringUtils.isBlank(ip)) {
						continue;
					}
					debug(MessageFormat.format("查询[{0}][{1}]", ip, port));
					update(getTableName(), ip, port);
				}
			}
		});
	}
	
	public void update(final String tableName, final String ip, final int port) {
		DB.handle(new Hander() {

			@Override
			public String getTableName() {
				return tableName;
			}

			@Override
			public void handle(Statement stmt) throws SQLException {
				if (port <= 0 || StringUtils.isBlank(ip)) {
					return;
				}
				debug(MessageFormat.format("查询[{0}][{1}]", ip, port));
				Map<String, String> update = new HashMap<String, String>();
				update.put("ip", ip);
				long start = System.currentTimeMillis();
				if (validate(ip, port)) {
					long end = System.currentTimeMillis() - start;
					update.put("timeout", String.format("%sms", end));
					update.put("validate", "Y");
				} else {
					update.put("validate", "N");
				}
				update.put("lastUpdated", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
				DB.update(tableName, update, "ip", stmt);
			}
			
		});
	}

	public boolean validate(String ip, int port) {
		return check(ip, port);
	}
	
	public boolean check(String ip, int port) {
		try {
			URL url = new URL("http://www.baidu.com");
			// 创建代理服务器
			InetSocketAddress addr = new InetSocketAddress(ip, port);
			Proxy proxy = new Proxy(Proxy.Type.HTTP, addr); // http 代理
			URLConnection conn = url.openConnection(proxy);
			InputStream in = conn.getInputStream();
			String s = IOUtils.toString(in, "UTF-8");
			if (s.indexOf("百度") > 0) {
				return true;
			}
		} catch (MalformedURLException e) {
			error(e.getMessage());
		} catch (IOException e) {
			error(e.getMessage());
		}
		return false;
	}
	
	public boolean validate(String ip, int port, boolean check) {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		try {
			HttpHost target = new HttpHost("www.whoishostingthis.com", 80, "http");
			HttpHost proxy = new HttpHost(ip, port, "http");
			RequestConfig config = RequestConfig.custom() //
					.setProxy(proxy) //
					.build(); //
			HttpGet request = new HttpGet("/tools/user-agent/");
			request.setHeader("User-agent", USER_AGENT);
			request.setConfig(config);//

			debug("Executing request {} to {} via {}", request.getRequestLine(), target, proxy);

			CloseableHttpResponse response = httpclient.execute(target, request);
			debug("----------------------------------------");
			StatusLine statusLine = response.getStatusLine();
			debug("{}", statusLine);
			if (statusLine.getStatusCode() != HttpStatus.SC_OK) {
				return false;
			}
			if (check) {
				String html = EntityUtils.toString(response.getEntity());
				Document doc = Jsoup.parse(html);
				Element element = doc.select("div#user-agent").first();
				if (null != element) {
					Element userAgentElement = element.select("div.user-agent").first();
					if (null != userAgentElement) {
						debug(MessageFormat.format("User Agent is: {0}", userAgentElement.text()));
					}
					Element ipElement = element.select("div.ip").first();
					if (null != ipElement) {
						debug(MessageFormat.format("IP Address is: {0}, Proxy IP:[{1}:{2}]", ipElement.text(), ip, port));
					}
				}
			}
		} catch (Exception e) {
			error("{}", e.getMessage());
			return false;
		} finally {
			try {
				httpclient.close();
			} catch (Exception e) {
			}
		}
		return true;
	}

}
