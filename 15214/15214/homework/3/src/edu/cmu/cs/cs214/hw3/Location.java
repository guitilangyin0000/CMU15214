package edu.cmu.cs.cs214.hw3;

/**
 * Represents an (x, y) location in the world.
 */
public final class Location {

    private int x;
    private int y;

    public Location(Location loc) {
        this(loc.x, loc.y);
    }

    public Location(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Returns a new {@link Location} that is one space away from the specified
     * source {@link Location} in the given {@link Direction}.
     *
     * @param src the specified location
     * @param dir the specified direction
     * @throws NullPointerException if src or dir are null.
     */
    public Location(Location src, Direction dir) {
        if (src == null) {
            throw new NullPointerException("Location cannot be null.");
        } else if (dir == null) {
            throw new NullPointerException("Direction cannot be null.");
        }

        this.x = src.x;
        this.y = src.y;

        switch (dir) {
        case NORTH:
            this.y -= 1;
            break;
        case SOUTH:
            this.y += 1;
            break;
        case EAST:
            this.x += 1;
            break;
        case WEST:
            this.x -= 1;
            break;
        }
    }

    /**
     * @return the x-coordinate of this Location
     */
    public int getX() {
        return x;
    }

    /**
     * @return the y-coordinate of this Location
     */
    public int getY() {
        return y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Location)) {
            return false;
        }
        Location loc = (Location) o;
        return x == loc.x && y == loc.y;
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        return result;
    }

    @Override
    public String toString() {
        return String.format("%s(%d,%d)", getClass().getSimpleName(), x, y);
    }

    /**
     * Returns the distance between this Location to another Location, measured
     * as the max of the horizontal or vertical distance between the Locations.
     *
     * @param otherLocation the other location
     * @return distance away from <code>otherLocation</code>
     */
    public int getDistance(Location otherLocation) {
        return Math.max(Math.abs(this.getX() - otherLocation.getX()), Math.abs(this.getY() - otherLocation.getY()));
    }

}
