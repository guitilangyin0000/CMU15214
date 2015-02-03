package edu.cmu.cs.cs214.hw3;

import edu.cmu.cs.cs214.hw3.items.Item;
import edu.cmu.cs.cs214.hw3.items.LivingItem;

/**
 * Food is something edible by a {@link LivingItem}.
 * It provides energy for the survival of the 
 * {@link LivingItem}. In this assignment, every {@link Item}
 * is a Food, although for a {@link LivingItem}, not all Food
 * are edible.
 */
public interface Food {

    /**
     * The energy that this food contains as a plant
     *
     * @return plant energy of this food
     */
    int getPlantCalories();

    /**
     * The energy that this food contains as an animal
     *
     * @return meat energy of this food
     */
    int getMeatCalories();
}
