package edu.cmu.cs.cs214.hw3.items.animals;

import javax.swing.ImageIcon;

import edu.cmu.cs.cs214.hw3.Food;
import edu.cmu.cs.cs214.hw3.Location;
import edu.cmu.cs.cs214.hw3.Util;
import edu.cmu.cs.cs214.hw3.World;
import edu.cmu.cs.cs214.hw3.ai.AI;
import edu.cmu.cs.cs214.hw3.commands.Command;
import edu.cmu.cs.cs214.hw3.items.LivingItem;

/**
 * The {@link Fox} is an {@link ArenaAnimal} that tries to eat {@link Rabbit}s.
 */
public class Fox extends GeneralArenaAnimal{

    private static final int INITIAL_ENERGY = 100;
    private static final int MAX_ENERGY = 120;
    private static final int STRENGTH = 100;
    private static final int VIEW_RANGE = 5;
    private static final int MIN_BREEDING_ENERGY = 20;
    private static final int COOLDOWN = 3;
    private static final ImageIcon foxImage = Util.loadImage("fox.gif");


    /**
     * Create a new {@link Fox} with an {@link AI} at
     * <code>initialLocation</code>. The <code> initialLocation </code> must be
     * valid and empty
     *
     * @param foxAI the AI designed for foxes
     * @param initialLocation the location where this Fox will be created
     */
    public Fox(AI foxAI, Location initialLocation) {
        super(foxAI, initialLocation);
        this.energy = INITIAL_ENERGY;
    }

    @Override
    public void eat(Food food) {
        // Note that energy does not exceed energy limit.
        energy = Math.min(MAX_ENERGY, energy + food.getMeatCalories());
    }

    @Override
    public int getCoolDownPeriod() {
        return COOLDOWN;
    }

    @Override
    public ImageIcon getImage() {
        return foxImage;
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
        return "Fox";
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
		Fox child = new Fox(ai, location);
		return child;
	}

	@Override
	public LivingItem breed() {
		return super.Breed();
	}

}
