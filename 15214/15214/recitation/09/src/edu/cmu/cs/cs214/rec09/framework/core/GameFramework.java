package edu.cmu.cs.cs214.rec09.framework.core;

import java.util.List;

/**
 * The interface by which {@link GamePlugin}s can directly interact with the game
 * framework.
 */
public interface GameFramework {

    /**
     * Get the {@link Player} that currently has the move.
     */
    public Player getCurrentPlayer();

    /**
     * Get the list of {@link Player}s that have been added to the framework.
     */
    public List<Player> getPlayers();

    /**
     * Get the object associated with the grid square located at (x, y).
     *
     * @param x The x coordinate of the grid square.
     * @param y The y coordinate of the grid square.
     * @return The object associated with the grid square at location (x, y), or
     *         null if no object has been set at this location since the
     *         beginning of the game.
     */
    public Object getSquare(int x, int y);

    /**
     * Set the object associated with the grid square located at (x, y). The
     * framework will display the text returned by the object's
     * {@link Object#toString()} method, or the empty string if the object is
     * null.
     *
     * @param x The x coordinate of the grid square.
     * @param y The y coordinate of the grid square.
     * @param obj The object to set at the grid square.
     */
    public void setSquare(int x, int y, Object obj);

    /**
     * Sets the text to display at the bottom of the framework's display.
     *
     * @param text The text to display.
     */
    public void setFooterText(String text);

}