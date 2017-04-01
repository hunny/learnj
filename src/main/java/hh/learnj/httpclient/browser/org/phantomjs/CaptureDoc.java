/**
 * 
 */
package hh.learnj.httpclient.browser.org.phantomjs;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.MessageFormat;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * @author huzexiong
 *
 */
public class CaptureDoc {

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		if (null == args || args.length == 0) {
			return;
		}
		String exe = args[0];
		String code = args[1];
		String url = args[2];
		if (StringUtils.isBlank(exe) || StringUtils.isBlank(code)) {
			System.out.println("参数不正确。");
			return;
		}
		String dir = new File(exe).getParent();
		String message = get(exe, code, url);
		Document doc = Jsoup.parse(message.toString(), "UTF-8");
		Elements elements = doc.select(".grid_3.pull_9 > ul li");
		for (Element element : elements) {
			Element label = element.select("div.nav-group-label").first();
			if (null == label) {
				continue;
			}
			String labelName = StringUtils.trim(label.text());
			Elements hrefs = element.select("a.nav-item-name");
			for (Element href : hrefs) {
				String link = href.attr("href");
				String path = "\"" + labelName + "/" + StringUtils.trim(href.text()) + "\"";
				System.out.println(String.format("%s %s %s %s", exe, code, link, path));
				get(exe, code, url, path);
			}
		}
	}

	private static String get(String exe, String code, String url) throws UnsupportedEncodingException, IOException {
		return get(exe, code, url, null);
	}

	/**
	 * @param exe
	 * @param code
	 * @param url
	 * @return
	 * @throws IOException
	 * @throws UnsupportedEncodingException
	 */
	private static String get(String exe, String code, String url, String captureName)
			throws IOException, UnsupportedEncodingException {
		if (StringUtils.isBlank(captureName)) {
			captureName = "";
		}
		Runtime rt = Runtime.getRuntime();
		String exec = MessageFormat.format("{0} {1} {2}", exe, code, url, captureName);
		Process p = rt.exec(exec);
		InputStream is = p.getInputStream();
		StringBuilder message = new StringBuilder();
		String line = null;
		BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
		while ((line = br.readLine()) != null) {
			message.append(line);
		}
		return message.toString();
	}

}
