package edu.cmu.cs.cs214.hw2.ingredients;

import edu.cmu.cs.cs214.hw2.beverage.Beverage;
import edu.cmu.cs.cs214.hw2.beverage.Recipe;
import edu.cmu.cs.cs214.hw2.beverage.SizeFactor;

public class WhipCream extends FlavoredBeverage {

	/*
	 * WhipCream class
	 */
	
	private final static int WHIPCREAM = 30;

	public WhipCream(String description, Beverage beverage) {
		super(description, beverage);
		cost += WHIPCREAM;

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
