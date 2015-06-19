package hh.learnj.designpattern.facade.step2;

import hh.learnj.designpattern.facade.step1.Shape;

public class Square implements Shape {

	@Override
	public void draw() {
		System.out.println("Square::draw()");
	}
}
