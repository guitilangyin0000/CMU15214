package edu.cmu.cs.cs214.rec09.plugin;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import edu.cmu.cs.cs214.rec09.framework.core.GameFramework;
import edu.cmu.cs.cs214.rec09.framework.core.GamePlugin;

/**
 * An example Memory game plug-in.
 */
public class MemoryPlugin implements GamePlugin {

	private static final String GAME_NAME = "Memory";

	// Precondition: GRID_WIDTH * GRID_HEIGHT % 2 == 0.
	private static final int GRID_WIDTH = 4;
	private static final int GRID_HEIGHT = 4;

	// Precondition: 2 * WORDS.length == GRID_HEIGHT * GRID_WIDTH.
	private static final String[] WORDS = { "Apple", "Boat", "Car", "Dog",
			"Eagle", "Fish", "Giraffe", "Helicopter", };

	private static final String SELECT_FIRST_CARD_MSG = "Select first card.";
	private static final String SELECT_SECOND_CARD_MSG = "Select second card.";
	private static final String MATCH_FOUND_MSG = "You found a match!";
	private static final String MATCH_NOT_FOUND_MSG = "Match not found.";
	private static final String PLAYER_WON_MSG = "All pairs found, %s won the game!";

	private static final int FIRST_CLICK = 0;
	private static final int SECOND_CLICK = 1;

	private GameFramework framework;
	private final List<String> cards = new ArrayList<String>();
	private final Point firstUncovered = new Point();
	private final Point secondUncovered = new Point();
	private boolean lastMatch;
	private int clickCounter;

	// The internal game grid is to keep a representation of the board we
	// don't want shown to the user.
	private final String internalGameGrid[][] = new String[GRID_HEIGHT][GRID_WIDTH];

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
		for (String word : WORDS) {
			cards.add(word);
		}
	}

	@Override
	public void onNewGame() {
		for (int y = 0; y < GRID_HEIGHT; y++) {
			for (int x = 0; x < GRID_WIDTH; x++) {
				internalGameGrid[y][x] = null;
			}
		}

		// Randomly place each card on the internal board twice.
		for (String card : cards) {
			for (int i = 0; i < 2; i++) {
				placeCard(card);
			}
		}
	}

	private void placeCard(String card) {
		while (true) {
			int y = rand.nextInt(GRID_HEIGHT);
			int x = rand.nextInt(GRID_WIDTH);
			if (internalGameGrid[y][x] == null) {
				internalGameGrid[y][x] = card;
				break;
			}
		}
	}

	@Override
	public void onNewMove() {
		clickCounter = FIRST_CLICK;
		framework.setFooterText(SELECT_FIRST_CARD_MSG);
	}

	@Override
	public boolean isMoveValid(int x, int y) {
		return framework.getSquare(x, y) == null;
	}

	@Override
	public boolean isMoveOver() {
		return clickCounter > SECOND_CLICK;
	}

	@Override
	public void onMovePlayed(int x, int y) {
		switch (clickCounter) {
		case FIRST_CLICK:
			if (!lastMatch) {
				framework.setSquare(firstUncovered.x, firstUncovered.y, null);
				framework.setSquare(secondUncovered.x, secondUncovered.y, null);
			}
			framework.setSquare(x, y, internalGameGrid[y][x]);
			firstUncovered.setLocation(x, y);
			framework.setFooterText(SELECT_SECOND_CARD_MSG);
			break;
		case SECOND_CLICK:
			framework.setSquare(x, y, internalGameGrid[y][x]);
			secondUncovered.setLocation(x, y);
			if (isMatch()) {
				framework.setFooterText(MATCH_FOUND_MSG);
			} else {
				framework.setFooterText(MATCH_NOT_FOUND_MSG);
			}
			break;
		}
		clickCounter++;
	}

	private boolean isMatch() {
		String card1 = internalGameGrid[firstUncovered.y][firstUncovered.x];
		String card2 = internalGameGrid[secondUncovered.y][secondUncovered.x];
		if (card1.equals(card2)) {
			lastMatch = true;
			return true;
		}
		lastMatch = false;
		return false;
	}

	@Override
	public boolean isGameOver() {
		// Check if all cards are showing.
		for (int y = 0; y < GRID_HEIGHT; y++) {
			for (int x = 0; x < GRID_WIDTH; x++) {
				if (framework.getSquare(x, y) == null) {
					return false;
				}
			}
		}
		return true;
	}

	@Override
	public String getGameOverMessage() {
		String name = framework.getCurrentPlayer().getName();
		return String.format(PLAYER_WON_MSG, name);
	}

	@Override
	public void onGameClosed() {

	}
}
