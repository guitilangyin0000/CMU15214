package edu.cmu.cs.cs214.rec08.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;

import edu.cmu.cs.cs214.rec08.core.Player;
import edu.cmu.cs.cs214.rec08.core.TicTacToe;
import edu.cmu.cs.cs214.rec08.core.TicTacToeImpl;

public class TicTacToeTest {

    private TicTacToe game;
    private Player playerX;
    private Player playerY;

    @Before
    public void setUp() {
        game = new TicTacToeImpl();
        playerX = new Player("Cross", "x");
        playerY = new Player("Knot", "o");
        game.addPlayer(playerX);
        game.addPlayer(playerY);
    }

    @Test
    public void testSingleMove() {
        game.startNewGame();
        assertNull(game.getSquare(0, 0));
        game.playMove(0, 0);
        assertEquals(playerX, game.getSquare(0, 0));
    }

	// TODO: write more tests
}
