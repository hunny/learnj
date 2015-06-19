package hh.learnj.designpattern.bridge.step2;

import hh.learnj.designpattern.bridge.step1.DrawAPI;

/**
 * Create concrete bridge implementer classes implementing the DrawAPI
 * interface.
 * 
 * @author hunnyhu
 *
 */
public class RedCircle implements DrawAPI {
	@Override
	public void drawCircle(int radius, int x, int y) {
		System.out.println("Drawing Circle[ color: red, radius: " + radius
				+ ", x: " + x + ", " + y + "]");
	}
}
