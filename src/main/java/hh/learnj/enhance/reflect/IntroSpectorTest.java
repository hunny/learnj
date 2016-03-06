package hh.learnj.enhance.reflect;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;

public class IntroSpectorTest {

	public static void main(String[] args) throws Exception {
		BeanPoint point = new BeanPoint(3, 5);
		String propertyName = "key";
		propertyDescriptorTest(point, propertyName);

		BeanInfo beanInfo = Introspector.getBeanInfo(point.getClass());
		PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();
		for (PropertyDescriptor pd : pds) {
			if (pd.getName().equals(propertyName)) {
				Method method = pd.getReadMethod();
				System.out.println("By BeanInfo:" + method.invoke(point));
			}
		}
		BeanUtils.setProperty(point, propertyName, "13");
		BeanUtils.setProperty(point, "date.time", 1000000);
		System.out.println(BeanUtils.getProperty(point, propertyName));
		BeanUtils.getProperty(point, "date");
		
		PropertyUtils.setProperty(point, propertyName, 25);
		System.out.println(PropertyUtils.getProperty(point, propertyName)
				.getClass().getName());

	}

	public static void propertyDescriptorTest(BeanPoint point,
			String propertyName) throws IntrospectionException,
			IllegalAccessException, InvocationTargetException {
		PropertyDescriptor pd = new PropertyDescriptor(propertyName,
				point.getClass());
		Method method = pd.getReadMethod();
		Object retVal = method.invoke(point);
		System.out.println(retVal);
		Method setterMethod = pd.getWriteMethod();
		setterMethod.invoke(point, 7);
		System.out.println(point.getKey());
		System.out.println(method.invoke(point));
	}

}
