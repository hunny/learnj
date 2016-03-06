package hh.learnj.enhance.arrayhashsethashcode;

import java.io.InputStream;
import java.util.Collection;
import java.util.Date;
import java.util.Properties;

public class ReflectUseCase {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void main(String[] args) throws Exception {
		InputStream stream = ReflectUseCase.class.getResourceAsStream("config.properties");
		Properties prop = new Properties();
		prop.load(stream);
		stream.close();
		String className = prop.getProperty("className");
		Collection collection = (Collection)Class.forName(className).newInstance();
		Date date1 = new Date();
		Date date2 = new Date();
		Date date3 = new Date();
		collection.add(date1);
		collection.add(date2);
		collection.add(date3);
		collection.add(date1);
		System.out.println(collection.size());

	}

}
