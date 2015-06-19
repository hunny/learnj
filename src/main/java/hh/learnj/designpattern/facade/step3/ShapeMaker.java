package hh.learnj.designpattern.facade.step3;

import hh.learnj.designpattern.facade.step1.Shape;
import hh.learnj.designpattern.facade.step2.Circle;
import hh.learnj.designpattern.facade.step2.Rectangle;
import hh.learnj.designpattern.facade.step2.Square;

/**
 * Create a facade class.
 * 
 * @author hunnyhu
 *
 */
public class ShapeMaker {

	private Shape circle;
	private Shape rectangle;
	private Shape square;

	public ShapeMaker() {
		circle = new Circle();
		rectangle = new Rectangle();
		square = new Square();
	}

	public void drawCircle() {
		circle.draw();
	}

	public void drawRectangle() {
		rectangle.draw();
	}

	public void drawSquare() {
		square.draw();
	}

}
