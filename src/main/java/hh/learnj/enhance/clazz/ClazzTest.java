package hh.learnj.enhance.clazz;

import java.util.Date;

public class ClazzTest {

	@SuppressWarnings("rawtypes")
	public static void main(String[] args) throws Exception {
		Class<Date> clazz = Date.class;
		System.out.println(clazz);
		Date date = new Date();
		Class clazz2 = date.getClass();
		System.out.println(clazz2);
		Class clazz3 = Class.forName("java.util.Date");
		System.out.println(clazz3);
		System.out.println(clazz == clazz2);
		System.out.println(clazz == clazz3);
		System.out.println(clazz.equals(clazz2));
		System.out.println(clazz.equals(clazz3));
		
		System.out.println(clazz.isPrimitive());
		System.out.println(int.class.isPrimitive());
		System.out.println(Integer.class.isPrimitive());
		
		System.out.println(int.class == Integer.class);
		System.out.println(int.class == Integer.TYPE);
		System.out.println(int[].class.isPrimitive());
		System.out.println(int[].class.isArray());
		System.out.println(void.class);

	}

}
