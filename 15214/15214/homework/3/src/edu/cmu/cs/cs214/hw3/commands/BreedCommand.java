package edu.cmu.cs.cs214.hw3.commands;

import edu.cmu.cs.cs214.hw3.Location;
import edu.cmu.cs.cs214.hw3.Util;
import edu.cmu.cs.cs214.hw3.World;
import edu.cmu.cs.cs214.hw3.items.LivingItem;

/**
 * A BreedCommand is a {@link Command} which represents a {@link LivingItem}
 * breeding. This Command inserts a 'child' into the given World.
 */
public final class BreedCommand implements Command {

    private final LivingItem item;
    private final Location target;

    /**
     * Constructor where <code>item</code> is the LivingItem that is breeding
     * and <code>target</code> is the location where the child will appear. The
     * child must be born at an empty location adjacent to the parent.
     *
     * @param item   the parent LivingItem
     * @param target the location where child will appear
     */
    public BreedCommand(LivingItem item, Location target) {
        this.item = item;
        this.target = target;
    }

    @Override
    public void execute(World world) throws InvalidCommandException {
        if (!Util.isValidLocation(world, target) || !Util.isLocationEmpty(world, target)) {
            throw new InvalidCommandException(
                    "Invalid BreedCommand: Invalid/non-empty breeding target location");
        }

        LivingItem child = item.breed();
        child.moveTo(target);
        world.addItem(child);
        world.addActor(child);
    }

}
