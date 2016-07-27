package hh.learnj.enhance.generic;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class GenericClassUtils {
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static <T> Class<T> getGenericClassType(Class clazz) {
		Type type = clazz.getGenericSuperclass();
		if (type instanceof ParameterizedType) {
			ParameterizedType pt = (ParameterizedType)type;
			Type[] types = pt.getActualTypeArguments();
			if (types.length > 0 && types[0] instanceof Class) {
				return (Class)types[0];
			}
		}
		return (Class)Object.class;
	}

}
