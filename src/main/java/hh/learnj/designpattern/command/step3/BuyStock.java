package hh.learnj.designpattern.command.step3;

import hh.learnj.designpattern.command.step1.Order;
import hh.learnj.designpattern.command.step2.Stock;

/**
 * Create concrete classes implementing the Order interface.
 * 
 * @author hunnyhu
 *
 */
public class BuyStock implements Order {
	
	private Stock abcStock;

	public BuyStock(Stock abcStock) {
		this.abcStock = abcStock;
	}

	public void execute() {
		abcStock.buy();
	}
}
