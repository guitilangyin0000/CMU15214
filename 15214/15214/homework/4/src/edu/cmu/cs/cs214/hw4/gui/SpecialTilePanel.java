package edu.cmu.cs.cs214.hw4.gui;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;

import edu.cmu.cs.cs214.hw4.core.Game;
import edu.cmu.cs.cs214.hw4.core.Location;
import edu.cmu.cs.cs214.hw4.core.Tile;
import edu.cmu.cs.cs214.hw4.core.specialTile.SpecialTile;


/*
 * The panel after the player buys the special tiles, the player
 * need to define the coordinate of the special tile he/she wants
 * to put
 */

public class SpecialTilePanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private final JFrame frame;
	private final GamePanelListener gamePanel;
	private final Game game;
	private final SpecialTile specialTile;
	private final static String BUTTON_TITLE_1 = "submit";
	private final static String BUTTON_TITLE_2 = "cancel";
	private JLabel instructLabel;
	private JLabel xLabel;
	private JLabel yLabel;
	private JTextField xText;
	private JTextField yText;
	private JButton submitButton;
	private JButton cancelButton;
	private JLabel msgLabel;
	public SpecialTilePanel(SpecialTile specialTile,
			GamePanelListener gamePanel, Game game, JFrame frame) {
		this.frame = frame;
		this.gamePanel = gamePanel;
		this.game = game;
		this.specialTile = specialTile;
		instructLabel = new JLabel(
				"Please type in the coordinate of your special tile:");
		xLabel = new JLabel("x coordinate: ");
		yLabel = new JLabel("y coordinate: ");
		xText = new JTextField(5);
		yText = new JTextField(5);
		submitButton = new JButton(BUTTON_TITLE_1);
		cancelButton = new JButton(BUTTON_TITLE_2);
		msgLabel = new JLabel("");
		initial();
	}

	public void initial() {
		setLayout(new GridLayout(8, 1, 5, 5));
		setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.DARK_GRAY));
		initialSubmitButton();
		initialCancelButton();
		add(instructLabel);
		add(xLabel);
		add(xText);
		add(yLabel);
		add(yText);
		add(submitButton);
		add(cancelButton);
		add(msgLabel);
	}

	public void initialCancelButton() {
		ActionListener cancelListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg) {
				// The player decides to cancel the purchase
				game.getCurrentPlayer().addScore(specialTile.getPrice());
				gamePanel.setTrue();
				gamePanel
						.setGameMsgLabel("Player: "
								+ game.getCurrentPlayer().getName()
								+ " has cancelled his/her special tile placement, score refunded.");
				gamePanel.updateAll();
				frame.dispose();
			}
		};
		cancelButton.addActionListener(cancelListener);
	}

	public void initialSubmitButton() {
		ActionListener submitListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg) {
				// parse the input data
				String xInput = xText.getText().trim();
				String yInput = yText.getText().trim();
				// if there is empty input, return
				if (xInput.equals("") || yInput.equals("")) {
					msgLabel.setText("Your input should not be empty!");
					xText.setText("");
					yText.setText("");
					return;
				}
				int xResult = -1;
				int yResult = -1;
				try {
					xResult = Integer.parseInt(xInput);
					yResult = Integer.parseInt(yInput);

				} catch (NumberFormatException e) {
					// if there is invalid character, return
					msgLabel.setText("There is invalid character in your input!");
					xText.setText("");
					yText.setText("");
					return;
				}

				if (!game.getBoard().isOnBoard(xResult, yResult)) {
					msgLabel.setText("Your input coordinate is out of board!");
					xText.setText("");
					yText.setText("");
					return;
				}

				if (game.getBoard().getLocation(xResult, yResult).isOccupied()) {
					msgLabel.setText("Your special tile should not set at the tile occupied location!");
					xText.setText("");
					yText.setText("");
					return;
				}
				String oldOwner = "";
				if (game.getBoard().getLocation(xResult, yResult)
						.getSpecialTile() != null) {
					oldOwner = game.getBoard().getLocation(xResult, yResult)
							.getSpecialTile().getOwner().getName();
				}
				if (oldOwner != "" && game.getBoard().getLocation(xResult, yResult)
						.getSpecialTile().getName() == "StealMove"
						&& specialTile.getOwner().equals(oldOwner)) {
					game.getBoard().getLocation(xResult, yResult)
							.removeSpecialTile();
				}

				game.getBoard().setSpecialTileToLocation(specialTile, xResult,
						yResult);
				gamePanel.setTrue();
				gamePanel.setGameMsgLabel("Player: "
						+ game.getCurrentPlayer().getName()
						+ " 's special tile has been successfully set!");
				gamePanel.updateAll();
				frame.dispose();
			}
		};
		submitButton.addActionListener(submitListener);
	}
}
