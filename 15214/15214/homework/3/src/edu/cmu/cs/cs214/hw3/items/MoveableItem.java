package edu.cmu.cs.cs214.hw3.items;

import edu.cmu.cs.cs214.hw3.Location;

/**
 * A MovableItem represents Items that can change its location in the World.
 * A {@link MoveableItem} can only move to a valid empty location that is not
 * farther than its moving range from its current location at each step.
 */
public interface MoveableItem extends Item {

    /**
     * Move to the target location. The <code> targetLocation </code> must be
     * valid and empty.
     *
     * @param targetLocation the location that this item is moving to
     */
    void moveTo(Location targetLocation);

    /**
     * Returns the maximum distance that this item can move in one step. For
     * example, a {@link MoveableItem} with moving range 1 can only move to
     * adjacent locations.
     *
     * @return the maximum moving distance
     */
    int getMovingRange();
}
