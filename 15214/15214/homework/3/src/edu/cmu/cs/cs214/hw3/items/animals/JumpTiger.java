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
import edu.cmu.cs.cs214.hw3.items.LivingItem;

/**
 * This is a simple implementation of a JumpTiger. It never loses energy and randomly 
 * jump from its current location, it can jump across 1 grid. For example, (10, 10) ->
 * (8, 10) || (10, 8) || (12, 10) || (10, 12)
 */
public class JumpTiger extends GeneralAnimal {
    private static final ImageIcon jumpTigerImage = Util.loadImage("jumpTiger.gif");

    private static final int MEAT_CALORIES = 200;
    private static final int STRENGTH = 150;

    private boolean isDead;

    /**
     * Create a new JumpTiger at <code>initialLocation</code>. The
     * <code>initialLocation</code> must be valid and empty.
     *
     * @param initialLocation the location where the Gnat will be created
     */
    public JumpTiger(Location initialLocation) {
        super(initialLocation);
        this.isDead = false;
    }

    @Override
    public ImageIcon getImage() {
        return jumpTigerImage;
    }

    @Override
    public String getName() {
        return "JumpTiger";
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
        return 2;
    }

    @Override
    public Command getNextAction(World world) {
    	
        Direction dir = Util.getRandomDirection();
        Location tmpLocation = new Location(this.getLocation(), dir);
        Location targetLocation = new Location(tmpLocation, dir);

        if (Util.isValidLocation(world, targetLocation)
                && Util.isLocationEmpty(world, targetLocation)) {
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
        // Never eats.
    }

    @Override
    public int getMovingRange() {
        return 2; // Can only move to adjacent locations.
    }
}
