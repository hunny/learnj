/**
 * 
 */
package hh.learnj.httpclient.usecase.proxy;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

/**
 * @author huzexiong
 *
 */
public class Req {

	public static String get(String url, String charset, Parser parser) {
		if (null == parser) {
			throw new IllegalArgumentException("parser不能为空。");
		}
		CloseableHttpClient httpclient = null;
		try {
			httpclient = HttpClients.createDefault();
			HttpGet httpget = new HttpGet(url);
			// 执行get请求.
			CloseableHttpResponse response = httpclient.execute(httpget);
			try {
				// 获取响应实体
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					// 打印响应内容
					String html = EntityUtils.toString(entity, charset);
					parser.parse(html);
					return html;
				}
			} finally {
				response.close();
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// 关闭连接,释放资源
			try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

}
