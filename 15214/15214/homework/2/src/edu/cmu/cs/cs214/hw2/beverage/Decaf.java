package edu.cmu.cs.cs214.hw2.beverage;

public class Decaf extends Coffee{

	/*
	 * Decaf class
	 */

	private final static int DECAF = 50;
	
	public Decaf(String description) {
		super(description);
	}

	@Override
	public int getCost() {
		return DECAF + sizefactor.getCost();
	}

	@Override
	public void prepare() {
		recipe.prepare();
		
	}

	@Override
	public void setSizeFactor(SizeFactor sizefactor) {
		this.sizefactor = sizefactor;
		
	}

	@Override
	public void setRecipe(Recipe recipe) {
		this.recipe = recipe;
		
	}

}
