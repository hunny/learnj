package hh.learnj.testj.webservice;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.xml.ws.Endpoint;

@WebService
public class Hi {
	
	@WebMethod
	public String hunny(String name) {
		System.out.println("Execute method hunny.");
		return "Hi, I'm WebService call method by parameter name " + name;
	}

	public static void main(String[] args) {
		System.out.println("Endpoint starting...");
		Endpoint.publish("http://localhost:9999/service/Hi", new Hi());
	}

}
