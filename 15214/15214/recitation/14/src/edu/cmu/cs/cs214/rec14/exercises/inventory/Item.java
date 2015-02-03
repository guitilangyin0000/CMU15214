package edu.cmu.cs.cs214.rec14.exercises.inventory;

public class Item {
	
	public final String name;
	public final String category;
	public final int price;
	
	public Item(String name, String category, int price) {
		this.name = name;
		this.category = category;
		this.price = price;
	}
}