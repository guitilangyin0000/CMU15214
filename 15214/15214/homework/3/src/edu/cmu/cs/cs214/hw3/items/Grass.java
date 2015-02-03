package edu.cmu.cs.cs214.hw3.items;

import javax.swing.ImageIcon;

import edu.cmu.cs.cs214.hw3.Location;
import edu.cmu.cs.cs214.hw3.Util;

/**
 * Grass will by planted by the {@link Gardener} every step at an empty
 * location if fewer than half of all locations in the world are occupied.
 */
public class Grass implements Item {
    private final static ImageIcon grassImage = Util.loadImage("grass.gif");

    private Location location;
    private boolean isDead;

    /**
     * Plant a Grass at <code> location </code>.
     * The location must be valid and empty
     *
     * @param location : the location where this grass will be created
     */
    public Grass(Location location) {
        this.location = location;
        this.isDead = false;
    }

    @Override
    public ImageIcon getImage() {
        return grassImage;
    }

    @Override
    public String getName() {
        return "grass";
    }

    @Override
    public Location getLocation() {
        return location;
    }

    @Override
    public int getPlantCalories() {
        return 10;
    }

    @Override
    public int getMeatCalories() {
        return 0;
    }

    @Override
    public void loseEnergy(int energy) {
        // Dies if loses energy.
        isDead = true;
    }

    @Override
    public boolean isDead() {
        return isDead;
    }

    @Override
    public int getStrength() {
        return 5;
    }

}