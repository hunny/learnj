package hh.learnj.designpattern.facade.step2;

import hh.learnj.designpattern.facade.step1.Shape;

/**
 * Create concrete classes implementing the same interface.
 * 
 * @author hunnyhu
 *
 */
public class Rectangle implements Shape {

	@Override
	public void draw() {
		System.out.println("Rectangle::draw()");
	}
}
