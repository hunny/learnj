package hh.learnj.testj;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

/**
 * 泛型与类型转化性能测试
 * @author Hunny.Hu
 *
 */
public class TestGenericPerformance {
	
	protected int i = 10000;
	protected Map<String, Object> map = new HashMap<String, Object>();
	
	@Before
	public void before() {
		for (int n = 0; n < i; n++) {
			map.put("" + n, new Double(Math.random()));
		}
	}
	
	@Test
	public void generic() {
		for (int n = 0; n < i; n++) {
			Double d =  getT("" + n);
		}
	}
	
	@Test
	public void common() {
		for (int n = 0; n < i; n++) {
			Double object = (Double)getObject("" + n);
		}
	}
	
	public Object getObject(String key) {
		return map.get(key);
	}
	
	public <T> T getT(String key) {
		T t = (T)map.get(key);
		return t;
	}

}
