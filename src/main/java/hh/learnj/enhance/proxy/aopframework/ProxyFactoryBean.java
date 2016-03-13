package hh.learnj.enhance.proxy.aopframework;

import hh.learnj.enhance.proxy.ProxyAdvice;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ProxyFactoryBean {
	protected Object target = null;
	protected ProxyAdvice advice = null;
	
	public Object getTarget() {
		return target;
	}

	public void setTarget(Object target) {
		this.target = target;
	}

	public ProxyAdvice getAdvice() {
		return advice;
	}

	public void setAdvice(ProxyAdvice advice) {
		this.advice = advice;
	}

	public Object getProxy() {
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
