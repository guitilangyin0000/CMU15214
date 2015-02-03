package edu.cmu.cs.cs214.hw2.beverage;

public class WhiteTea extends Tea{
	/*
	 * WhiteTea class
	 */
	
	private final static int WHITETEA = 100;

	public WhiteTea(String description) {
		super(description);
	}

	@Override
	public int getCost() {
		return WHITETEA + sizefactor.getCost();
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
