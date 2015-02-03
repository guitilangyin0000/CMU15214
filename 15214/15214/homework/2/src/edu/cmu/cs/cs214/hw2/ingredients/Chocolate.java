package edu.cmu.cs.cs214.hw2.ingredients;

import edu.cmu.cs.cs214.hw2.beverage.Beverage;
import edu.cmu.cs.cs214.hw2.beverage.Recipe;
import edu.cmu.cs.cs214.hw2.beverage.SizeFactor;

public class Chocolate extends FlavoredBeverage {

	/*
	 * Chocolate class
	 */
	
	private final static int CHOCOLATE = 30;

	public Chocolate(String description, Beverage beverage) {
		super(description, beverage);
		cost += CHOCOLATE; 
		/* each time we define the class, we will add to cost */

	}

	@Override
	public int getCost() {
		return cost;
	}

	@Override
	public void prepare() {
		baseBeverage.prepare();
	}

	@Override
	public void setSizeFactor(SizeFactor sizefactor) {
		baseBeverage.setSizeFactor(sizefactor);

	}

	@Override
	public void setRecipe(Recipe recipe) {
		baseBeverage.setRecipe(recipe);

	}

}
