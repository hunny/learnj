package hh.learnj.enhance.reflect;

import hh.learnj.enhance.reflect.beans.ReflectPoint;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;

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
		
		int [] a1 = new int[] {1, 2, 3};
		int [] a2 = new int[4];
		int [][] a3 = new int[3][4];
		String [] a4 = new String[] {"a", "b", "c"}; 
		Integer [] a5 = new Integer[] {5, 6, 7};
		System.out.println(a1.getClass() == a2.getClass());
//		System.out.println(a1.getClass() == a4.getClass());
//		System.out.println(a1.getClass() == a3.getClass());
		System.out.println(a1.getClass().getSuperclass().getName());
		System.out.println(a3.getClass().getSuperclass().getName());
		System.out.println(a4.getClass().getSuperclass().getName());
		System.out.println(Arrays.asList(a1));
		System.out.println(Arrays.asList(a4));
		System.out.println(Arrays.asList(a5));
		
		printObjects(a4);
		printObjects("Hello World.");

	}
	
	@SuppressWarnings("rawtypes")
	public static void printObjects(Object obj) {
		Class clazz = obj.getClass();
		if (clazz.isArray()) {
			int len = Array.getLength(obj);
			for (int i = 0; i < len; i++) {
				System.out.println(Array.get(obj, i));
			}
		} else {
			System.out.println(obj);
		}
	}
	
	public static void staticMethod() {
		System.out.println("static method test.");
	}

}
