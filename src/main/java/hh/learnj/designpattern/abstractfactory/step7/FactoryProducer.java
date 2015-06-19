package hh.learnj.designpattern.abstractfactory.step7;

import hh.learnj.designpattern.abstractfactory.step5.AbstractFactory;
import hh.learnj.designpattern.abstractfactory.step6.ColorFactory;
import hh.learnj.designpattern.abstractfactory.step6.ShapeFactory;

/**
 * Create a Factory generator/producer class to get factories by passing an
 * information such as Shape or Color
 * 
 * @author hunnyhu
 */
public class FactoryProducer {
	
	public static AbstractFactory getFactory(String choice) {
		
		if ("SHAPE".equalsIgnoreCase(choice)) {
			return new ShapeFactory();
		} else if ("COLOR".equalsIgnoreCase(choice)) {
			return new ColorFactory();
		}

		return null;
	}
	
}
