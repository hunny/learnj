/**
 * 
 */
package hh.learnj.httpclient.spider;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.MessageFormat;
import java.util.Date;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author huzexiong
 *
 */
public class PhantomjsSpider {

	private final Logger logger = LoggerFactory.getLogger(PhantomjsSpider.class);
	
	private String exe = null;
	private String code = null;
	private String url = null;
	
	public PhantomjsSpider(String exe, String code) {
		this.exe = exe;
		this.code = code;
	}
	
	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		Runtime rt = Runtime.getRuntime();
		String url = "http://www.tianyancha.com/search?key=%E4%B8%8A%E6%B5%B7%E4%B8%89%E7%9F%B3%E6%9C%8D%E8%A3%85%E6%9C%8D%E9%A5%B0%E6%9C%89%E9%99%90%E5%85%AC%E5%8F%B8&checkFrom=searchBox";
		String exec = MessageFormat.format("{0} {1} {2}", "C:/work/phantomjs-2.1.1-windows/bin/phantomjs.exe", "C:/work/phantomjs-2.1.1-windows/bin/code.js", url);
		Process p = rt.exec(exec);
		InputStream is = p.getInputStream();
		StringBuilder message = new StringBuilder();
		String line = null;
		BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
		while ((line = br.readLine()) != null) {
			message.append(line);
		}
		Document doc = Jsoup.parse(message.toString(), "UTF-8");
		Element element = doc.select(".query_name").first();
		System.out.println(element.toString());
	}
	
	public void justDoIt(String url, ResultHandler handler) {
		setUrl(url);
		Runtime rt = Runtime.getRuntime();
		String exec = MessageFormat.format("{0} {1} {2}", this.exe, this.code, url);
		
		debug(exec);
		InputStream is = null;
		try {
			Process p = rt.exec(exec);
			is = p.getInputStream();
			StringBuilder message = new StringBuilder();
			String line = null;
			BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			while ((line = br.readLine()) != null) {
				message.append(line);
			}
			if (message.length() == 0) {
				debug("地址没有检测到返回值[]", url);
				return;
			}
			if (null != handler) {
				handler.handle(message.toString());
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (Exception e) {}
		}
	}
	
	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	protected void debug(String format, Object...args) {
		if (logger.isDebugEnabled()) {
			logger.debug(format, args);
		}
	}

}
