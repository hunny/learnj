package hh.learnj.designpattern.bridge.step4;

import hh.learnj.designpattern.bridge.step1.DrawAPI;
import hh.learnj.designpattern.bridge.step3.Shape;

/**
 * Create concrete class implementing the Shape interface.
 * 
 * @author hunnyhu
 */
public class Circle extends Shape {
	
	private int x, y, radius;

	public Circle(int x, int y, int radius, DrawAPI drawAPI) {
		super(drawAPI);
		this.x = x;
		this.y = y;
		this.radius = radius;
	}

	public void draw() {
		drawAPI.drawCircle(radius, x, y);
	}
}
