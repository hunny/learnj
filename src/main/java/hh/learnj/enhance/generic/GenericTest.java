package hh.learnj.enhance.generic;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

public class GenericTest {

	public static void main(String[] args) throws Exception {

		Constructor<String> constructor = String.class.getConstructor(StringBuffer.class);
		String str = constructor.newInstance(new StringBuffer("ABC"));
		System.out.println(str);
		
		List<String> list1 = new ArrayList<String>();
		list1.add("abc");
		List<Integer> list2 = new ArrayList<Integer>();
		list2.add(19);
		System.out.println(list1.getClass() == list2.getClass());
		//Generic show it to compiler
		list2.getClass().getMethod("add", Object.class).invoke(list2, "Over Compiler.");
		System.out.println(list2.get(1));
		printCollection(list1);
		printCollection(list2);
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("A", 12);
		map.put("B", 23);
		map.put("C", 19);
		Set<Map.Entry<String, Integer>> entrySet = map.entrySet();
		for (Map.Entry<String, Integer> entry: entrySet) {
			System.out.println(entry.getKey() + ":" + entry.getValue());
		}
		
		int x1 = testGenericMethod(1, 3);
		Number x2 = testGenericMethod(3.5, 10);
		Object x3 = testGenericMethod(45, "abc");
		Object x4 = "OK";
		String x5 = autoConvert(x4);
		
		Method method = GenericTest.class.getMethod("checkVectorParameterType", Vector.class);
		Type[] types = method.getGenericParameterTypes();
		ParameterizedType type = (ParameterizedType)types[0];
		System.out.println(type.getRawType());
		System.out.println(type.getActualTypeArguments()[0]);
	}
	
	public void checkVectorParameterType(Vector<Date> params) {
		
	}
	
	public static <T> void fillArray(T[] arr, T t) {
		for (int i = 0; i < arr.length; i++) {
			arr[i] = t;
		}
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T autoConvert(Object obj) {
		return (T)obj;
	}
	
	private static <T> T testGenericMethod(T a, T b) {
		System.out.println("a:" + a.getClass().getName() + ", b:"
				+ b.getClass().getName());
		return a;
	}
	
	public static void printCollection(Collection<?> collection) {
		System.out.println("print collection " + collection.iterator().next().getClass());
		System.out.println("The size: " + collection.size());
		for (Object obj : collection) {
			System.out.println(obj);
		}
	}

}
