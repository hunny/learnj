package hh.learnj.enhance.proxy.aopframework;

import java.io.InputStream;

public class AOPFrameworkTest {

	public static void main(String[] args) {
		InputStream ins = AOPFrameworkTest.class.getResourceAsStream("config.properties");
		Object bean = new BeanFactory(ins).getBean("xxx");
		if (null == bean) {
			System.err.println("Bean not found!");
		} else {
			System.out.println(bean.getClass().getCanonicalName());
		}

	}

}
