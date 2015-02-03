package edu.cmu.cs.cs214.hw2.beverage;

public class RedTea extends Tea{

	/*
	 * RedTea class
	 */
	
	private final static int REDTEA = 80;
	
	public RedTea(String description) {
		super(description);
	}
	
	@Override
	public int getCost() {
		return REDTEA + sizefactor.getCost();
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
