package edu.cmu.cs.cs214.hw2.beverage;

abstract public class Tea extends Beverage{
	
	/*
	 * Tea abstract class
	 */
	
	protected SizeFactor sizefactor;
	protected Recipe recipe;

	public Tea(String description) {
		super(description);
	}

}
