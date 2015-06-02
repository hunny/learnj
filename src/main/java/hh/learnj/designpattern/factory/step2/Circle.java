package hh.learnj.designpattern.factory.step2;

import hh.learnj.designpattern.factory.step1.Shape;

/**
 * Step 2 Create concrete classes implementing the same interface.
 * @author Hunny.Hu
 */
public class Circle implements Shape {

	@Override
	public void draw() {
		System.out.println("Inside Circle::draw() method.");
	}
	
}
