package hh.learnj.designpattern.proxy.invocationhandler;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * 动态代理的场景类
 * 
 * @author hunnyhu
 *
 */
public class InvocationHandlerProxyClient {

	public static void main(String[] args) {
		// 具体主题角色，也就是被代理类
		Subject subject = new RealSubject();
		// 代理实例的处理handler
		InvocationHandler invocationHandler = new SubjectHandler(subject);
		// 当前加载器
		ClassLoader classLoader = subject.getClass().getClassLoader();
		Subject proxy = (Subject) Proxy.newProxyInstance(classLoader, subject
				.getClass().getInterfaces(), invocationHandler);
		proxy.request();
	}

}
