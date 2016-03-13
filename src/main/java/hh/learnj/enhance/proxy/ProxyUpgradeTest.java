package hh.learnj.enhance.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ProxyUpgradeTest {

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		List<Object> list = new ArrayList<Object>();
		Collection<Object> proxy = (Collection<Object>)getProxy(list, new ProxyAdviceTest());
		proxy.add("OK");
		proxy.add(123);
		System.out.println(proxy);
	}

	public static Object getProxy(final Object target, final ProxyAdvice advice) {
		return Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), new InvocationHandler() {
			@Override
			public Object invoke(Object proxy, Method method, Object[] args)
					throws Throwable {
				advice.beforeMethod(method);
				Object returnValue = method.invoke(target, args);
				advice.afterMethod(method);
				return returnValue;
			}
		});
	}

}
