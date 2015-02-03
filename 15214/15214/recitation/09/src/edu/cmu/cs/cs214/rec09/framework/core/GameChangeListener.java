package edu.cmu.cs.cs214.rec09.framework.core;

import edu.cmu.cs.cs214.rec09.framework.gui.GameFrameworkGui;

/**
 * An observer interface that listens for changes in a game's state. The
 * {@link GameFrameworkImpl} calls these methods to notify the {@link GameFrameworkGui}
 * when it should update its display.
 */
public interface GameChangeListener {

    /**
     * Called when a new {@link GamePlugin} is registered with the framework
     *
     * @param plugin The Plugin that has just been registered.
     */
    public void onPluginRegistered(GamePlugin plugin);

    /**
     * Called when a new game has started. This method is called both when a
     * game has been reset (i.e. a player has won and a new game has started)
     * and when the current game has switched (i.e. the user has selected on a
     * new game to play in the 'New Game' sub-menu). This method is called
     * immediately before the plugin's {@link GamePlugin#onNewGame()} method is
     * called, giving the plug-in a chance to modify the initial state of the
     * game board after the GUI has been reset (if necessary).
     *
     * @param plugin The game plug-in that is about to start.
     */
    public void onNewGame(GamePlugin plugin);

    /**
     * Called when the current player has changed.
     *
     * @param player The new current player.
     */
    public void onCurrentPlayerChanged(Player player);

    /**
     * Called when the framework's footer text has changed.
     *
     * @param text The new footer text to display.
     */
    public void onFooterTextChanged(String text);

    /**
     * Called when a square in the grid has changed and the GUI display should
     * be updated.
     *
     * @param x The x coordinate of the grid square.
     * @param y The y coordinate of the grid square.
     * @param text The text to display for the grid square.
     */
    public void onSquareChanged(int x, int y, String text);

    /**
     * Called when the game has ended, prompting a dialog to be shown.
     *
     * @param gameOverMessage The message to show as the dialog's content.
     */
    public void onGameEnded(String gameOverMessage);

}