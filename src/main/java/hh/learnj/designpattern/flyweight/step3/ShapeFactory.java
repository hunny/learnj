package hh.learnj.designpattern.flyweight.step3;

import hh.learnj.designpattern.flyweight.step1.Shape;
import hh.learnj.designpattern.flyweight.step2.Circle;

import java.util.HashMap;

public class ShapeFactory {

	private static final HashMap<String, Shape> circleMap = new HashMap<String, Shape>();

	public static Shape getCircle(String color) {
		
		Shape circle = circleMap.get(color);

		if (circle == null) {
			circle = new Circle(color);
			circleMap.put(color, circle);
			System.out.println("Creating circle of color : " + color);
		}
		return circle;
	}
}
