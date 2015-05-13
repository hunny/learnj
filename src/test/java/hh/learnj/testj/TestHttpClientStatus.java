package hh.learnj.testj;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.Assert;
import org.junit.Test;

public class TestHttpClientStatus {

	@Test
	public void testStatus() throws ClientProtocolException, IOException {
		HttpClient client = HttpClientBuilder.create().build();
		HttpResponse response = client.execute(new HttpGet(
				"http://www.baidu.com"));
		int statusCode = response.getStatusLine().getStatusCode();
		Assert.assertEquals(statusCode, HttpStatus.SC_OK);
	}

}
