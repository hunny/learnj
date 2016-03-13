package hh.learnj.enhance.proxy.aopframework;

import hh.learnj.enhance.proxy.ProxyAdvice;

import java.io.InputStream;
import java.util.Properties;

public class BeanFactory {
	
	protected Properties prop = new Properties();
	
	public BeanFactory(InputStream ins) {
		try {
			prop.load(ins);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("rawtypes")
	public Object getBean(String name) {
		String className = prop.getProperty(name);
		Object bean = null;
		try {
			Class clazz = Class.forName(className);
			bean = clazz.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (bean instanceof ProxyFactoryBean) {
			ProxyFactoryBean proxyFactoryBean = ((ProxyFactoryBean) bean);
			Object proxy = null;
			try {
				ProxyAdvice advice = (ProxyAdvice)Class.forName(prop.getProperty(name + ".advice")).newInstance();
				Object target = Class.forName(prop.getProperty(name + ".target")).newInstance();
				proxyFactoryBean.setAdvice(advice);
				proxyFactoryBean.setTarget(target);
				return ((ProxyFactoryBean) bean).getProxy();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return proxy;
		}
		return bean;
	}

}
