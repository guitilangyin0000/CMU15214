package edu.cmu.cs.cs214.hw2.beverage;

public class MilkFreeCoffeeDrink implements Recipe{

	/*
	 * MilkFreeCoffeeDrink class
	 */
	
	public MilkFreeCoffeeDrink(){
		return;
	}
	@Override
	public void prepare() {
		System.out.println("add coffee");
		System.out.println("process ingredients");	
		System.out.println("add ingredients");	
	}

}
