package edu.cmu.cs.cs214.hw2.beverage;

public class Espresso extends Coffee{

	/*
	 * Espresso class
	 */
	private final static int ESPRESSO = 100;
	
	public Espresso(String description) {
		super(description);
	}

	@Override
	public int getCost() {
		return ESPRESSO + sizefactor.getCost();
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
