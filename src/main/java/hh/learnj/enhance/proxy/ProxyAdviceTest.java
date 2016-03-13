package hh.learnj.enhance.proxy;

import java.lang.reflect.Method;

public class ProxyAdviceTest implements ProxyAdvice {

	@Override
	public void beforeMethod(Method method) {
		System.out.println("[-][>] InvocationHandler Begin: " + method.getName());
	}

	@Override
	public void afterMethod(Method method) {
		System.out.println("[-][>] InvocationHandler End: " + method.getName());
	}
}
