package hh.learnj.designpattern.abstractfactory.step2;

import hh.learnj.designpattern.abstractfactory.step1.Shape;

public class Square implements Shape {
	@Override
	public void draw() {
		System.out.println("Inside Square::draw() method.");
	}
}
