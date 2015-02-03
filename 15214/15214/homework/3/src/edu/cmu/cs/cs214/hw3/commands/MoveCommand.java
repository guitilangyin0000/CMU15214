package edu.cmu.cs.cs214.hw3.commands;

import edu.cmu.cs.cs214.hw3.Location;
import edu.cmu.cs.cs214.hw3.Util;
import edu.cmu.cs.cs214.hw3.World;
import edu.cmu.cs.cs214.hw3.items.MoveableItem;

/**
 * A MoveCommand is a {@link Command} which represents a {@link MovableItem}
 * moving. This Command moves that Item from one space in the world to another.
 */
public final class MoveCommand implements Command {

    private final MoveableItem item;
    private final Location targetLocation;

    /**
     * Construct a {@link MoveCommand}, where <code>item</code> is the moving
     * item and <code>targetLocation</code> is the location that
     * <code> item </code> is moving to. The target location must be within
     * <code>item</code>'s moving range and the target location must be empty
     * and valid.
     *
     * @param item the Item that is moving
     * @param targetLocation the location that Item is moving to
     */
    public MoveCommand(MoveableItem item, Location targetLocation) {
        this.item = item;
        this.targetLocation = targetLocation;
    }

    @Override
    public void execute(World world) throws InvalidCommandException {
        // If the item is dead, then it will not move.
        if (item.isDead()) {
            return;
        }
        if (!Util.isValidLocation(world, targetLocation)
                || !Util.isLocationEmpty(world, targetLocation)) {
            throw new InvalidCommandException(
                    "Invalid MoveCommand: Invalid/non-empty target location");
        }
        if (item.getMovingRange() < targetLocation.getDistance(item.getLocation())) {
            throw new InvalidCommandException(
                    "Invalid MoveCommand: Target location farther than moving range");
        }

        item.moveTo(targetLocation);
    }

}