package hh.learnj.designpattern.interpreter.step2;

import hh.learnj.designpattern.interpreter.step1.Expression;

/**
 * Create concrete classes implementing the above interface.
 * 
 * @author hunnyhu
 *
 */
public class TerminalExpression implements Expression {

	private String data = null;

	public TerminalExpression(String data) {
		this.data = data;
	}

	@Override
	public boolean interpret(String context) {
		if (context.contains(data)) {
			return true;
		}
		return false;
	}

}
