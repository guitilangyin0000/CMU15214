package edu.cmu.cs.cs214.rec14.exercises.inventory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class InventoryExercise {

	private Set<Item> items;
	
	public InventoryExercise(Set<Item> items) {
		this.items = items;
	}
	
    /**
     * 1. Get a list of all item names. Duplicates are allowed, though not required.
     */
    public List<String> getAllItemNames() {
        return new ArrayList<>();
    }

    /**
     * 2. Get a list of all distinct item names
     */
    public List<String> getDistinctItemNames() {
        return new ArrayList<>();
    }

    /**
     * 3. Calculate the total price of all items
     */
    public int getTotalPrice() {
        return 0;
    }

    /**
     * 4. Calculate the total price of all items with the category "Laptop" and have prices greater than 1000
     */
    public int getTotalLaptopPrice() {
        return 0;
    }

    /**
     * 5. Create a map from each category name to the total price of all items in the category
     */
    public Map<String, Integer> getTotalPricePerCategory() {
        return new HashMap<>();
    }

}