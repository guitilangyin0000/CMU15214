package edu.cmu.cs.cs214.hw3.items.animals;

import javax.swing.ImageIcon;

import edu.cmu.cs.cs214.hw3.Food;
import edu.cmu.cs.cs214.hw3.Location;
import edu.cmu.cs.cs214.hw3.Util;
import edu.cmu.cs.cs214.hw3.World;
import edu.cmu.cs.cs214.hw3.ai.AI;
import edu.cmu.cs.cs214.hw3.commands.Command;
import edu.cmu.cs.cs214.hw3.items.Grass;
import edu.cmu.cs.cs214.hw3.items.LivingItem;

/**
 * The {@link Rabbit} is an {@link ArenaAnimal} that eats {@link Grass} and can
 * be eaten by {@link Fox}.
 */
public class Rabbit extends GeneralArenaAnimal {

    private static final int INITIAL_ENERGY = 40;
    private static final int MAX_ENERGY = 60;
    private static final int STRENGTH = 60;
    private static final int MIN_BREEDING_ENERGY = 10;
    private static final int VIEW_RANGE = 3;
    private static final int COOLDOWN = 2;
    private static final ImageIcon rabbitImage = Util.loadImage("rabbit.gif");

    /**
     * Create a new {@link Rabbit} with an {@link AI} at
     * <code> initialLocation </code>. The <code> initialLoation
     * </code> must be valid and empty.
     *
     * @param rabbitAI : The AI designed for rabbits
     * @param initialLocation : the location where this rabbit will be created
     */
    public Rabbit(AI rabbitAI, Location initialLocation) {
        super(rabbitAI, initialLocation);
        energy = INITIAL_ENERGY;
    }

    @Override
    public void eat(Food food) {
        // Note that energy does not exceed energy limit.
        energy = Math.min(MAX_ENERGY, energy + food.getPlantCalories());
    }

    @Override
    public int getCoolDownPeriod() {
        return COOLDOWN;
    }

    @Override
    public ImageIcon getImage() {
        return rabbitImage;
    }

    @Override
    public int getMaxEnergy() {
        return MAX_ENERGY;
    }

    @Override
    public int getMinimumBreedingEnergy() {
        return MIN_BREEDING_ENERGY;
    }

    @Override
    public int getMovingRange() {
        return 1; // Can only move to adjacent locations.
    }

    @Override
    public String getName() {
        return "Rabbit";
    }


    @Override
    public int getStrength() {
        return STRENGTH;
    }

    @Override
    public int getViewRange() {
        return VIEW_RANGE;
    }


	@Override
	public GeneralArenaAnimal getChild() {
		GeneralArenaAnimal child = new Rabbit(ai, location);
		return child;
	}

	@Override
	public LivingItem breed() {
		return super.Breed();
	}
}
