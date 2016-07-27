package hh.learnj.designpattern.proxy.common;

/**
 * 动态代理的场景类
 * 
 * @author hunnyhu
 *
 */
public class CommonProxyClient {

	public static void main(String[] args) {
		Subject proxy = new Proxy();
		proxy.request();
	}

}
