package edu.cmu.cs.cs214.hw2.beverage;

public class MilkFreeTeaDrink implements Recipe {

	/*
	 * MilkFreeTeaDrink class
	 */
	
	public MilkFreeTeaDrink() {
		return;
	}

	@Override
	public void prepare() {
		System.out.println("add water");
		System.out.println("add tea bags");
		System.out.println("add all ingredients");
	}
}
