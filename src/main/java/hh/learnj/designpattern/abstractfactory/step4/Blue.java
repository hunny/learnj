package hh.learnj.designpattern.abstractfactory.step4;

import hh.learnj.designpattern.abstractfactory.step3.Color;

public class Blue implements Color {

	@Override
	public void fill() {
		System.out.println("Inside Blue::fill() method.");
	}

}
