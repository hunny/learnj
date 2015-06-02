package hh.learnj.designpattern.factory.step3;

import hh.learnj.designpattern.factory.step1.Shape;
import hh.learnj.designpattern.factory.step2.Circle;
import hh.learnj.designpattern.factory.step2.Rectangle;
import hh.learnj.designpattern.factory.step2.Square;

/**
 * Step 3 Create a Factory to generate object of concrete class based on given
 * information.
 * @author Hunny.Hu
 */
public class ShapeFactory {
	// use getShape method to get object of type shape
	public Shape getShape(String shapeType) {
		if (shapeType == null) {
			return null;
		}
		if (shapeType.equalsIgnoreCase("CIRCLE")) {
			return new Circle();

		} else if (shapeType.equalsIgnoreCase("RECTANGLE")) {
			return new Rectangle();

		} else if (shapeType.equalsIgnoreCase("SQUARE")) {
			return new Square();
		}
		return null;
	}
}
