package hh.learnj.testj;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestHttpClient {
	
	private static final Logger logger = Logger.getLogger(TestHttpClient.class);
	
	private CloseableHttpClient httpClient = null;
	
	@Before
	public void before() {
		httpClient = HttpClients.createDefault();
	}

	@Test
	public void testHttpClientForGet() throws Exception {
//		HttpGet httpget = new HttpGet("http://www.baidu.com");
		HttpGet httpget = new HttpGet("http://192.168.1.104:8080/sweb/userController/2/showUserForJson.do");
		httpget.addHeader(new BasicHeader("Pragma", "no-cache"));
		httpget.addHeader(new BasicHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_8_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2272.76 Safari/537.36"));
		httpget.addHeader("Accept-Language", "en-US,en;q=0.8,zh-CN;q=0.6,zh;q=0.4,zh-TW;q=0.2");
		// ==============httppGet开始=================
		CloseableHttpResponse httpReponse = httpClient.execute(httpget);
		try {
			// 获取状态行
			System.out.println(httpReponse.getStatusLine());
			logger.debug(httpReponse.getStatusLine());
			HttpEntity entity = httpReponse.getEntity();
			// 返回内容
			System.out.println(EntityUtils.toString(entity));
		} finally {
			httpReponse.close();
		}
		// ===============httppPst结束=================
	}
	
	@Test
	public void testHttpClientQuickGet() {
//		Content content  =  Request.Get("http://www.baidu.com").execute().returnContent();
//	    System.out.println(content);
	}
	
	@Test
	public void testHttpClientForPost() throws Exception {
		// ===============httppPst请求开始==============
		HttpPost httpPost = new HttpPost("http://192.168.1.104:8080/sweb/userController/showUserFromParam.do");
		httpPost.addHeader(new BasicHeader("Pragma", "no-cache"));
		httpPost.addHeader(new BasicHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_8_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2272.76 Safari/537.36"));
		httpPost.addHeader("Accept-Language", "en-US,en;q=0.8,zh-CN;q=0.6,zh;q=0.4,zh-TW;q=0.2");
		
		List<BasicNameValuePair> nvps = new ArrayList<BasicNameValuePair>();
		nvps.add(new BasicNameValuePair("id", "4"));
		nvps.add(new BasicNameValuePair("username", "myusername"));
		nvps.add(new BasicNameValuePair("password", "mypassword"));

		httpPost.setEntity(new UrlEncodedFormEntity(nvps));
		CloseableHttpResponse httppHttpResponse2 = httpClient.execute(httpPost);

		try {
			System.out.println(httppHttpResponse2.getStatusLine());
			System.out.println(EntityUtils.toString(httppHttpResponse2
					.getEntity()));
		} finally {
			httppHttpResponse2.close();
		}
		// ===============httppPst请求结束==============
	}
	
	@After
	public void after() {
		try {
			httpClient.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
