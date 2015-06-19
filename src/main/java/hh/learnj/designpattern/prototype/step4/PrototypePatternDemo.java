package hh.learnj.designpattern.prototype.step4;

import hh.learnj.designpattern.prototype.step1.Shape;
import hh.learnj.designpattern.prototype.step3.ShapeCache;

/**
 * Prototype pattern refers to creating duplicate object while keeping
 * performance in mind. This type of design pattern comes under creational
 * pattern as this pattern provides one of the best ways to create an object.
 * 
 * This pattern involves implementing a prototype interface which tells to
 * create a clone of the current object. This pattern is used when creation of
 * object directly is costly. For example, an object is to be created after a
 * costly database operation. We can cache the object, returns its clone on next
 * request and update the database as and when needed thus reducing database
 * calls.
 * 
 * http://www.tutorialspoint.com/design_pattern/prototype_pattern.htm
 * 
 * @author hunnyhu
 *
 */
public class PrototypePatternDemo {

	public static void main(String[] args) {

		ShapeCache.loadCache();

		Shape clonedShape1 = (Shape) ShapeCache.getShape("1");
		System.out.println("Shape : " + clonedShape1.getType());

		Shape clonedShape2 = (Shape) ShapeCache.getShape("2");
		System.out.println("Shape : " + clonedShape2.getType());

		Shape clonedShape3 = (Shape) ShapeCache.getShape("3");
		System.out.println("Shape : " + clonedShape3.getType());
	}
}
