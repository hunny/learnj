package hh.learnj.designpattern.prototype.step2;

import hh.learnj.designpattern.prototype.step1.Shape;

public class Square extends Shape {

	public Square() {
		type = "Square";
	}

	@Override
	public void draw() {
		System.out.println("Inside Square::draw() method.");
	}
}
