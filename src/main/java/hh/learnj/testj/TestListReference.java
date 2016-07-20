package hh.learnj.testj;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class TestListReference {
	
	protected List<Info> list = new ArrayList<Info>();
	
	@Before
	public void before() {
		list.add(new Info("hello", "pk123"));
		list.add(new Info("OK", "bomb"));
		list.add(new Info("Hi", "qq"));
	}
	
	@Test
	public void testMethod() {
		method(list);
		showResult(list);
	}
	
	public void method(List<Info> ref) {
		for (Info info : ref) {
			if ("OK".equals(info.getKey())) {
				info.setValue("1230");
			}
		}
	}
	
	public void showResult(List<Info> ref) {
		for (Info info : ref) {
			System.out.println(info);
		}
	}
	
	class Info {
		
		private String key;
		private String value;
		
		public Info(String key, String value) {
			super();
			this.key = key;
			this.value = value;
		}
		public String getKey() {
			return key;
		}
		public void setKey(String key) {
			this.key = key;
		}
		public String getValue() {
			return value;
		}
		public void setValue(String value) {
			this.value = value;
		}
		
		public String toString() {
			return "[" + this.getKey() + "]=[" + this.getValue() + "]";
		}
		
	}

}
