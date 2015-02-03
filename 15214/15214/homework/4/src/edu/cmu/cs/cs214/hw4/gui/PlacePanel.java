package edu.cmu.cs.cs214.hw4.gui;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import edu.cmu.cs.cs214.hw4.core.Game;
import edu.cmu.cs.cs214.hw4.core.Location;
import edu.cmu.cs.cs214.hw4.core.Tile;

/*
 * The panel which will be popped up when player decides to place the tiles
 */
public class PlacePanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private final JFrame frame;
	private final GamePanelListener gamePanel;
	private final Game game;
	private final int startX;
	private final int startY;
	private final int dir;
	private final JLabel placeInstruct;
	private final JButton submitButton;
	private final JButton cancelButton;
	private final JPanel workPanel;
	private final JLabel placeMsg;
	private final int boardLength;
	private ArrayList<JTextField> allTexts;
	private ArrayList<Location> allLocations;

	// initial the place panel
	public PlacePanel(GamePanelListener gamePanel, Game game, JFrame frame,
			int startX, int startY, int dir) {
		this.frame = frame;
		this.gamePanel = gamePanel;
		this.game = game;
		this.startX = startX;
		this.startY = startY;
		this.dir = dir;
		placeInstruct = new JLabel(
				"Please use your tiles in inventory to fill, the board piece is according to your start location and direction of placement");
		workPanel = new JPanel();
		submitButton = new JButton("place your tiles");
		cancelButton = new JButton("cancel");
		placeMsg = new JLabel("");
		boardLength = game.getBoard().getLength();
		allTexts = new ArrayList<JTextField>();
		allLocations = new ArrayList<Location>();
		initial();
	}

	public void initial() {
		setLayout(new GridLayout(5, 1));
		setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.DARK_GRAY));
		initialWorkPanel();
		initialSubmitButton();
		initialCancelButton();

		add(placeInstruct);
		add(workPanel);
		add(submitButton);
		add(cancelButton);
		add(placeMsg);
	}

	public void initialWorkPanel() {
		Location[][] locations = game.getBoard().getLocations();
		int length = 0;
		if (dir == 1) {
			length = boardLength - startY;
		} else {
			length = boardLength - startX;
			;
		}
		workPanel.setLayout(new FlowLayout());
		int deltaX = 0;
		int deltaY = 0;
		if (dir == 1) {
			deltaY = 1;
		} else {
			// workPanel.setLayout(new GridLayout(length, 1));
			deltaX = 1;
		}
		for (int i = 0; i < length; i++) {
			int currX = startX + i * deltaX;
			int currY = startY + i * deltaY;
			if (locations[currX][currY].isOccupied()) {
				JLabel newLabel = new JLabel();
				newLabel.setText(locations[currX][currY].getTile().getLetter()
						+ "");
				newLabel.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2,
						locations[currX][currY].getMultipler().getColor()));
				workPanel.add(newLabel);
			} else {
				JTextField newText = new JTextField(5);
				newText.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2,
						locations[currX][currY].getMultipler().getColor()));
				workPanel.add(newText);
				allTexts.add(newText);
				allLocations.add(game.getBoard().getLocation(currX, currY));
			}
		}
	}

	public void clearText() {
		for (JTextField text : allTexts) {
			text.setText("");
		}
	}

	public void initialCancelButton() {
		ActionListener cancelListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg) {
				game.initialAction();
				gamePanel.setTrue();
				gamePanel.setGameMsgLabel("Player: "
						+ game.getCurrentPlayer().getName()
						+ " has cancelled his/her placement");
				gamePanel.removeSettingPanel();
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
				ArrayList<Tile> tempTiles = new ArrayList<Tile>();
				ArrayList<Location> tempLocations = new ArrayList<Location>();
				int endIndex = 0;
				// sub by sub to parse input data
				
				for (int i = 0; i < allTexts.size(); i++) {
					if (allTexts.get(i).getText().trim().equals("")) {
						endIndex = i;
						break;
					}
				}
				// if the input is not start from the start location, return
				if (endIndex == 0) {
					placeMsg.setText("You should at least fill in the start location!");
					clearText();
					return;
				}
				// if (endIndex > 7){
				// placeMsg.setText("You have too many input!");
				// clearText();
				// return;
				// }
				// if the inputs are not in sequence, return
				for (int j = endIndex + 1; j < allTexts.size(); j++) {
					if (!allTexts.get(j).getText().trim().equals("")) {
						placeMsg.setText("Your inputs are not continues!");
						clearText();
						return;
					}
				}
				// if the inputs have invalid characters, return
				for (int k = 0; k < endIndex; k++) {
					String input = allTexts.get(k).getText().trim();
					if (input.length() > 1
							|| game.getTilePackage().getValueByLetter(
									input.charAt(0)) == -1) {
						placeMsg.setText("Your inputs have invalid characters!");
						clearText();
						return;
					}
					tempTiles
							.add(new Tile(input.charAt(0), game
									.getTilePackage().getValueByLetter(
											input.charAt(0))));
					tempLocations.add(allLocations.get(k));
				}
				if (!game.getCurrentPlayer().isIn(tempTiles)) {
					placeMsg.setText("Your input tiles are not all in your inventory!");
					clearText();
					return;
				}
				placeMsg.setText("You have pass the filtering!");
				game.setDirectionToAction(dir);
				game.setStartLocationToAction(startX, startY);
				System.out.println(startX + " " + startY);
				for (int m = 0; m < endIndex; m++) {
					game.addTileToAction(tempTiles.get(m), tempLocations.get(m)
							.getX(), tempLocations.get(m).getY());
				}
				// check the result of validation
				int signal = game.checkValidAction();
				if (signal == 0) {
					placeMsg.setText("Your first move should place on the star location!");
					game.initialAction();
					clearText();
					return;
				} else if (signal == 2) {
					placeMsg.setText("Your placement is not adjacent to an existing word!");
					game.initialAction();
					clearText();
					return;
				} else if (signal == 3) {
					placeMsg.setText("Your word is not in the dictionary!");
					game.initialAction();
					clearText();
					return;
				} else if (signal == 4) {
					placeMsg.setText("Your word has triggered invalid word with existing words on the board!");
					game.initialAction();
					clearText();
					return;
				}
				// finally make the action
				game.makeAction();
				game.initialAction();
				gamePanel.setTrue();
				gamePanel.removeSettingPanel();
				gamePanel.updateAll();
				game.setFirstMove();
				gamePanel.setGameMsgLabel("Player: "
						+ game.getCurrentPlayer().getName()
						+ " can have your move now!");
				frame.dispose();
			}
		};
		submitButton.addActionListener(submitListener);
	}

}
