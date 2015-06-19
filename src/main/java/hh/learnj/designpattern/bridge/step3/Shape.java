package hh.learnj.designpattern.bridge.step3;

import hh.learnj.designpattern.bridge.step1.DrawAPI;

/**
 * Create an abstract class Shape using the DrawAPI interface.
 * 
 * @author hunnyhu
 *
 */
public abstract class Shape {
	
	protected DrawAPI drawAPI;

	protected Shape(DrawAPI drawAPI) {
		this.drawAPI = drawAPI;
	}

	public abstract void draw();
}
