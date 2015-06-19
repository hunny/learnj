package hh.learnj.designpattern.builder.step6;

import hh.learnj.designpattern.builder.step4.ChickenBurger;
import hh.learnj.designpattern.builder.step4.Coke;
import hh.learnj.designpattern.builder.step4.Pepsi;
import hh.learnj.designpattern.builder.step4.VegBurger;
import hh.learnj.designpattern.builder.step5.Meal;

/**
 * Create a MealBuilder class, the actual builder class responsible to create
 * Meal objects.
 * 
 * @author hunnyhu
 *
 */
public class MealBuilder {
	
	public Meal prepareVegMeal() {
		Meal meal = new Meal();
		meal.addItem(new VegBurger());
		meal.addItem(new Coke());
		return meal;
	}

	public Meal prepareNonVegMeal() {
		Meal meal = new Meal();
		meal.addItem(new ChickenBurger());
		meal.addItem(new Pepsi());
		return meal;
	}
	
}
