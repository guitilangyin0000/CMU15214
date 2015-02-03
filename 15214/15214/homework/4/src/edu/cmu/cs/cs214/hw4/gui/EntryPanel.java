package edu.cmu.cs.cs214.hw4.gui;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;

import edu.cmu.cs.cs214.hw4.core.Game;
import edu.cmu.cs.cs214.hw4.core.Player;

public class EntryPanel extends JPanel {
	private final static long serialVersionUID = 1L;
	private final ArrayList<String> players;
	private final JFrame gameFrame;
	private final static int MAX_PLAYER_NUM = 4;
	private final static String PLAYER_BUTTON_NAME = "Get me in!";
	private final static String LAUNCH_BUTTON_NAME = "Launch";
	private final static String PLAYER_INITIAL_MSG = "Current players:";
	private final static String PLAYER_INITIAL_TEXT = "Enter your name here: ";
	private final static int TEXT_LENGTH = 25;
	private final JLabel MsgLabel = new JLabel(
			"Welcome to fancy scribble 1.0! Please enter your username and launch the game.");
	
	public EntryPanel(JFrame frame){
		// set the parent frame with the input frame
		gameFrame = frame;
		// initial the player list
		players = new ArrayList<String>();
		// initial the addPlayerSystem
		JPanel addPlayerPanel = new JPanel();
		JButton addPlayerButton = new JButton(PLAYER_BUTTON_NAME);
		final JTextField addPlayerText = new JTextField(TEXT_LENGTH);
		addPlayerText.setText(PLAYER_INITIAL_TEXT);
		final JLabel addPlayerMsg = new JLabel(PLAYER_INITIAL_MSG);
		// define the listener for addPlayerButton
		ActionListener addPlayerListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg) {
				String inputName = addPlayerText.getText().trim();
				if (inputName.equals("")){
					MsgLabel.setText("The player's name cannot be empty!");
					addPlayerText.setText(PLAYER_INITIAL_TEXT);
					return;
					}
				if (players.contains(inputName)){
					MsgLabel.setText("The player's name has already exist!");
					addPlayerText.setText(PLAYER_INITIAL_TEXT);
					return;
				}
				if (players.size() == MAX_PLAYER_NUM){
					MsgLabel.setText("The number of players has reached the maximum!");
					addPlayerText.setText(PLAYER_INITIAL_TEXT);
					return;
				}
				if (inputName.equals(PLAYER_INITIAL_TEXT.trim())){
					MsgLabel.setText("Really? Change another one, ok?");
					addPlayerText.setText(PLAYER_INITIAL_TEXT);
					return;
				}
				players.add(inputName);
				MsgLabel.setText("Player " + inputName + " has been successfully added!");
				addPlayerMsg.setText(addPlayerMsg.getText() + " " + inputName);
				addPlayerText.setText(PLAYER_INITIAL_TEXT);
				addPlayerText.requestFocus();
			}
		};
		// add listener to the addPlayerButton
		addPlayerButton.addActionListener(addPlayerListener);
		// add contents to the addPlayerPanel
		addPlayerPanel.setLayout(new BorderLayout());
		addPlayerPanel.add(addPlayerButton, BorderLayout.CENTER);
		addPlayerPanel.add(addPlayerMsg, BorderLayout.SOUTH);
		addPlayerPanel.add(addPlayerText, BorderLayout.NORTH);
		
		JButton launchButton = new JButton(LAUNCH_BUTTON_NAME);
		ActionListener launchListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg) {
				if (players.isEmpty()){
					MsgLabel.setText("There is no player added now...");
					return;
				}
				runGame();
			}
		};
		launchButton.addActionListener(launchListener);
		// add contents to the EntryPanel
		setLayout(new BorderLayout());
		add(MsgLabel, BorderLayout.NORTH);
		add(addPlayerPanel, BorderLayout.CENTER);
		add(launchButton, BorderLayout.SOUTH);
		// settings
		setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		setVisible(true);	
	}
	
	// start a new game by creating another frame and gameBoard
	public void runGame(){
		final Game game = new Game();
		final String gameName = "Fancy Scribble 1.0";
		for (String playerName: players){
			Player newPlayer = new Player(playerName);
			game.addPlayer(newPlayer);
		}
		game.initialPlayerInventory();
		game.startGame();

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				JFrame frame = new JFrame(gameName);
				final GamePanel gamePanel = new GamePanel(game);
				gamePanel.setOpaque(true);
				frame.add(gamePanel);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				// because we need the mouse coordinate data
				frame.setResizable(true);
				frame.pack();
				frame.setVisible(true);
			}
		});
		gameFrame.dispose();
	}

}
