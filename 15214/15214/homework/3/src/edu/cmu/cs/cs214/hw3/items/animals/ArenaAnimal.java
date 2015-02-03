package edu.cmu.cs.cs214.hw3.items.animals;

import edu.cmu.cs.cs214.hw3.items.LivingItem;

/**
 * Abstractions for foxes and rabbits that provide additional information that
 * might be of use to the AI:
 * <ol>
 * <li>Maximum energy, given by {@link #getMaxEnergy()}</li>
 * <li>View range, given by {@link #getViewRange()}</li>
 * <li>Minimum breeding energy, given by {@link #getMinimumBreedingEnergy()}</li>
 * </ol>
 *
 */
public interface ArenaAnimal extends LivingItem {

    /**
     * Returns the limit of the {@link LivingItem}'s energy. The
     * {@link LivingItem}'s energy should never exceed this limit.
     *
     * @return the energy limit of this animal
     */
    int getMaxEnergy();

    /**
     * Returns the range of the animal's vision. The range is measured in
     * Manhattan Distance, for example, if an animal has view range of 2, then
     * it can see all valid locations in the rectangle
     * {(x-2,y-2),(x+2,y-2),(x-2,y+2),(x+2,y+2)}, where (x,y) are the
     * coordinates of its current location.
     *
     * @return the view range of this animal
     */
    int getViewRange();

    /**
     * Returns the minimum energy required for an animal to breed
     *
     * @return the minimum breeding energy of this animal
     */
    int getMinimumBreedingEnergy();

}
