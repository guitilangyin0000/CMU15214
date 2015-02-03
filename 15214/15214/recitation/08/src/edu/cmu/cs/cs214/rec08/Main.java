package edu.cmu.cs.cs214.rec08;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import edu.cmu.cs.cs214.rec08.core.Player;
import edu.cmu.cs.cs214.rec08.core.TicTacToe;
import edu.cmu.cs.cs214.rec08.core.TicTacToeImpl;
import edu.cmu.cs.cs214.rec08.gui.TicTacToePanel;

public class Main {

	private static final String RECITATION_NAME = "rec08";
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                createAndShowGameBoard();
            }
        });
    }

    private static void createAndShowGameBoard(){
		JFrame frame = new JFrame(RECITATION_NAME);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		TicTacToe game = new TicTacToeImpl();
		game.addPlayer(new Player("Cross", "x"));
		game.addPlayer(new Player("Knot", "o"));
		
		// create and set up the content panel
		TicTacToePanel gamePanel = new TicTacToePanel(game);
		gamePanel.setOpaque(true);
		frame.setContentPanel(gamePanel);
		
		game.startNewGame();
		
		// display the window
		frame.pack();
		frame.setVisible(true);
		
	}

}
