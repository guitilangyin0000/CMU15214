package edu.cmu.cs.cs214.hw4.gui;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;

import edu.cmu.cs.cs214.hw4.core.*;

/*
 * The pop up exchange panel for users, users can input the tiles he/she wants to exchange
 */
public class ExchangePanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private final JFrame frame;
	private final GamePanelListener gamePanel;
	private final Game game;
	private final static int INPUT_LENGTH = 20;
	private final static String BUTTON_TITLE_1 = "exchange";
	private final static String BUTTON_TITLE_2 = "cancel";
	private final static String MSG_INITIAL = "";
	private JLabel exchangeInstructLabel;
	private JTextField exchangeInput;
	private JButton exchangeSubmitButton;
	private JButton exchangeCancelButton;
	private JLabel exchangeMsgLabel;

	public ExchangePanel(GamePanelListener gamePanel, Game game, JFrame frame) {
		this.gamePanel = gamePanel;
		this.game = game;
		this.frame = frame;
		exchangeInstructLabel = new JLabel(
				"Please type in the tiles you want to exchange:");
		exchangeInput = new JTextField(INPUT_LENGTH);
		exchangeSubmitButton = new JButton(BUTTON_TITLE_1);
		exchangeCancelButton = new JButton(BUTTON_TITLE_2);
		exchangeMsgLabel = new JLabel(MSG_INITIAL);
		initial();
	}

	// initial the exchange panel
	public void initial() {
		setLayout(new GridLayout(5, 1, 10, 10));
		setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.DARK_GRAY));
		initialSubmitButton();
		initialCancelButton();
		// add the contents to the main panel
		add(exchangeInstructLabel);
		add(exchangeInput);
		add(exchangeSubmitButton);
		add(exchangeCancelButton);
		add(exchangeMsgLabel);
	}

	// initial the listener to the button
	public void initialCancelButton() {
		ActionListener cancelListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg) {
				gamePanel.setTrue();
				gamePanel.setGameMsgLabel("Player: "
						+ game.getCurrentPlayer().getName()
						+ " has cancelled his/her tile exchange.");
				gamePanel.updateAll();
				frame.dispose();
			}
		};
		exchangeCancelButton.addActionListener(cancelListener);
	}

	// initial the submit button
	public void initialSubmitButton() {
		ActionListener submitListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg) {
				String input = exchangeInput.getText().trim();
				// sub by sub, to parse the input the String
				// if the input is empty, return
				if (input.equals("")) {
					exchangeMsgLabel
							.setText("Your exchange tiles cannot be empty!");
					return;
				}
				// if the input has more than 7 tile exchange request
				if (input.length() > 7) {
					exchangeMsgLabel
							.setText("Your exchange tiles have exceeded the maximum number 7!");
					return;
				}
				ArrayList<Tile> exchangeTiles = new ArrayList<Tile>();
				for (int i = 0; i < input.length(); i++) {
					char letter = input.charAt(i);
					int value = game.getTilePackage().getValueByLetter(letter);
					// if it's an invalid character, return
					if (value == -1) {
						exchangeMsgLabel
								.setText("There are invalid letter in your input!");
						return;
					}
					System.out.println("value: " + value);
					Tile newTile = new Tile(letter, value);
					exchangeTiles.add(newTile);
				}
				if (!game.getCurrentPlayer().isIn(exchangeTiles)) {
					// if the exchange tiles are not in the user's inventory
					exchangeMsgLabel
							.setText("There are tiles that are not in your inventory!");
					return;
				}
				game.exchangeTiles(exchangeTiles, game.getCurrentPlayer());
				Player prevPlayer = game.getCurrentPlayer();
				game.updateOrder();
				gamePanel.setTrue();
				gamePanel
						.setGameMsgLabel("Player: "
								+ prevPlayer.getName()
								+ " 's tiles have been successfully exchanged! Now it's "
								+ game.getCurrentPlayer().getName()
								+ "'s turn!");
				gamePanel.updateAll();
				// dispose the window, back to the game panel
				frame.dispose();
			}
		};
		exchangeSubmitButton.addActionListener(submitListener);
	}

}
