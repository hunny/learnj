package hh.learnj.designpattern.decorator.reflect;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import javassist.Modifier;

public class DecorateAnimal implements Animal {
	
	/**
	 * 被包装的动物
	 */
	private Animal animal;
	
	/**
	 * 使用哪一种包装器
	 */
	private Class<? extends Feature> clazz;
	
	public DecorateAnimal(Animal animal, Class<? extends Feature> clazz) {
		this.animal = animal;
		this.clazz = clazz;
	}

	@Override
	public void doStuff() {
		InvocationHandler handler = new InvocationHandler() {
			/**
			 * 具体包装行为
			 */
			@Override
			public Object invoke(Object proxy, Method method, Object[] args)
					throws Throwable {
				Object object = null;
				if (Modifier.isPublic(method.getModifiers())) {
					object = method.invoke(clazz.newInstance(), args);
				}
				animal.doStuff();
				return object;
			}
			
		};
		ClassLoader loader = getClass().getClassLoader();
		Feature proxy = (Feature)Proxy.newProxyInstance(loader, clazz.getInterfaces(), handler);
		proxy.load();
	}

}
