package hh.learnj.designpattern.visitor.step4;

import hh.learnj.designpattern.visitor.step2.Computer;
import hh.learnj.designpattern.visitor.step2.Keyboard;
import hh.learnj.designpattern.visitor.step2.Monitor;
import hh.learnj.designpattern.visitor.step2.Mouse;
import hh.learnj.designpattern.visitor.step3.ComputerPartVisitor;

public class ComputerPartDisplayVisitor implements ComputerPartVisitor {

	@Override
	public void visit(Computer computer) {
		System.out.println("Displaying Computer.");
	}

	@Override
	public void visit(Mouse mouse) {
		System.out.println("Displaying Mouse.");
	}

	@Override
	public void visit(Keyboard keyboard) {
		System.out.println("Displaying Keyboard.");
	}

	@Override
	public void visit(Monitor monitor) {
		System.out.println("Displaying Monitor.");
	}

}
