package hh.learnj.designpattern.iterator.step3;

import hh.learnj.designpattern.iterator.step1.Iterator;
import hh.learnj.designpattern.iterator.step2.NameRepository;

/**
 * Iterator pattern is very commonly used design pattern in Java and .Net
 * programming environment. This pattern is used to get a way to access the
 * elements of a collection object in sequential manner without any need to know
 * its underlying representation.
 * 
 * Iterator pattern falls under behavioral pattern category.
 * 
 * http://www.tutorialspoint.com/design_pattern/iterator_pattern.htm
 * 
 * @author hunnyhu
 *
 */
public class IteratorPatternDemo {

	public static void main(String[] args) {

		NameRepository namesRepository = new NameRepository();

		for (Iterator iter = namesRepository.getIterator(); iter.hasNext();) {
			String name = (String) iter.next();
			System.out.println("Name : " + name);
		}
	}
}
