package hh.learnj.enhance.reflect.usecase;

import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import hh.learnj.poi.guide.read.usecase.ReadExcelData;

/**
 * Read From Excel at sheet(0) And Mapping to T object and save to Map with key K.
 * @author huzexiong
 * @param <K>
 * @param <T>
 */
public abstract class DataMapper<K, T> {

	  public Map<K, T> getExpectData(InputStream inputStream, Class<T> clazz) throws Exception {

	    Collection data = new ReadExcelData(inputStream).getData();
	    Map<K, T> map = new HashMap<K, T>();
	    Object[] mData = data.toArray();
	    Object[] column = (Object[]) mData[0];
	    int size = mData.length - 1;
	    for (int i = size; i >= 1; i--) {
	      T t = mapValue(column, mData[i], clazz);
	      K k = mapKey(t);
	      map.put(k, t);
	    }
	    return map;
	  }

	  public abstract K mapKey(T t);

	  public T mapValue(Object[] column, Object row, Class<T> t) throws Exception {
	    Class<?> clazz = Class.forName(t.getName());
	    Constructor<?> constructor = clazz.getConstructor();
	    Object obj = constructor.newInstance();
	    Map<String, Method> mapMethods = new HashMap<String, Method>();
	    for (Method method : clazz.getMethods()) {
	      mapMethods.put(method.getName().toUpperCase(), method);
	    }
	    int size = column.length;
	    Object[] mRow = (Object[]) row;
	    for (int i = size - 1; i >= 0; i--) {
	      String name = "set" + String.valueOf(column[i]);
	      Method method = mapMethods.get(name.toUpperCase());
	      if (null != method) {
	        method.invoke(obj, mRow[i]);
	      }
	    }
	    return (T) obj;
	  }
}
