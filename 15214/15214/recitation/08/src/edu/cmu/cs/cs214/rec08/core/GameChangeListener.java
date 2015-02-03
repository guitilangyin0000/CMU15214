package edu.cmu.cs.cs214.rec08.core;

public interface GameChangeListener {

    /**
     * Called when any tile on the board changes. This
     * includes changes to initialize a fresh board.
     *
     * @param x The x coordinate of the updated cell on the board.
     * @param y The y coordinate of the updated cell on the board.
     */
    public void squareChanged(int x, int y);

    /**
     * Called when the current player changed
     *
     * @param player The new current player.
     */
    public void currentPlayerChanged(Player player);

    /**
     * Called when the game ends, announcing the winner (or null on a tie).
     *
     * @param winner The winner of the game, or null on a tie.
     */
    public void gameEnded(Player winner);

}
