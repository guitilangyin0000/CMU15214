package edu.cmu.cs.cs214.rec09.plugin;

import edu.cmu.cs.cs214.rec09.framework.core.GameFramework;
import edu.cmu.cs.cs214.rec09.framework.core.GamePlugin;
import edu.cmu.cs.cs214.rec09.framework.core.Player;

/**
 * TODO: Implement Tic-Tac-Toe!.
 */
public class TicTacToePlugin implements GamePlugin {

    private static final String GAME_NAME = "Tic-Tac-Toe";

    private static final int GRID_WIDTH = 3;
    private static final int GRID_HEIGHT = 3;

    private static final String PLAYER_WON_MSG = "%s has won the game!";
    private static final String GAME_TIED_MSG = "The game has ended in a tie.";

    private GameFramework framework;
    private boolean stalemate;

    @Override
    public String getGameName() {
        return "Tic-Tac-Toe";
    }

    @Override
    public int getGridWidth() {
        return 3;
    }

    @Override
    public int getGridHeight() {
        return 3;
    }

    @Override
    public void onRegister(GameFramework f) {
    	this.framework = f;
    }

    @Override
    public void onNewGame() {
    }

    @Override
    public boolean isMoveValid(int x, int y) {
		return framework.getSquare(x, y) == null;
    }

    @Override
    public void onNewMove() {
    }

    @Override
    public boolean isGameOver() {
    	return false;
    }

    @Override
    public String getGameOverMessage() {
        return null;
    }

    private boolean isFull() { 
		return false;
    }

    private boolean hasWon(Player currentPlayer) {
        return false;
    }

    @Override
    public boolean isMoveOver() {
        return false;
    }

    @Override
    public void onMovePlayed(int x, int y) {
    }

	@Override
	public void onGameClosed() {		
	}
}
