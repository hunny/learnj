package hh.learnj.enhance.reflect;

import hh.learnj.enhance.reflect.beans.ReflectPoint;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ReflectTest {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void main(String[] args) throws Exception {
		
		Constructor [] constructors = Class.forName("java.lang.String").getConstructors();
		System.out.println(constructors.getClass());
		
		Constructor<String> constructor = (Constructor<String>) Class.forName("java.lang.String").getConstructor(StringBuffer.class);
		
		String nString = constructor.newInstance(new StringBuffer("String Buffer Test."));
		System.out.println(nString);
		System.out.println(nString.charAt(2));
		Method method = String.class.getMethod("charAt", int.class);
		System.out.println(method.invoke(nString, 3));
		//如果传递给invoke的第一个参数是null，意味着它调用的是一个静态的方法
		Method staticMethod = ReflectTest.class.getMethod("staticMethod");
		System.out.println(staticMethod.invoke(null));
		
		ReflectPoint point = new ReflectPoint(3, 5, "Test");
		Field field = point.getClass().getField("y");
		System.out.println(field);
		System.out.println(field.getName());
		System.out.println(field.get(point));
		
		Field fieldx = point.getClass().getDeclaredField("x");
		fieldx.setAccessible(true);//暴力反射取值
		System.out.println(fieldx.get(point));
		
		Field [] fields = ReflectPoint.class.getFields();
		System.out.println("Original value:" + point.abc);
		for (Field tmpField : fields) {
			if (tmpField.getType() == String.class) {
				String name = tmpField.getName();
				System.out.println(name + "'s value will be changed:");
				String oldValue = (String) tmpField.get(point);
				String newValue = oldValue.replaceAll("t", "OK");
				tmpField.set(point, newValue);
			}
		}
		System.out.println("Changed value:" + point.abc);

	}
	
	public static void staticMethod() {
		System.out.println("static method test.");
	}

}
