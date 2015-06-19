package hh.learnj.designpattern.abstractfactory.step4;

import hh.learnj.designpattern.abstractfactory.step3.Color;


/**
 * Create concrete classes implementing the same interface.
 * @author hunnyhu
 */
public class Red implements Color {

	@Override
	public void fill() {
		System.out.println("Inside Red::fill() method.");
	}

}
