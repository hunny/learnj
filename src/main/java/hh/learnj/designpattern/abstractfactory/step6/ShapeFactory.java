package hh.learnj.designpattern.abstractfactory.step6;

import hh.learnj.designpattern.abstractfactory.step1.Shape;
import hh.learnj.designpattern.abstractfactory.step2.Circle;
import hh.learnj.designpattern.abstractfactory.step2.Rectangle;
import hh.learnj.designpattern.abstractfactory.step2.Square;
import hh.learnj.designpattern.abstractfactory.step3.Color;
import hh.learnj.designpattern.abstractfactory.step5.AbstractFactory;

/**
 * Create Factory classes extending AbstractFactory to generate object of
 * concrete class based on given information.
 * 
 * @author hunnyhu
 */
public class ShapeFactory extends AbstractFactory {

	@Override
	public Color getColor(String color) {
		return null;
	}

	@Override
	public Shape getShape(String shape) {
		if ("CIRCLE".equalsIgnoreCase(shape)) {
			return new Circle();

		} else if ("RECTANGLE".equalsIgnoreCase(shape)) {
			return new Rectangle();

		} else if ("SQUARE".equalsIgnoreCase(shape)) {
			return new Square();
		}
		return null;
	}

}
