package hh.learnj.enhance.arrayhashsethashcode;

import hh.learnj.enhance.reflect.beans.ReflectPoint;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

public class ArrayListHashSetHashCode {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void main(String[] args) {
		
		ReflectPoint point1 = new ReflectPoint(3, 5, "A");
		ReflectPoint point2 = new ReflectPoint(1, 10, "B");
		ReflectPoint point3 = new ReflectPoint(2, 25, "C");
		
		Collection collection1 = new ArrayList();
		collection1.add(point1);
		collection1.add(point2);
		collection1.add(point3);
		collection1.add(point1);
		System.out.println(collection1.size());
		//HashSet是通过hashCode和equasl两个方法来进行判断是否属于同一个对象，
		//默认的hashcode算法与对象的内存地址有关
		Collection collection2 = new HashSet();
		collection2.add(point1);
		collection2.add(point2);
		collection2.add(point3);
		collection2.add(point1);
		System.out.println(collection2.size());

	}

}
