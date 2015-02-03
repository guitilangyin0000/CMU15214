package edu.cmu.cs.cs214.hw2.beverage;

public abstract class Coffee extends Beverage{

	/*
	 * Coffee abstract class
	 */
	protected SizeFactor sizefactor;
	protected Recipe recipe;
	
	public Coffee(String description) {
		super(description);
	}
	
}
