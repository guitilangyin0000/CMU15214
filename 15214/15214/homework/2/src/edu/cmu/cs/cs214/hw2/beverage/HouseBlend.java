package edu.cmu.cs.cs214.hw2.beverage;

public class HouseBlend extends Coffee{

	/*
	 * HouseBlend class
	 */
	
	private final static int HOUSEBLEND = 80;
	
	public HouseBlend(String description) {
		super(description);
	}

	@Override
	public int getCost() {
		return HOUSEBLEND + sizefactor.getCost();
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
