package hh.learnj.enhance.proxy;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javassist.Modifier;

public class Test {

	public static void main(String[] args) throws Exception {
		Test t = new Test();
		List<String> s = new ArrayList<String>();
		s.add("香港");
		t.changeName(s);
		System.out.println(s);
		short i = 1;
		i += 1;
		System.out.println(i);
		Integer k = 360;
		t.changePrimary(k);
		System.out.println(k);
		Method method = Test.class.getMethod("changePrimary", Integer.class);
		int m = method.getModifiers();
		System.out.println(Modifier.toString(m));
	}
	
	void changeName(List<String> l) {
		l.add("台湾");
	}
	
	protected void changePrimary(Integer i) {
		i = 16;
	}

}
