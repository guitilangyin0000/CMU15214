package edu.cmu.cs.cs214.hw2.beverage;

public class CoffeeSizeFactor implements SizeFactor {
	
	/*
	 * CoffeeSizeFactor class
	 */

	/* define the price of different size */
	private static final int LARGESIZECOFFEE = 100;
	private static final int MEDIUMSIZECOFFEE = 70;
	private static final int SMALLSIZECOFFEE = 40;
	
	/* define the cost */
	private int cost;

	public CoffeeSizeFactor(String size) {
		if (size.equals("large")) {
			cost = LARGESIZECOFFEE;
		} else if (size.equals("medium")) {
			cost = MEDIUMSIZECOFFEE;
		} else if (size.equals("small")) {
			cost = SMALLSIZECOFFEE;
		}
	}

	@Override
	public int getCost() {
		return cost;
	}
}
