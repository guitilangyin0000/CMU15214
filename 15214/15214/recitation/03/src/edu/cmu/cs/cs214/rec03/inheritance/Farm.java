package edu.cmu.cs.cs214.rec03.inheritance;

import java.util.HashSet;
import java.util.Set;

/**
 * Contains a collection of {@link Animal}s.
 */
public abstract class Farm {

    private Set<Animal> animals;

    public Farm() {
        animals = new HashSet<Animal>();
    }

    /**
     * @return number of {@link Animal}s in the <code>Farm</code>
     */
    public int countAnimals() {
        return animals.size();
    }

    /**
     * @param newAnimal
     *            {@link Animal} to add to the Farm
     */
    public void addAnimal(Animal newAnimal) {
        animals.add(newAnimal);
    }

}
