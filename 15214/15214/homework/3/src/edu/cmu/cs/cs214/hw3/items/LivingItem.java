package edu.cmu.cs.cs214.hw3.items;

import edu.cmu.cs.cs214.hw3.Actor;
import edu.cmu.cs.cs214.hw3.Food;
import edu.cmu.cs.cs214.hw3.items.animals.ArenaAnimal;

/**
 * A LivingItem has properties of a life. In addition to moving, it can eat
 * food, breed children, and contain energy.
 */
public interface LivingItem extends MoveableItem, Actor {

    /**
     * Returns the current energy of this living thing. A {@link LivingItem}
     * gains energy by eating and loses energy by performing actions. If its
     * energy level drops below or equal to 0, it dies.
     *
     * @return current energy level
     */
    int getEnergy();

    /**
     * Breeds a child of this {@link LivingItem}, the child must be the same
     * animal as the parent. A {@link LivingItem} can only breed when all of the
     * following conditions are satisfied:
     * <ol>
     * <li>There is an empty location adjacent to its location</li>
     * <li>If it is an {@link ArenaAnimal}, its energy level must exceeds its
     * breeding limit, specified by
     * {@link ArenaAnimal#getMinimumBreedingEnergy()}</li>
     * </ol>
     * After breeding, both the parent and the child should have half of
     * the energy of the parent's energy.
     *
     * @return child the offspring of this animal
     */
    LivingItem breed();

    /**
     * Eat a {@link Food}, the food must be adjacent to and have less strength
     * than this {@link LivingItem}.
     *
     * @param food the Food to be eaten
     */
    void eat(Food food);
}
