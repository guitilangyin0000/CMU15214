package edu.cmu.cs.cs214.rec09.framework.core;

import javax.swing.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * The framework core implementation.
 */
public class GameFrameworkImpl implements GameFramework {

    private GameChangeListener gameChangeListener;
    private final List<Player> players;
    private int currentPlayer;
    private Object[][] gameGrid;
    private GamePlugin currentPlugin;

    public GameFrameworkImpl() {
        players = new ArrayList<Player>();
    }

    /**
     * Sets the framework's {@link GameChangeListener} listener to be notified
     * about changes made to the game's state.
     */
    public void setGameChangeListener(GameChangeListener listener) {
        gameChangeListener = listener;
    }

    /**
     * Registers a new {@link GamePlugin} with the game framework and notifies the
     * GUI about the change.
     */
    public void registerPlugin(GamePlugin plugin) {
        plugin.onRegister(this);
        notifyPluginRegistered(plugin);
    }

    /**
     * Starts a new game for the provided {@link GamePlugin} and notifies the GUI
     * about the change.
     */
    public void startNewGame(GamePlugin plugin) {
        final int width = plugin.getGridWidth();
        final int height = plugin.getGridHeight();

        if (currentPlugin != plugin) {
            // If currentPlugin != plugin, then we are switching to a new
            // plug-in's game. This requires a bit more work to setup since
            // different games can have different grid widths and grid heights.
            if (currentPlugin != null)
                currentPlugin.onGameClosed();
            currentPlugin = plugin;
            gameGrid = new String[height][width];
        }

        // Reset the game's internal state.
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                gameGrid[y][x] = null;
            }
        }

        currentPlayer = 0;

        // Note that the order of method calls is important here. We should
        // give the plug-in a chance to modify the game's internal initial
        // state AFTER the GUI has reset its display.
        notifyNewGameStarted(plugin);

        // Invoke the plug-in lifecycle methods next time through the event
        // queue (we need to wait for the GUI to reset before the plug-ins
        // are allowed to make modifications to the GUI's initial state).
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // Tell the plug-in that a new game is about to begin.
                currentPlugin.onNewGame();
                // Notify the plug-in that it is the beginning of a new move.
                currentPlugin.onNewMove();
            }
        });
    }

    /**
     * Adds a player to the game framework and notifies the GUI about the
     * change.
     */
    public void addPlayer(String name) {
        players.add(new Player(name));
        setFooterText(getNumPlayersFooter());
    }

    /**
     * Removes a player from the game framework and notifies the GUI about the
     * change.
     */
    public void removePlayer(String name) {
        Iterator<Player> iterator = players.iterator();
        while (iterator.hasNext()) {
            Player player = iterator.next();
            if (player.getName().equals(name)) {
                iterator.remove();
                break;
            }
        }
        setFooterText(getNumPlayersFooter());
    }

    private String getNumPlayersFooter() {
        if (players.size() == 1) {
            return players.size() + " player added.";
        } else {
            return players.size() + " players added.";
        }
    }

    /**
     * Performs a move a specified location and notifies the GUI about the change.
     *
     * @param x The x coordinate of the grid square.
     * @param y The y coordinate of the grid square.
     */
    public void playMove(int x, int y) {
        if (currentPlugin.isMoveValid(x, y)) {
            currentPlugin.onMovePlayed(x, y);
            if (currentPlugin.isGameOver()) {
                notifyGameEnded();
                startNewGame(currentPlugin);
            } else if (currentPlugin.isMoveOver()) {
                currentPlayer = (currentPlayer + 1) % players.size();
                notifyPlayerChanged();
                currentPlugin.onNewMove();
            }
        }
    }

    /* GameFramework methods. */

    @Override
    public Player getCurrentPlayer() {
        if (players.isEmpty()) {
            throw new IllegalStateException("No players added to the game.");
        }
        return players.get(currentPlayer);
    }

    @Override
    public List<Player> getPlayers() {
        return new ArrayList<Player>(players);
    }

    @Override
    public Object getSquare(int x, int y) {
        return gameGrid[y][x];
    }

    @Override
    public void setSquare(int x, int y, Object obj) {
        gameGrid[y][x] = obj;
        notifySquareChanged(x, y, obj);
    }

    @Override
    public void setFooterText(String text) {
        notifyFooterChanged(text);
    }

    /* Notify GameChangeListener methods. */

    private void notifyPluginRegistered(GamePlugin plugin) {
        gameChangeListener.onPluginRegistered(plugin);
    }

    private void notifyNewGameStarted(GamePlugin plugin) {
        gameChangeListener.onNewGame(plugin);
    }

    private void notifyPlayerChanged() {
        gameChangeListener.onCurrentPlayerChanged(getCurrentPlayer());
    }

    private void notifyGameEnded() {
        gameChangeListener.onGameEnded(currentPlugin.getGameOverMessage());
    }

    private void notifySquareChanged(int x, int y, Object obj) {
        String text = obj != null ? obj.toString() : null;
        gameChangeListener.onSquareChanged(x, y, text);
    }

    private void notifyFooterChanged(String text) {
        gameChangeListener.onFooterTextChanged(text);
    }
}
