package hh.learnj.designpattern.command.step5;

import hh.learnj.designpattern.command.step2.Stock;
import hh.learnj.designpattern.command.step3.BuyStock;
import hh.learnj.designpattern.command.step3.SellStock;
import hh.learnj.designpattern.command.step4.Broker;

/**
 * Use the Broker class to take and execute commands.
 * 
 * Command pattern is a data driven design pattern and falls under behavioral
 * pattern category. A request is wrapped under an object as command and passed
 * to invoker object. Invoker object looks for the appropriate object which can
 * handle this command and passes the command to the corresponding object which
 * executes the command.
 * 
 * http://www.tutorialspoint.com/design_pattern/command_pattern.htm
 * 
 * @author hunnyhu
 *
 */
public class CommandPatternDemo {

	public static void main(String[] args) {

		Stock abcStock = new Stock();

		BuyStock buyStockOrder = new BuyStock(abcStock);
		SellStock sellStockOrder = new SellStock(abcStock);

		Broker broker = new Broker();
		broker.takeOrder(buyStockOrder);
		broker.takeOrder(sellStockOrder);

		broker.placeOrders();
	}
}
