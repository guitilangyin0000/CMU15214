package edu.cmu.cs.cs214.hw3.commands;

import edu.cmu.cs.cs214.hw3.World;
import edu.cmu.cs.cs214.hw3.items.LivingItem;

/**
 * A WaitCommand is a {@link Command} which represents doing nothing
 */
public final class WaitCommand implements Command {

    @Override
    public void execute(World w) {
        // Do nothing.
    }

}
