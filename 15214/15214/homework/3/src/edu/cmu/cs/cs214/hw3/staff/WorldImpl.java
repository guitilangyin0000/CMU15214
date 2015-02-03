package edu.cmu.cs.cs214.hw3.staff;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import edu.cmu.cs.cs214.hw3.Actor;
import edu.cmu.cs.cs214.hw3.Location;
import edu.cmu.cs.cs214.hw3.World;
import edu.cmu.cs.cs214.hw3.commands.Command;
import edu.cmu.cs.cs214.hw3.commands.InvalidCommandException;
import edu.cmu.cs.cs214.hw3.items.Item;
import edu.cmu.cs.cs214.hw3.items.animals.ArenaAnimal;

/**
 * The implementation of the {@link World}.
 *
 * Do not modify this file.
 */
public class WorldImpl implements World {

    private static final int DEFAULT_WIDTH = 40;
    private static final int DEFAULT_HEIGHT = 40;

    private final int width;
    private final int height;

    private final Map<Actor, Integer> actorWait = new HashMap<Actor, Integer>();
    private final Set<Item> items = new HashSet<Item>();
    private final List<Actor> actors = new ArrayList<Actor>();

    /**
     * Default constructor which creates this World with the default number of
     * columns and rows.
     */
    public WorldImpl() {
        this(DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }

    /**
     * Constructor which creates this World with the specified width and height.
     * The width and height must both be greater than or equal to zero.
     *
     * @param width the number of columns in this World
     * @param height the number of rows in this World
     */
    public WorldImpl(int width, int height) {
        if (width < 0 || height < 0) {
            throw new IllegalArgumentException("Width and height must be non-negative.");
        }
        this.width = width;
        this.height = height;
    }

    @Override
    public void addActor(Actor actor) {
        actors.add(actor);
        actorWait.put(actor, 0);
    }

    @Override
    public void addItem(Item item) {
        items.add(item);
    }

    @Override
    public Iterable<Item> getItems() {
        // Lazily removes all of the dead items.
        Iterator<Item> it = items.iterator();
        while (it.hasNext()) {
            Item item = it.next();
            if (item.isDead()) {
                it.remove();
                actorWait.remove(item);
                actors.remove(item);
            }
        }

        return Collections.unmodifiableSet(items);
    }

    @Override
    public void step() {
        // Checks whether or not each Actor has completed its cooldown. If it
        // has, then the Actor's next command is executed. Otherwise, The
        // Actor's cooldown time decreases by 1.
        for (Actor actor : new ArrayList<Actor>(actors)) {
            Integer waitPeriod = actorWait.get(actor);
            // If waitPeriod is null, then the actor has been removed by another
            // Command already, so do nothing in that case.
            if (waitPeriod != null) {
                if (waitPeriod <= 0) {
                    Command command = actor.getNextAction(this);
                    actorWait.put(actor, actor.getCoolDownPeriod());
                    try {
                        command.execute(this);
                    } catch (InvalidCommandException e) {
                        e.printStackTrace();
                    }
                } else {
                    actorWait.put(actor, waitPeriod - 1);
                }
            }
        }
    }

    @Override
    public Set<Item> searchSurroundings(ArenaAnimal animal) {
        return searchSurroundings(animal.getLocation(), animal.getViewRange());
    }

    @Override
    public Set<Item> searchSurroundings(Location loc, int range) {
        Set<Item> result = new HashSet<Item>();
        for (Item item : items) {
            if (item.getLocation().getDistance(loc) <= range) {
                result.add(item);
            }
        }
        return result;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

}
