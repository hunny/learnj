package hh.learnj.designpattern.abstractfactory.step5;

import hh.learnj.designpattern.abstractfactory.step1.Shape;
import hh.learnj.designpattern.abstractfactory.step3.Color;

/**
 * Create an Abstract class to get factories for Color and Shape Objects.
 * @author hunnyhu
 */
public abstract class AbstractFactory {

	public abstract Color getColor(String color);

	public abstract Shape getShape(String shape);

}
