package edu.cmu.cs.cs214.rec08.core;

import java.util.ArrayList;
import java.util.List;

/**
 * The implementation of the Tic-Tac-Toe game. 
 * You do not need to look at this file in order to
 * develop a UI for the game. The Tic-Tac-Toe interface
 * should have provided sufficient information.
 *
 */
public class TicTacToeImpl implements TicTacToe {

    private static final int DEFAULT_GRID_WIDTH = 3;
    private static final int DEFAULT_GRID_HEIGHT = 3;

    private final int gridWidth;
    private final int gridHeight;
    private final List<Player> players;
    private final List<GameChangeListener> gameChangeListeners;
    private final Player gameGrid[][];

    private int currentPlayerIndex;
    private boolean hasStarted;

    /**
     * Initialize a game of Tic-Tac-Toe with a 3x3 game grid.
     */
    public TicTacToeImpl() {
        this(DEFAULT_GRID_WIDTH, DEFAULT_GRID_HEIGHT);
    }

    /**
     * Initialize a game of Tic-Tac-Toe with a custom grid size.
     */
    public TicTacToeImpl(int width, int height) {
        gridWidth = width;
        gridHeight = height;
        players = new ArrayList<Player>();
        gameChangeListeners = new ArrayList<GameChangeListener>();
        gameGrid = new Player[gridHeight][gridWidth];
    }

    @Override
    public void addGameChangeListener(GameChangeListener listener) {
        gameChangeListeners.add(listener);
    }

    @Override
    public void addPlayer(Player player) {
        players.add(player);
    }

    @Override
    public Player getCurrentPlayer() {
        checkStarted();
        checkHasPlayers();
        return players.get(currentPlayerIndex);
    }

    @Override
    public int getGridHeight() {
        return gridHeight;
    }

    @Override
    public int getGridWidth() {
        return gridWidth;
    }

    @Override
    public Player getSquare(int x, int y) {
        checkStarted();
        checkHasPlayers();
        return gameGrid[y][x];
    }

    @Override
    public boolean playMove(int x, int y) {
        checkStarted();
        checkHasPlayers();

        if (x < 0 || x > gridWidth || y < 0 || y > gridHeight) {
            return false;
        }
        if (gameGrid[y][x] != null) {
            return false;
        }

        gameGrid[y][x] = getCurrentPlayer();
        notifyMoveMade(x, y);
        checkGameEnd();
        switchPlayers();

        return true;
    }

    @Override
    public void startNewGame() {
        hasStarted = true;
        for (int x = 0; x < gridWidth; x++) {
            for (int y = 0; y < gridHeight; y++) {
                gameGrid[y][x] = null;
                notifyMoveMade(x, y);
            }
        }
        currentPlayerIndex = 0;
        notifyPlayerChanged();
    }

    private void switchPlayers() {
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
        notifyPlayerChanged();
    }

    private void notifyMoveMade(int x, int y) {
        for (GameChangeListener listener : gameChangeListeners) {
            listener.squareChanged(x, y);
        }
    }

    private void notifyPlayerChanged() {
        for (GameChangeListener listener : gameChangeListeners) {
            listener.currentPlayerChanged(getCurrentPlayer());
        }
    }

    private void notifyGameEnd(Player winner) {
        for (GameChangeListener listener : gameChangeListeners) {
            listener.gameEnded(winner);
        }
    }

    private void checkGameEnd() {
        if (hasWon()) {
            notifyGameEnd(getCurrentPlayer());
        } else if (isFull()) {
            notifyGameEnd(null);
        }
    }

    /**
     * Checks if the current player has won the game.
     */
    private boolean hasWon() {
        checkStarted();
        checkHasPlayers();

        Player currentPlayer = getCurrentPlayer();

        // Check for a horizontal win.
        for (int y = 0; y < gridHeight; y++) {
            for (int x = 0; x < gridWidth; x++) {
                if (gameGrid[y][x] == null || !gameGrid[y][x].equals(currentPlayer)) {
                    break;
                }
                if (x == gridWidth - 1) {
                    return true;
                }
            }
        }

        // Check for a vertical win.
        for (int x = 0; x < gridWidth; x++) {
            for (int y = 0; y < gridHeight; y++) {
                if (gameGrid[y][x] == null || !gameGrid[y][x].equals(currentPlayer)) {
                    break;
                }
                if (y == gridHeight - 1) {
                    return true;
                }
            }
        }

        // Check for a diagonal win.
        for (int x = 0, y = 0; x < gridWidth && y < gridHeight; x++) {
            if (gameGrid[y][x] == null || !gameGrid[y][x].equals(currentPlayer)) {
                break;
            }
            if (x == gridWidth - 1 && y == gridHeight - 1) {
                return true;
            }
            y++;
        }

        // Check for anti-diagonal win.
        for (int x = gridWidth - 1, y = 0; x >= 0 && y < gridHeight; x--) {
            if (gameGrid[y][x] == null || !gameGrid[y][x].equals(currentPlayer)) {
                break;
            }
            if (x == 0 && y + 1 == gridHeight) {
                return true;
            }
            y++;
        }

        return false;
    }

    /**
     * Checks if all of the squares in the game grid are occupied.
     */
    private boolean isFull() {
        for (int x = 0; x < gridWidth; x++) {
            for (int y = 0; y < gridHeight; y++) {
                if (gameGrid[y][x] == null) {
                    return false;
                }
            }
        }
        return true;
    }

    private void checkStarted() {
        if (!hasStarted) {
            throw new IllegalStateException("Game not yet started.");
        }
    }

    private void checkHasPlayers() {
        if (players.size() < 2) {
            throw new IllegalStateException("Game must be played with at least two players.");
        }
    }
}
