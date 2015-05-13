package hh.learnj.httpclient.usecase.www12333sh;

import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;

public interface MyHttpBuilder {
	
	public String getUrl();
	
	public void handle(HttpEntity entity);
	
	public List<NameValuePair> params();

}
