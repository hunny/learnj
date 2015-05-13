package hh.learnj.httpclient.usecase.www12333sh;

import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;


public class HttpClientSocialInsure {

	public static void main(String[] args) {
		checkIndexPage();
	}
	
	public static void checkPostPage() {
		new AbstractMyHttpBuilder() {
			
			@Override
			public String getUrl() {
				return "http://www.12333sh.gov.cn/grxx/login.jsw";
			}
			
			@Override
			public List<NameValuePair> params() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public void handle(HttpEntity entity) {
				// TODO Auto-generated method stub
				
			}
		};
	}

	public static void checkIndexPage() {
		
		new AbstractMyHttpBuilder() {
			
			@Override
			public String getUrl() {
				return "http://www.12333sh.gov.cn/grxx/grxx.jsp";
			}

			@Override
			public List<NameValuePair> params() {
				return null;
			}

			@Override
			public void handle(HttpEntity entity) {
				System.out.println("处理完毕。");
			}
			
		}.doGet();
	}

}
