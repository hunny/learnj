package hh.learnj.designpattern.abstractfactory.step6;

import hh.learnj.designpattern.abstractfactory.step1.Shape;
import hh.learnj.designpattern.abstractfactory.step3.Color;
import hh.learnj.designpattern.abstractfactory.step4.Blue;
import hh.learnj.designpattern.abstractfactory.step4.Green;
import hh.learnj.designpattern.abstractfactory.step4.Red;
import hh.learnj.designpattern.abstractfactory.step5.AbstractFactory;

public class ColorFactory extends AbstractFactory {

	@Override
	public Color getColor(String color) {
		if ("RED".equalsIgnoreCase(color)) {
			return new Red();
		} else if ("GREEN".equalsIgnoreCase(color)) {
			return new Green();
		} else if ("BLUE".equalsIgnoreCase(color)) {
			return new Blue();
		}
		return null;
	}

	@Override
	public Shape getShape(String shape) {
		return null;
	}

}
