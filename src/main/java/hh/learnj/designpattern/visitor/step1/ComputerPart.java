package hh.learnj.designpattern.visitor.step1;

import hh.learnj.designpattern.visitor.step3.ComputerPartVisitor;

/**
 * Define an interface to represent element.
 * 
 * @author hunnyhu
 *
 */
public interface ComputerPart {

	public void accept(ComputerPartVisitor computerPartVisitor);

}
