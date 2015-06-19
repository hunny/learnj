package hh.learnj.designpattern.prototype.step2;

import hh.learnj.designpattern.prototype.step1.Shape;

public class Rectangle extends Shape {

	public Rectangle() {
		type = "Rectangle";
	}

	@Override
	public void draw() {
		System.out.println("Inside Rectangle::draw() method.");
	}

}
