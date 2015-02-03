package edu.cmu.cs.cs214.hw2.beverage;

public class TeaSizeFactor implements SizeFactor{

	/*
	 * TeaSizeFactor class
	 */
	
	private static final int LARGESIZETEA = 70;
	private static final int MEDIUMSIZETEA = 50;
	private static final int SMALLSIZETEA = 20;
	
	private int cost;
	
	public TeaSizeFactor(String size){
		if (size.equals("large")){
			cost = LARGESIZETEA;
		}
		else if (size.equals("medium")){
			cost = MEDIUMSIZETEA;
		}
		else if (size.equals("small")){
			cost = SMALLSIZETEA;
		}
	}
	@Override
	public int getCost() {
		return cost;
	}

}
