package edu.cmu.cs.cs214.hw2.ingredients;

import edu.cmu.cs.cs214.hw2.beverage.Beverage;
import edu.cmu.cs.cs214.hw2.beverage.Recipe;
import edu.cmu.cs.cs214.hw2.beverage.SizeFactor;

public class Jasmine extends FlavoredBeverage {

	/*
	 * Jasmine class
	 */
	
	private final static int JASMINE = 50;

	public Jasmine(String description, Beverage beverage) {
		super(description, beverage);
		cost += JASMINE;
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
