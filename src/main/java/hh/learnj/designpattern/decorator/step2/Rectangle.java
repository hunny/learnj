package hh.learnj.designpattern.decorator.step2;

import hh.learnj.designpattern.decorator.step1.Shape;

public class Rectangle implements Shape {

	   @Override
	   public void draw() {
	      System.out.println("Shape: Rectangle");
	   }
	}
