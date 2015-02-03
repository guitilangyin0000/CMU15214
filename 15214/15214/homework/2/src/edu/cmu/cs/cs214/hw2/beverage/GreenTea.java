package edu.cmu.cs.cs214.hw2.beverage;

public class GreenTea extends Tea{

	/*
	 * GreenTea class
	 */
	
	private final static int GREENTEA = 100;

	public GreenTea(String description) {
		super(description);
	}

	@Override
	public int getCost() {
		return GREENTEA + sizefactor.getCost();
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
