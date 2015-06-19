package hh.learnj.designpattern.decorator.step5;

import hh.learnj.designpattern.decorator.step1.Shape;
import hh.learnj.designpattern.decorator.step2.Circle;
import hh.learnj.designpattern.decorator.step2.Rectangle;
import hh.learnj.designpattern.decorator.step4.RedShapeDecorator;

/**
 * Decorator pattern allows a user to add new functionality to an existing
 * object without altering its structure. This type of design pattern comes
 * under structural pattern as this pattern acts as a wrapper to existing class.
 * 
 * This pattern creates a decorator class which wraps the original class and
 * provides additional functionality keeping class methods signature intact.
 * 
 * We are demonstrating the use of decorator pattern via following example in
 * which we will decorate a shape with some color without alter shape class.
 * 
 * http://www.tutorialspoint.com/design_pattern/decorator_pattern.htm
 * @author hunnyhu
 *
 */
public class DecoratorPatternDemo {

	public static void main(String[] args) {

		Shape circle = new Circle();

		Shape redCircle = new RedShapeDecorator(new Circle());

		Shape redRectangle = new RedShapeDecorator(new Rectangle());
		System.out.println("Circle with normal border");
		circle.draw();

		System.out.println("\nCircle of red border");
		redCircle.draw();

		System.out.println("\nRectangle of red border");
		redRectangle.draw();
	}
}
