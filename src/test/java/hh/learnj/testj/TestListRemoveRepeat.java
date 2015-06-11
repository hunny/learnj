package hh.learnj.testj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

public class TestListRemoveRepeat {
	
	@Test
	public void testStringListRepeat() {
		List<String> sList = new ArrayList<String>();
		sList.add("1");
		sList.add("1");
		sList.add("2");
		System.out.println("before:");
		loopList(sList);
		removeRepeats(sList);
		System.out.println("after:");
		loopList(sList);
	}
	
	@Test
	public void testMapListRepeat() {
		List<Map<String, Object>> mList = new ArrayList<Map<String, Object>>();
		mList.add(makeMap("1", "1"));
		mList.add(makeMap("1", "1"));
		mList.add(makeMap("2", "2"));
		System.out.println("before:");
		loopList(mList);
		removeRepeats(mList);
		System.out.println("after:");
		loopList(mList);
	}
	
	public Map<String, Object> makeMap(String key, String value) {
		Map<String, Object> m1 = new HashMap<String, Object>();
		m1.put(key, value);
		return m1;
	}
	
    public <T> List<T> removeRepeats(List<T> list) {
    	if (null == list) {
    		throw new IllegalArgumentException("Parameter list can not be null");
    	}
    	Set<T> set = new HashSet<T>(list);
    	list.clear();
    	list.addAll(set);
    	return list;
    }
    
    public <T> void loopList(List<T> list) {
    	for (T t : list) {
    		System.out.println(t);
    	}
    }

}
