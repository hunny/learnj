package hh.learnj.enhance.proxy;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ProxyTest {

	public static void main(String[] args) throws Exception {
		//通常使用接口类自己本身的类加载器作为第一个参数
		Class<?> classProxy = Proxy.getProxyClass(Collection.class.getClassLoader(), Collection.class);
		System.out.println("Class Name:" + classProxy.getName() + ", Class Canonial Name:" + classProxy.getCanonicalName());
		System.out.println("[+][-] All Constructors:");
		Constructor<?> [] constructors = classProxy.getConstructors();
		for (Constructor<?> constructor : constructors) {
			String name = constructor.getName();
			//StirngBuilder unsafe thread, StringBuffer safe thread.
			StringBuilder sBuilder = new StringBuilder(name);
			sBuilder.append('(');
			Class<?> [] classParams = constructor.getParameterTypes();
			for (Class<?> params : classParams) {
				sBuilder.append(params.getName()).append(',');
			}
			if (null != classParams && classParams.length > 0) {
				sBuilder.deleteCharAt(sBuilder.length() - 1);
			}
			sBuilder.append(')');
			System.out.println(sBuilder.toString());
		}
		System.out.println("[+][-] All Methods:");
		Method [] methods = classProxy.getMethods();
		for (Method method : methods) {
			String name = method.getName();
			//StirngBuilder unsafe thread, StringBuffer safe thread.
			StringBuilder sBuilder = new StringBuilder();
			Class<?> returnType = method.getReturnType();
			sBuilder.append(returnType.getCanonicalName());
			sBuilder.append(' ');
			sBuilder.append(name);
			sBuilder.append('(');
			Class<?> [] classParams = method.getParameterTypes();
			for (Class<?> params : classParams) {
				sBuilder.append(params.getCanonicalName()).append(',');
			}
			if (null != classParams && classParams.length > 0) {
				sBuilder.deleteCharAt(sBuilder.length() - 1);
			}
			sBuilder.append(')');
			System.out.println(sBuilder.toString());
		}
		System.out.println("[+][-]Proxy Class New Instance");
		Constructor<?> object = classProxy.getConstructor(InvocationHandler.class);
		Collection<?> proxy = (Collection<?>) object.newInstance(new InvocationHandler() {
			@Override
			public Object invoke(Object proxy, Method method, Object[] args)
					throws Throwable {
				return null;
			}
		});
		System.out.println(proxy);
		@SuppressWarnings("unchecked")
		Collection<Object> proxy1 = (Collection<Object>)Proxy.newProxyInstance(Collection.class.getClassLoader(), new Class[] {
			Collection.class
		}, new InvocationHandler() {
			List<Object> target = new ArrayList<Object>();
			@Override
			public Object invoke(Object proxy, Method method, Object[] args)
					throws Throwable {
				System.out.println("[-][>] InvocationHandler Begin: " + method.getName());
				Object returnValue = method.invoke(target, args);
				System.out.println("[-][>] InvocationHandler End: " + method.getName());
				return returnValue;
			}
		});
		proxy1.add("OK");
		proxy1.add(123);
		proxy1.add(Boolean.class);
		System.out.println("Element Count:" + proxy1.size());
		System.out.println(proxy1);
	}

}
