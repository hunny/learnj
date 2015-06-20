package hh.learnj.designpattern.command.step3;

import hh.learnj.designpattern.command.step1.Order;
import hh.learnj.designpattern.command.step2.Stock;

public class SellStock implements Order {
	
	private Stock abcStock;

	public SellStock(Stock abcStock) {
		this.abcStock = abcStock;
	}

	public void execute() {
		abcStock.sell();
	}
}
