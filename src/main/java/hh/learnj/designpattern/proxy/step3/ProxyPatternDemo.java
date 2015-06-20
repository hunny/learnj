package hh.learnj.designpattern.proxy.step3;

import hh.learnj.designpattern.proxy.step1.Image;
import hh.learnj.designpattern.proxy.step2.ProxyImage;

/**
 * In proxy pattern, a class represents functionality of another class. This
 * type of design pattern comes under structural pattern.
 * 
 * In proxy pattern, we create object having original object to interface its
 * functionality to outer world.
 * 
 * http://www.tutorialspoint.com/design_pattern/proxy_pattern.htm
 * 
 * @author hunnyhu
 *
 */
public class ProxyPatternDemo {

	public static void main(String[] args) {
		Image image = new ProxyImage("test_10mb.jpg");

		// image will be loaded from disk
		image.display();
		System.out.println("");

		// image will not be loaded from disk
		image.display();
	}
}
