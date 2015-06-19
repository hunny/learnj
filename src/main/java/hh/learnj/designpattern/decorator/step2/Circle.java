package hh.learnj.designpattern.decorator.step2;

import hh.learnj.designpattern.decorator.step1.Shape;

public class Circle implements Shape {

	@Override
	public void draw() {
		System.out.println("Shape: Circle");
	}

}
