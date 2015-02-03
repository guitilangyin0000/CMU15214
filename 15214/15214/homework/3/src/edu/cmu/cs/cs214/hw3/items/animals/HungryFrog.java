package edu.cmu.cs.cs214.hw3.items.animals;

import javax.swing.ImageIcon;

import edu.cmu.cs.cs214.hw3.Direction;
import edu.cmu.cs.cs214.hw3.Food;
import edu.cmu.cs.cs214.hw3.Location;
import edu.cmu.cs.cs214.hw3.Util;
import edu.cmu.cs.cs214.hw3.World;
import edu.cmu.cs.cs214.hw3.commands.Command;
import edu.cmu.cs.cs214.hw3.commands.MoveCommand;
import edu.cmu.cs.cs214.hw3.commands.WaitCommand;
import edu.cmu.cs.cs214.hw3.items.Item;
import edu.cmu.cs.cs214.hw3.items.LivingItem;

/**
 * This is a simple implementation of a HungryFrog, it can eat Gnat when it encounter.
 * Because it's too hungry, we cannot set a upper bound of energy of it, so the eat
 * method is empty.
 */
public class HungryFrog extends GeneralAnimal {
    private static final ImageIcon hungryFrog = Util.loadImage("hungryFrog.gif");

    private static final int MEAT_CALORIES = 120;
    private static final int STRENGTH = 60;

    private boolean isDead;

    /**
     * Create a new HungryFrog at <code>initialLocation</code>. The
     * <code>initialLocation</code> must be valid and empty.
     *
     * @param initialLocation the location where the Gnat will be created
     */
    public HungryFrog(Location initialLocation) {
        super(initialLocation);
        this.isDead = false;
    }

    @Override
    public ImageIcon getImage() {
        return hungryFrog;
    }

    @Override
    public String getName() {
        return "HungryFrog";
    }


    @Override
    public int getMeatCalories() {
        return MEAT_CALORIES;
    }

    @Override
    public void loseEnergy(int energy) {
        isDead = true; // Dies if it loses energy.
    }

    @Override
    public boolean isDead() {
        return isDead;
    }


    @Override
    public int getCoolDownPeriod() {
    	// This frog is really hungry
    	// and can never be satisfied
    	return 1; 
    }

    @Override
    public Command getNextAction(World world) { 
    	
    	Direction dir = Util.getRandomDirection();
        Location targetLocation = new Location(location, dir);

        // if the target location is valid and empty, just move there
        if (Util.isValidLocation(world, targetLocation)
                && Util.isLocationEmpty(world, targetLocation)) {
            return new MoveCommand(this, targetLocation);
        }
        
        boolean isGnat = false;
        Item isGnatItem = null; 
        // traverse all the items, if is a Gnat Item, kill and eat it.
        for (Item item : world.getItems()) {
            if (item.getLocation().equals(targetLocation) && item.getName() == "Gnat") {
                isGnat = true;
                isGnatItem = item;
                break;
            }
        }
        
        if (isGnat){
        	isGnatItem.loseEnergy(Integer.MAX_VALUE);
        	// System.out.println("HungryFrog has eaten one Gnat!");
        	return new MoveCommand(this, targetLocation);
        }
 
        return new WaitCommand();
    }

    @Override
    public int getStrength() {
        return STRENGTH;
    }

    @Override
    public int getEnergy() {
        // doesn't every die, except when run over by a Vehicle
        return 100;
    }

    @Override
    public LivingItem breed() {
        return null; // Never eats.
    }

    @Override
    public void eat(Food food) {
        // After eating, there is no increasing
    	// energy for frog, because it's too hungry
    	return;
    }

    @Override
    public int getMovingRange() {
        return 1; // Can only move to adjacent locations.
    }
}
