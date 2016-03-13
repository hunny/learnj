package hh.learnj.enhance.proxy;

import java.lang.reflect.Method;

public interface ProxyAdvice {
	
	public void beforeMethod(Method method);
	
	public void afterMethod(Method method);

}
