package hh.learnj.designpattern.command.step4;

import hh.learnj.designpattern.command.step1.Order;

import java.util.ArrayList;
import java.util.List;

public class Broker {

	private List<Order> orderList = new ArrayList<Order>();

	public void takeOrder(Order order) {
		orderList.add(order);
	}

	public void placeOrders() {
		for (Order order : orderList) {
			order.execute();
		}
		orderList.clear();
	}
}
