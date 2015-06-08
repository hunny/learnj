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
	protected Double g = new Double(0);
	protected Double c = new Double(0);
	
	@Before
	public void before() {
		for (int n = 0; n < i; n++) {
			map.put("" + n, new Double(Math.random()));
		}
	}

	@Test
	public void genericTest() {
		long start = System.currentTimeMillis();
		for (int n = 0; n < i; n++) {
			Double d =  getT("" + n);
		}
		System.out.println("generic:" + (System.currentTimeMillis() - start));
	}
	
	@Test
	public void commonTest() {
		long start = System.currentTimeMillis();
		for (int n = 0; n < i; n++) {
			Double d = getObject("" + n);
		}
		System.out.println("common:" + (System.currentTimeMillis() - start));
	}
	
	public Double getObject(String key) {
		return (Double)map.get(key);
	}
	
	public <T> T getT(String key) {
		return (T)map.get(key);
	}

}
