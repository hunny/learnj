package hh.learnj.designpattern.prototype.step3;

import hh.learnj.designpattern.prototype.step1.Shape;
import hh.learnj.designpattern.prototype.step2.Circle;
import hh.learnj.designpattern.prototype.step2.Rectangle;
import hh.learnj.designpattern.prototype.step2.Square;

import java.util.Hashtable;

public class ShapeCache {

	private static Hashtable<String, Shape> shapeMap = new Hashtable<String, Shape>();

	public static Shape getShape(String shapeId) {
		Shape cachedShape = shapeMap.get(shapeId);
		return (Shape) cachedShape.clone();
	}

	// for each shape run database query and create shape
	// shapeMap.put(shapeKey, shape);
	// for example, we are adding three shapes
	
	public static void loadCache() {
		
		Circle circle = new Circle();
		circle.setId("1");
		shapeMap.put(circle.getId(), circle);

		Square square = new Square();
		square.setId("2");
		shapeMap.put(square.getId(), square);

		Rectangle rectangle = new Rectangle();
		rectangle.setId("3");
		shapeMap.put(rectangle.getId(), rectangle);
	}
}
