package hh.learnj.designpattern.bridge.step2;

import hh.learnj.designpattern.bridge.step1.DrawAPI;

public class GreenCircle implements DrawAPI {
	
	@Override
	public void drawCircle(int radius, int x, int y) {
		System.out.println("Drawing Circle[ color: green, radius: " + radius
				+ ", x: " + x + ", " + y + "]");
	}
}
