package hh.learnj.designpattern.factory.step4;

import hh.learnj.designpattern.factory.step1.Shape;
import hh.learnj.designpattern.factory.step3.ShapeFactory;

/**
 * Step 4 Use the Factory to get object of concrete class by passing an
 * information such as type.
 * 
 * @author Hunny.Hu
 */
public class FactoryPatternDemo {
	public static void main(String[] args) {
		ShapeFactory shapeFactory = new ShapeFactory();
		// get an object of Circle and call its draw method.
		Shape shape1 = shapeFactory.getShape("CIRCLE");
		// call draw method of Circle
		shape1.draw();
		// get an object of Rectangle and call its draw method.
		Shape shape2 = shapeFactory.getShape("RECTANGLE");
		// call draw method of Rectangle
		shape2.draw();
		// get an object of Square and call its draw method.
		Shape shape3 = shapeFactory.getShape("SQUARE");
		// call draw method of circle
		shape3.draw();
	}
}
