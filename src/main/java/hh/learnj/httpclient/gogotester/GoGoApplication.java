/**
 * 
 */
package hh.learnj.httpclient.gogotester;

import java.util.UUID;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;

/**
 * @author huzexiong
 *
 */
public class GoGoApplication {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.setProperty("http.maxTotal", "100");
		System.setProperty("http.maxPerRoute", "10");
		System.setProperty("http.connectionTimeout", "5000");
		System.setProperty("http.socketTimeout", "5000");
		String [] ips = "216.58.201.91|216.58.201.68|172.217.29.34|172.217.21.30|216.58.207.64|208.117.233.92|66.102.12.181|216.58.206.134|74.125.143.81|216.58.202.223|216.58.206.61|108.177.96.190|216.58.202.131|216.58.203.130|172.217.20.164|209.85.232.110|216.58.206.164|216.58.205.111|172.217.8.151".split("\\|");
		for (String ip : ips) {
			HttpGet httpReq = new HttpGet("https://" + ip + "/?" + UUID.randomUUID().toString());
			HttpResponse response = null;
			try {
				response = HttpConnect.execute(httpReq);
				System.out.println("code: " + response.getStatusLine().getStatusCode());
				if (response.getStatusLine().getStatusCode() == 200) {
					String serverName = response.getLastHeader("Server").getValue();
					System.out.println("server name: " + serverName);
					if (serverName.equals("gws")) {
						System.out.print(ip + "|");
						System.out.println();
						System.out.println("shutting down...");
					}
				}
			} catch (Exception e) {
				System.err.println(e.getMessage());;
			}
		}
	}

}
