package hh.learnj.enhance.reflect.usecase;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * -----------------------------------------
 * 
 * @描述 测试类
 * @作者 fancy
 * @邮箱 fancydeepin@yeah.net
 * @日期 2012-8-26
 *     <p>
 *     -----------------------------------------
 */
public class MethodGenriceTypeTest {

	private Map<String, Number> collection = new HashMap<String, Number>();

	public Map<String, Number> getCollection() {
		return collection;
	}

	public void setCollection(Map<String, Number> collection) {
		this.collection = collection;
	}

	public static void main(String[] args) {
		try {
			Class<?> clazz = MethodGenriceTypeTest.class; // 取得 Class
			Field field = clazz.getDeclaredField("collection"); // 取得字段变量
			Type type = field.getGenericType(); // 取得泛型的类型
			ParameterizedType ptype = (ParameterizedType) type; // 转成参数化类型
			System.out.println(ptype.getActualTypeArguments()[0]); // 取出第一个参数的实际类型
			System.out.println(ptype.getActualTypeArguments()[1]); // 取出第二个参数的实际类型
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
