package hh.learnj.testj;

import java.io.FileInputStream;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.junit.Test;

public class HttpPostXml {
	
	protected static final Logger logger = Logger.getLogger(HttpPostXml.class);
	
	@Test
	public void post() throws Exception{
		
		String requestAddress = System.getProperty("url");
		String bodyFilePath = System.getProperty("path");
		
		if (null == requestAddress || null == bodyFilePath) {
			logger.info("usage example: -Durl=http://localhost:8080/test/req -Dpath=C:/Users/user1/Desktop/req.log");
			return;
		}
		
        HttpClient client = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(requestAddress);
        InputStream inputStream = new FileInputStream(bodyFilePath);
        String body = IOUtils.toString(inputStream, "UTF-8");
        HttpEntity entity = new ByteArrayEntity(body.getBytes("UTF-8"));
        post.setEntity(entity);
        HttpResponse response = client.execute(post);
        String result = EntityUtils.toString(response.getEntity());
        logger.info(result);
        
    }
}
