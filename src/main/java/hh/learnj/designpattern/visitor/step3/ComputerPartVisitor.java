package hh.learnj.designpattern.visitor.step3;

import hh.learnj.designpattern.visitor.step2.Computer;
import hh.learnj.designpattern.visitor.step2.Keyboard;
import hh.learnj.designpattern.visitor.step2.Monitor;
import hh.learnj.designpattern.visitor.step2.Mouse;

/**
 * Define an interface to represent visitor.
 * 
 * @author hunnyhu
 *
 */
public interface ComputerPartVisitor {

	public void visit(Computer computer);

	public void visit(Mouse mouse);

	public void visit(Keyboard keyboard);

	public void visit(Monitor monitor);
	
}
