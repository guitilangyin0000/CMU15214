package edu.cmu.cs.cs214.hw2.beverage;

public class TeaDrink implements Recipe{

	/*
	 * TeaDrink class
	 */
	
	public TeaDrink() {
		return;
	}

	@Override
	public void prepare() {
		System.out.println("add water");
		System.out.println("add tea bags");
		System.out.println("add non-milk ingredients");
		System.out.println("add milk");
	}

}
