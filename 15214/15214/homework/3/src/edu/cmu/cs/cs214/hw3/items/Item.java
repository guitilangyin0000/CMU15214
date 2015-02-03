package edu.cmu.cs.cs214.hw3.items;

import javax.swing.ImageIcon;

import edu.cmu.cs.cs214.hw3.Food;
import edu.cmu.cs.cs214.hw3.Location;
import edu.cmu.cs.cs214.hw3.items.animals.ArenaAnimal;
import edu.cmu.cs.cs214.hw3.items.animals.Gnat;

/**
 * An Item represents a physical object in the virtual World that
 * occupies a field and is represented with an image.
 */
public interface Item extends Food {

    /**
     * The visualization of this Item in the world.
     *
     * @return the image of this Item
     */
    ImageIcon getImage();

    /**
     * Gets a String that serves as a unique identifier for this type of Item.
     *
     * @return the name of this item
     */
    String getName();

    /**
     * Gets the location of this Item in the World.
     *
     * @return the location in the world
     */
    Location getLocation();

    /**
     * Returns the strength of this Item. Generally, if an item possesses
     * greater strength than another, then it can eliminate the other. For
     * example, a {@link Vehicle} can run over everything that has a lower
     * strength.
     *
     * @return the strength of this Item
     */
    int getStrength();

    /**
     * Causes this Item to lose energy. The consequences of this varies for
     * different types of Items.
     * <ul>
     * <li>{@link Grass} and {@link Gnat} die when they lose energy.</li>
     * <li>{@link ArenaAnimal} reduces its energy level and it dies if its
     * energy level drops below or equal to 0</li>
     * </ul>
     *
     * @param energy the amount of energy lost
     */
    void loseEnergy(int energy);

    /**
     * Returns whether or not this Item is dead. If this Item is dead, it will
     * be removed from the World. An item is dead if it is eaten, run over by a
     * Vehicle, loses all its energy and energy level drops below or equal 0,
     * etc.
     *
     * @return true if this Item is dead, false if alive
     */
    boolean isDead();

}
