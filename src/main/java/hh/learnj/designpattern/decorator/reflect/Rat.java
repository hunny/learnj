package hh.learnj.designpattern.decorator.reflect;

/**
 * 老鼠是一种动物
 * @author hunnyhu
 *
 */
public class Rat implements Animal {

	@Override
	public void doStuff() {
		System.out.println("Jerry will play with Tom.");
	}

}
