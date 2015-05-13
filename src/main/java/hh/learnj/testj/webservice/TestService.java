package hh.learnj.testj.webservice;

import java.net.URL;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;

//import client.HiService;


public class TestService {

	public static void main(String[] args) throws Exception {
	}
	
	public void invokeWithCommon() {
		//WebService Source generate by command:
		//wsimport -p client -keep http://localhost:9999/service/Hi?wsdl
//		HiService service = new HiService();
//        client.Hi helloProxy = service.getHiPort();     
//        String hello = helloProxy.hunny("世界");  
//        System.out.println(hello);  
	}
	
	public void invokeWithAxis() throws Exception {
		//创建服务对象
		Service service = new Service();
		//创建调用对象
		Call call = (Call)service.createCall();
		//设置访问地址
		String url = "http://localhost:9999/service/Hi";
		call.setTargetEndpointAddress(new URL(url));
		//设置服务器中被调用的方法名
		call.setOperation("hunny");
		//进行调用
		Object o = call.invoke(new Object[] {"OK"});
		System.out.println("object " + o);
	}

}
