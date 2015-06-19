package hh.learnj.designpattern.facade.step2;

import hh.learnj.designpattern.facade.step1.Shape;

public class Circle implements Shape {

	@Override
	public void draw() {
		System.out.println("Circle::draw()");
	}
}
