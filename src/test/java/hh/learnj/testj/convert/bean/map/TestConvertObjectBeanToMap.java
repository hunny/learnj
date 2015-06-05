package hh.learnj.testj.convert.bean.map;

import hh.learnj.testj.TestHttpClient;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;

/**
 * 把Java Bean转化为Map
 * @author Hunny.Hu
 */
public class TestConvertObjectBeanToMap {

	private static final Logger logger = Logger.getLogger(TestHttpClient.class);

	@Test
	public void convertObjectToMapWithJava() throws IntrospectionException,
			IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {
		logger.debug("convertObjectToMapWithJava");
		
		NoteBook actionMethodNoteBook = new NoteBook(100, "Action Method Notebook");
	    Map<String, Object> objectAsMap = new HashMap<String, Object>();
	    BeanInfo info = Introspector.getBeanInfo(actionMethodNoteBook.getClass());
	    for (PropertyDescriptor pd : info.getPropertyDescriptors()) {
	        Method reader = pd.getReadMethod();
	        if (reader != null)
	            objectAsMap.put(pd.getName(),reader.invoke(actionMethodNoteBook));
	    }
	    
	    logger.info(objectAsMap);
	}
	
	@Test
	public void convertObjectToMapWithApacheCommons () throws IllegalAccessException, 
	    InvocationTargetException, NoSuchMethodException {
	    NoteBook fieldNoteBook = new NoteBook(878, "Field Notebook");
	    @SuppressWarnings("unchecked")
	    Map<String, Object> objectAsMap = BeanUtils.describe(fieldNoteBook);
	    logger.info(objectAsMap);
	}
	
	@Test
	public void convertObjectToMapWithJackson () {
	    NoteBook moleskineNoteBook = new NoteBook(200, "Moleskine Notebooks");
	    ObjectMapper objectMapper = new ObjectMapper();
	    @SuppressWarnings("unchecked")
	    Map<String, Object> objectAsMap = objectMapper.convertValue(moleskineNoteBook, Map.class);
	    logger.info(objectAsMap);
	}
	
}
