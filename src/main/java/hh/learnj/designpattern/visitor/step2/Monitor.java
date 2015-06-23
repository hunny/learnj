package hh.learnj.designpattern.visitor.step2;

import hh.learnj.designpattern.visitor.step1.ComputerPart;
import hh.learnj.designpattern.visitor.step3.ComputerPartVisitor;

public class Monitor implements ComputerPart {

	@Override
	public void accept(ComputerPartVisitor computerPartVisitor) {
		computerPartVisitor.visit(this);
	}
}
