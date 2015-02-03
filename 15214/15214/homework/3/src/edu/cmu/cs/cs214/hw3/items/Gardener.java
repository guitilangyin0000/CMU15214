package edu.cmu.cs.cs214.hw3.items;

import edu.cmu.cs.cs214.hw3.Actor;
import edu.cmu.cs.cs214.hw3.Location;
import edu.cmu.cs.cs214.hw3.Util;
import edu.cmu.cs.cs214.hw3.World;
import edu.cmu.cs.cs214.hw3.commands.Command;
import edu.cmu.cs.cs214.hw3.commands.WaitCommand;

/**
 * The Gardener does not show up in the world, but it plants a {@link Grass} at
 * a random location each step if more than half of the world's locations are
 * empty. Don't worry, Grass doesn't just appear...
 */
public class Gardener implements Actor {

    @Override
    public int getCoolDownPeriod() {
        // Acts every step.
        return 1;
    }

    @SuppressWarnings("unused")
    @Override
    public Command getNextAction(World world) {
        int occupiedLocations = 0;
        for (Item item : world.getItems()) {
            occupiedLocations++;
        }

        // If the number of occupied locations is less than half of the total
        // number of locations, this Gardener plants Grass at a random location.
        int totalLocations = world.getHeight() * world.getWidth();
        if (occupiedLocations < totalLocations / 2) {
            final Location loc = Util.getRandomEmptyLocation(world);

            // An anonymous Command class which plants grass.
            return new Command() {
                @Override
                public void execute(World world) {
                    world.addItem(new Grass(loc));
                }
            };

        }

        // Else it does nothing at all.
        return new WaitCommand();
    }

}
