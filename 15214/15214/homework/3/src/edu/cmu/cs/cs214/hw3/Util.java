package edu.cmu.cs.cs214.hw3;

import java.net.URL;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

import javax.swing.ImageIcon;

import edu.cmu.cs.cs214.hw3.items.Item;

/**
 * Utility class for common tasks.
 */
public final class Util {

    private Util() {
        // This class should not be instantiated.
    }

    // Use a fixed seed so that simulations are repeatable.
    public static final Random RAND = new Random(2013);

    /**
     * Checks if location is within the bounds of the given {@link ArenaWorld}.
     *
     * @param world the current ArenaWorld
     * @param location the location to be checked on validity
     * @return true if the location valid, false otherwise
     */
    public static boolean isValidLocation(ArenaWorld world, Location location) {
        return location.getX() >= 0 && location.getX() < world.getWidth() && location.getY() >= 0
                && location.getY() < world.getHeight();
    }

    /**
     * @return a random {@link Direction}
     */
    public static Direction getRandomDirection() {
        return Direction.values()[RAND.nextInt(Direction.values().length)];
    }

    /**
     * Returns the direction from a source location towards a destination
     * location.
     *
     * @param src the starting location
     * @param dest The destination location.
     * @return direction towards destination
     * @throws NullPointerException If src or dest are null.
     */
    public static Direction getDirectionTowards(Location src, Location dest) {
        if (src == null || dest == null) {
            throw new NullPointerException("Location cannot be null.");
        }

        int dx = dest.getX() - src.getX();
        int dy = dest.getY() - src.getY();

        if (Math.abs(dx) > Math.abs(dy)) {
            if (dx > 0) {
                return Direction.EAST;
            } else {
                return Direction.WEST;
            }
        } else {
            if (dy > 0) {
                return Direction.SOUTH;
            } else {
                return Direction.NORTH;
            }
        }
    }

    /**
     * Returns a random, empty location in the specified {@link World} or null
     * if the world is full.
     *
     * @param world the current World
     * @return a random empty location, or null if no empty location exists
     */
    public static Location getRandomEmptyLocation(World world) {
        return getRandomEmptyLocation(world, 0, 0, world.getWidth(), world.getHeight());
    }

    /**
     * Returns a random, empty location in the specified World within a bounding
     * box (or null if the world is full).
     *
     * @param world the current World
     * @param startW the starting x-coordinate (x-coordinate of the top-left
     *            corner of the bounding box)
     * @param startH the starting y-coordinate (y-coordinate of the top-left
     *            corner of the bounding box)
     * @param width the width of the bounding box
     * @param height the height of the bounding box
     * @return a random, empty location in a bounding box, or null if no such
     *         location exists
     */
    public static Location getRandomEmptyLocation(World world, int startW, int startH, int width,
            int height) {
        int x = RAND.nextInt(width - startW) + startW;
        int y = RAND.nextInt(height - startH) + startH;

        for (int i = startW; i < width; i++) {
            x = ((x + 1) % (width - startW)) + startW;
            for (int j = startH; j < height; j++) {
                y = ((y + 1) % (height - startH)) + startH;
                Location loc = new Location(x, y);
                if (isLocationEmpty(world, loc)) {
                    return loc;
                }
            }
        }

        // No position free.
        return null;
    }

    
    /**
     * Checks if a location in the specified World is valid and empty.
     *
     * @param world the current World
     * @param location the Location to be checked on emptiness
     * @return true if the location is valid and empty, false otherwise
     */
    public static boolean isLocationEmpty(World world, Location location) {
        if (!isValidLocation(world, location)) {
            return false;
        }
        for (Item item : world.getItems()) {
            if (item.getLocation().equals(location)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Returns a random empty Location in the specified World that is adjacent
     * to <code>loc</code>, or null if there is no empty adjacent location.
     *
     * @param world the current World
     * @param loc the center location, all empty locations returned (if any)
     *            are adjacent to loc
     * @return empty adjacent location, or null if none exists
     */
    public static Location getRandomEmptyAdjacentLocation(World world, Location loc) {
        Location[] neighbors = new Location[3 * 3]; // 3 x 3 bounding box
        int numLocs = 0;
        for (int x = loc.getX() - 1; x <= loc.getX() + 1; x++) {
            for (int y = loc.getY() - 1; y <= loc.getY() + 1; y++) {
                Location l = new Location(x, y);
                if (isValidLocation(world, l) && isLocationEmpty(world, l)) {
                    neighbors[numLocs] = l;
                    numLocs++;
                }
            }
        }
        if (numLocs == 0)
            return null;
        return neighbors[RAND.nextInt(numLocs)];
    }

    /**
     * Loads an image, given the name of the file (for example, "fox.gif"). This
     * file must be in the 'image' folder.
     *
     * @param filename the name of the image
     * @return A loaded {@link ImageIcon}
     */
    public static ImageIcon loadImage(String filename) {
        URL resource = Util.class.getResource("images/" + filename);
        if (resource == null) {
            System.err.println("Image images/" + filename + " not found");
            return getUnknownImage();
        }
        return new ImageIcon(resource);
    }

    /**
     * Loads an image named "unknown.gif" under the "image" folder
     *
     * @return A loaded {@link ImageIcon}
     */
    private static ImageIcon getUnknownImage() {
        URL resource = Util.class.getResource("images/unknown.gif");
        if (resource == null) {
            throw new RuntimeException("images not available, check resources");
        }
        return new ImageIcon(resource);
    }

}
