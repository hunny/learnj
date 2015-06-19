package hh.learnj.designpattern.builder.step5;

import hh.learnj.designpattern.builder.step1.Item;

import java.util.ArrayList;
import java.util.List;

/**
 * Create a Meal class having Item objects defined above.
 * 
 * @author hunnyhu
 *
 */
public class Meal {

	private List<Item> items = new ArrayList<Item>();

	public void addItem(Item item) {
		items.add(item);
	}

	public float getCost() {
		float cost = 0.0f;

		for (Item item : items) {
			cost += item.price();
		}
		return cost;
	}

	public void showItems() {
		for (Item item : items) {
			System.out.print("Item : " + item.name());
			System.out.print(", Packing : " + item.packing().pack());
			System.out.println(", Price : " + item.price());
		}
	}
}
