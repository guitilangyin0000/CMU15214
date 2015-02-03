package edu.cmu.cs.cs214.hw3;

import java.util.Set;

import edu.cmu.cs.cs214.hw3.items.Item;

/**
 * The virtual world simulation. Holds all of the {@link Item}s in this
 * simulation and contains methods which insert and retrieve these Items.
 */
public interface World extends ArenaWorld {

    /**
     * Adds an {@link Actor} to this world. This actor will then be called to
     * take action every N steps where N is given by its cool-down period.
     *
     * @see Actor#getCoolDownPeriod()
     *
     * @param actor the Actor to be added to this world
     */
    void addActor(Actor actor);

    /**
     * Add an {@link Item} to this world.
     *
     * @param item the Item to be added to this world
     */
    void addItem(Item item);

    /**
     * Returns the collection of {@link Item}s in this World. The
     * <code>Iterable</code> interface enables this collection to be used in a
     * "for each" loop:
     *
     * <pre>
     *   e.g. <code> for(Item item : world.getItems()) {...}
     * </pre>
     *
     * @return a collection of all Items in this World
     */
    Iterable<Item> getItems();

    /**
     * Retrieves the set of {@link Item}s surrounding a location within a range
     * in this World.
     *
     * @param loc the location around which items are retrieved
     * @param range the maximum distance an item can be from the location
     * @return the set of items found
     */
    Set<Item> searchSurroundings(Location loc, int range);

    /**
     * Performs a single step in the simulation.
     */
    void step();

}
