package edu.cmu.cs.cs214.rec09.plugin;

import java.util.Random;

import edu.cmu.cs.cs214.rec09.framework.core.GameFramework;
import edu.cmu.cs.cs214.rec09.framework.core.GamePlugin;

/**
 * An example Rock Paper Scissors game plug-in.
 */
public class RpsPlugin implements GamePlugin {

    private static final String GAME_NAME = "Rocks Paper Scissors";

    private static final int GRID_WIDTH = 3;
    private static final int GRID_HEIGHT = 1;

    private static final String PLAYER_WON_MSG = "Player %s won!";
    private static final String COMPUTER_WON_MSG = "The computer won!";
    private static final String GAME_TIED_MSG = "The game ended in a tie.";

    private static final int ROCK_INDEX = 0;
    private static final int PAPER_INDEX = 1;
    private static final int SCISSORS_INDEX = 2;

    private GameFramework framework;
    private int computerSelection;
    private int playerSelection;

    private final Random rand = new Random();

    @Override
    public String getGameName() {
        return GAME_NAME;
    }

    @Override
    public int getGridWidth() {
        return GRID_WIDTH;
    }

    @Override
    public int getGridHeight() {
        return GRID_HEIGHT;
    }

    @Override
    public void onRegister(GameFramework f) {
        framework = f;
    }

    @Override
    public void onNewGame() {
        framework.setSquare(ROCK_INDEX, 0, "Rock");
        framework.setSquare(PAPER_INDEX, 0, "Paper");
        framework.setSquare(SCISSORS_INDEX, 0, "Scissors");
        computerSelection = rand.nextInt(3);
    }

    @Override
    public void onNewMove() {
        // Nothing to do here.
    }

    @Override
    public boolean isMoveValid(int x, int y) {
        // Impossible to make an invalid move.
        return true;
    }

    @Override
    public boolean isMoveOver() {
        // only one button click per player
        return true;
    }

    @Override
    public void onMovePlayed(int x, int y) {
        playerSelection = y;
    }

    @Override
    public boolean isGameOver() {
        // Only one action per game.
        return true;
    }

    @Override
    public String getGameOverMessage() {
        if (computerSelection == playerSelection) {
            return GAME_TIED_MSG;
        }
        if (hasPlayerWon()) {
            String name = framework.getCurrentPlayer().getName();
            return String.format(PLAYER_WON_MSG, name);
        }
        return COMPUTER_WON_MSG;
    }

    private boolean hasPlayerWon() {
        if ( playerSelection == PAPER_INDEX && computerSelection == ROCK_INDEX) {
            // Paper beats rock.
            return true;
        }
        if (playerSelection == ROCK_INDEX && computerSelection == SCISSORS_INDEX) {
            // Rock beats scissors.
            return true;
        }
        if (playerSelection == SCISSORS_INDEX && computerSelection == PAPER_INDEX) {
            // Scissors beats paper.
            return true;
        }
        // Otherwise the computer won or the game was a tie.
        return false;
    }

	@Override
	public void onGameClosed() {
		
	}

}
