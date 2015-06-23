package hh.learnj.designpattern.visitor.step2;

import hh.learnj.designpattern.visitor.step1.ComputerPart;
import hh.learnj.designpattern.visitor.step3.ComputerPartVisitor;

public class Computer implements ComputerPart {

	private ComputerPart[] parts;

	public Computer() {
		parts = new ComputerPart[] { 
			new Mouse(), 
			new Keyboard(), 
			new Monitor() 
		};
	}

	@Override
	public void accept(ComputerPartVisitor computerPartVisitor) {
		for (int i = 0; i < parts.length; i++) {
			parts[i].accept(computerPartVisitor);
		}
		computerPartVisitor.visit(this);
	}
}
