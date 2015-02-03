package edu.cmu.cs.cs214.rec08.core;

/**
 * The TicTacToe interface is used by the GUI to report GUI related events to
 * the core implementation.
 */
public interface TicTacToe {

    /**
     * Register a game change listener to be notified of game change events.
     *
     * @param listener The listener to be notified of game change events.
     */
    public void addGameChangeListener(GameChangeListener listener);

    /**
     * Add a player to the game.
     *
     * @param The player to add to the game.
     */
    public void addPlayer(Player player);

    /**
     * Get the player that currently has the turn.
     * 
     * @return The player that currently has the turn.
     */
    public Player getCurrentPlayer();

    /**
     * Get the game grid's height (number of squares vertically).
     *
     * @return The game grid's height.
     */
    public int getGridHeight();

    /**
     * Get the game grid's width (number of squares horizontally).
     *
     * @return The game grid's width.
     */
    public int getGridWidth();


    /**
     * Get the player who placed a move in a given location.
     *
     * @param x The x coordinate associated with the desired location.
     * @param y The y coordinate associated with the desired location.
     *
     * @return The player who moved at this location or null if no move has been
     *         played at this location.
     */
    public Player getSquare(int x, int y);

    /**
     * Attempts to place the current player's symbol on a square in the game
     * grid. If true is returned, then the move is valid and the change is made;
     * otherwise, the move is invaid and nothing is changed.
     *
     * @param x The x coordinate of the current player's move.
     * @param y The y coordinate of the current player's move.
     *
     * @throws IllegalStateException if the game has not yet been started.
     *
     * @return true if the move was valid and made, false otherwise false can
     *         mean that the coordinates were outside of the board or occupied
     *         already.
     */
    public boolean playMove(int x, int y);

    /**
     * Starts (or restarts) the game.
     */
    public void startNewGame();

}
