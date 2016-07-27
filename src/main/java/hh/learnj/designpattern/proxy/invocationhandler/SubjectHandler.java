package hh.learnj.designpattern.proxy.invocationhandler;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class SubjectHandler implements InvocationHandler {
	
	/**
	 * 被代理的对象
	 */
	private Subject subject;
	
	public SubjectHandler(Subject subject) {
		this.subject = subject;
	}

	/**
	 * 委托处理方法
	 */
	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		System.out.println("预处理");
		System.out.println("预处理代理类名" + proxy.getClass().getName());
		//直接调用被代理类的方法
		Object object = method.invoke(subject, args);
		System.out.println("后处理");
		return object;
	}

}
