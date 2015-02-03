package edu.cmu.cs.cs214.hw4.gui;

import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Main {
	
	private static final String GAME_NAME = "Fancy Scribble 1.0";
	
	private static void createAndShowGame() throws IOException {
		JFrame frame = new JFrame(GAME_NAME);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		EntryPanel gamePanel = new EntryPanel(frame);
		gamePanel.setOpaque(true);
		frame.add(gamePanel);

		frame.pack();
		frame.setVisible(true);
		frame.setResizable(true);
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					createAndShowGame();
				} catch (IOException e) {
				}
			}
		});
	}

}
