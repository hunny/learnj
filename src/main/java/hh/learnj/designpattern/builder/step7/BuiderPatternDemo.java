package hh.learnj.designpattern.builder.step7;

import hh.learnj.designpattern.builder.step5.Meal;
import hh.learnj.designpattern.builder.step6.MealBuilder;

/**
 * BuiderPatternDemo uses MealBuider to demonstrate builder pattern.
 * 
 * @author hunnyhu
 *
 */
public class BuiderPatternDemo {

	public static void main(String[] args) {

		MealBuilder mealBuilder = new MealBuilder();
		Meal vegMeal = mealBuilder.prepareVegMeal();
		System.out.println("Veg Meal");
		vegMeal.showItems();
		System.out.println("Total Cost: " + vegMeal.getCost());

		Meal nonVegMeal = mealBuilder.prepareNonVegMeal();
		System.out.println("\n\nNon-Veg Meal");
		nonVegMeal.showItems();
		System.out.println("Total Cost: " + nonVegMeal.getCost());
	}
}
