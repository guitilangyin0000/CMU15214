package edu.cmu.cs.cs214.hw2.beverage;

public class CoffeeDrink implements Recipe {
	/*
	 * CoffeeDrink class
	 */
	public CoffeeDrink() {
		return;
	}

	@Override
	public void prepare() {
		System.out.println("steam milk");
		System.out.println("add coffee");
		System.out.println("mix steamed milk and coffee");
		System.out.println("process non-milk ingredients");
		System.out.println("add all ingredients");
	}
}
