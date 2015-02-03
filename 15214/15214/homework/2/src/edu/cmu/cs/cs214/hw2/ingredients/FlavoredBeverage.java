package edu.cmu.cs.cs214.hw2.ingredients;

import edu.cmu.cs.cs214.hw2.beverage.Beverage;
import edu.cmu.cs.cs214.hw2.beverage.Coffee;
import edu.cmu.cs.cs214.hw2.beverage.Tea;

public abstract class FlavoredBeverage extends Beverage {

	protected Beverage baseBeverage;
	protected int cost;
	public boolean isCoffee;
	
    public FlavoredBeverage(String description, Beverage beverage) {
		super(description);
		
		this.baseBeverage = beverage;
		cost = baseBeverage.getCost();
		/* because beverage is updating, so we will get the latest cost */
		
		/* the first and second conditions are for the entry cases */
		if (beverage instanceof Coffee) {
			isCoffee = true;
		}else if (beverage instanceof Tea) {
			isCoffee = false;
		/* the last condition is for duplicate defining case*/
		}else{
			isCoffee = ((FlavoredBeverage)beverage).isCoffee;
		}
	}
}
