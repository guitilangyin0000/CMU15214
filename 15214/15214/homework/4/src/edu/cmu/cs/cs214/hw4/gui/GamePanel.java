package edu.cmu.cs.cs214.hw4.gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;

import edu.cmu.cs.cs214.hw4.core.*;
import edu.cmu.cs.cs214.hw4.core.specialTile.SpecialTile;

/*
 *  the main game board, the main GUI that connects the players and the game
 */
public class GamePanel extends JPanel implements GamePanelListener {

	private static final long serialVersionUID = 1L;
	private final Game game;
	private final int boardLength;
	private final int playerNum;
	private final int specialTileNum;
	private final static Color GRAY = new Color(220, 220, 220);
	private final static int TRUNK_LENGTH = 45;
	private JButton[] specialTiles;
	private JLabel[][] tileLabels;
	private JLabel[][] multiplerLabels;
	private JLabel[] playerNames;
	private JLabel[] playerScores;
	private JLabel[] playerInventories;
	private JLabel gameMsgLabel;
	private JLabel currentPlayerLabel;
	private JPanel[][] grids;
	private JPanel boardPanel;
	private JPanel playerInfoPanel;
	private JPanel playerCommandPanel;
	private JPanel gameMsgPanel;
	private Boolean gamePanelFlag;
	private JPanel eastPanel;
	private JPanel settingPanel;

	public GamePanel(Game game) {
		this.game = game;
		boardLength = game.getBoard().getLength();
		playerNum = game.getPlayerNum();
		specialTileNum = game.getSpecialTileStoreNum();
		grids = new JPanel[boardLength + 1][boardLength + 1];
		tileLabels = new JLabel[boardLength][boardLength];
		multiplerLabels = new JLabel[boardLength][boardLength];
		playerNames = new JLabel[playerNum];
		playerScores = new JLabel[playerNum];
		playerInventories = new JLabel[playerNum];
		specialTiles = new JButton[specialTileNum];
		boardPanel = new JPanel();
		playerInfoPanel = new JPanel();
		playerCommandPanel = new JPanel();
		gameMsgPanel = new JPanel();
		gameMsgLabel = new JLabel("Welcome to fancy scribble 1.0!");
		currentPlayerLabel = new JLabel();
		gamePanelFlag = true;
		eastPanel = new JPanel();
		settingPanel = new JPanel();
		
		initial();
	}

	public Game getGame() {
		return game;
	}

	public void initial() {
		setLayout(new BorderLayout());
		setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		setBackground(GRAY);
        // initial panels
		initialBoardPanel();
		initialPlayerInfoPanel();
		initialPlayerCommandPanel();
		initialGameMsgPanel();
        // set the east part of the main panel
		eastPanel.setLayout(new GridLayout(4, 1));
		eastPanel.add(playerInfoPanel);
		eastPanel.add(playerCommandPanel);
		eastPanel.add(gameMsgPanel);
        // add the separated panel to the main game panel
		add(boardPanel, BorderLayout.CENTER);
		add(eastPanel, BorderLayout.EAST);

	}

	public void initialBoardPanel() {
		boardPanel.setLayout(new GridLayout(boardLength + 1, boardLength + 1,
				5, 5));
		Location[][] locations = game.getBoard().getLocations();
		// traverse the board, and set the initial separated small panels on the board
		for (int i = 0; i < boardLength + 1; i++) {
			for (int j = 0; j < boardLength + 1; j++) {
				grids[i][j] = new JPanel();
				grids[i][j].setLayout(new BorderLayout());
				grids[i][j].setPreferredSize(new Dimension(70, 35));
				grids[i][j].setOpaque(true);
                // instructions of the coordinate
				if (i == 0 && j == 0) {
					JLabel coordinate = new JLabel("X|Y");
					coordinate.setFont(new Font("Serif", Font.PLAIN, 18));
					grids[i][j].add(coordinate, BorderLayout.WEST);
					// set the X and Y coordinates
				} else if ((i == 0 && j != 0) || (j == 0 && i != 0)) {
					JLabel coordinate = new JLabel((Math.max(i, j) - 1) + "");
					coordinate.setFont(new Font("Serif", Font.PLAIN, 18));
					grids[i][j].add(coordinate, BorderLayout.WEST);
				} else {
					// set the tile labels and special tile labels
					Color color = locations[i - 1][j - 1].getMultipler()
							.getColor();
					multiplerLabels[i - 1][j - 1] = new JLabel("");
					multiplerLabels[i - 1][j - 1].setFont(new Font("Serif",
							Font.PLAIN, 12));
					multiplerLabels[i - 1][j - 1].setForeground(Color.red);
					tileLabels[i - 1][j - 1] = new JLabel("");
					tileLabels[i - 1][j - 1].setFont(new Font("Serif",
							Font.BOLD, 14));
					grids[i][j].setBorder(BorderFactory.createMatteBorder(5, 5,
							5, 5, color));
					grids[i][j].add(tileLabels[i - 1][j - 1],
							BorderLayout.CENTER);
					grids[i][j].add(multiplerLabels[i - 1][j - 1],
							BorderLayout.EAST);
				}
				// add the component labels to the small panel
				boardPanel.add(grids[i][j]);
			}
		}
		repaint();
		return;
	}
	

	// update the board panel
	public void updateBoardPanel() {
		String currentName = game.getCurrentPlayer().getName();
		Location[][] locations = game.getBoard().getLocations();
		for (int i = 1; i < boardLength + 1; i++) {
			for (int j = 1; j < boardLength + 1; j++) {
				if (locations[i - 1][j - 1].isOccupied()) {
					char letter = locations[i - 1][j - 1].getTile().getLetter();
					int value = locations[i - 1][j - 1].getTile().getValue();
					tileLabels[i - 1][j - 1].setText("" + letter + value);
				} else {
					tileLabels[i - 1][j - 1].setText("");
				}
                // show the special tile representation
				if (locations[i - 1][j - 1].getSpecialTile() != null
						&& currentName.equals(locations[i - 1][j - 1]
								.getSpecialTile().getOwner().getName())) {
					char sign = locations[i - 1][j - 1].getSpecialTile()
							.getName().charAt(0);
					multiplerLabels[i - 1][j - 1].setText(" " + sign);
				} else {
					multiplerLabels[i - 1][j - 1].setText(" ");
				}

				Color color = locations[i - 1][j - 1].getMultipler().getColor();
				grids[i][j].setBorder(BorderFactory.createMatteBorder(5, 5, 5,
						5, color));
			}
		}
		repaint();
		return;
	}

	public void initialPlayerInfoPanel() {
		playerInfoPanel.setLayout(new GridLayout(playerNum, 1));
		playerInfoPanel.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2,
				Color.BLUE));
		JPanel[] playerInfos = new JPanel[playerNum];
		ArrayList<Player> players = game.getPlayers();
		for (int i = 0; i < playerNum; i++) {
			Boolean isCurrent = players.get(i).getName()
					.equals(game.getCurrentPlayer().getName());
			String inventory = players.get(i).showInventory();

			playerInfos[i] = new JPanel(new GridLayout(3, 1));
			playerInfos[i].setPreferredSize(new Dimension(300, 100));
			playerInfos[i].setBorder(BorderFactory.createMatteBorder(1, 1, 1,
					1, Color.BLACK));
			playerNames[i] = new JLabel("Player: " + players.get(i).getName());
			playerScores[i] = new JLabel("Score: " + players.get(i).getScore());
			playerInventories[i] = new JLabel("Inventory: " + inventory);
			// if it's not the current player, hide the inventory information
			if (!isCurrent) {
				playerInventories[i] = new JLabel("Inventory: "
						+ "***********************");
			}
			playerInfos[i].add(playerNames[i]);
			playerInfos[i].add(playerScores[i]);
			playerInfos[i].add(playerInventories[i]);
			// add playerInfos[i] to the total pool
			playerInfoPanel.add(playerInfos[i]);
		}
		repaint();
		return;
	}

	// update the players' information including score and inventory
	public void updatePlayerInfoPanel() {
		playerInfoPanel.removeAll();
		playerInfoPanel.setLayout(new GridLayout(playerNum, 1));
		playerInfoPanel.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2,
				Color.BLUE));
		JPanel[] playerInfos = new JPanel[playerNum];
		ArrayList<Player> players = game.getPlayers();
		for (int i = 0; i < playerNum; i++) {
			Boolean isCurrent = players.get(i).getName()
					.equals(game.getCurrentPlayer().getName());
			String inventory = players.get(i).showInventory();
			// we need more lines to show the inventory
			int moreNum = 0;
			if (inventory.length() > TRUNK_LENGTH) {
				moreNum = (inventory.length() - TRUNK_LENGTH) / TRUNK_LENGTH
						+ 1;
			}
			ArrayList<JLabel> moreLabels = new ArrayList<JLabel>();
			if (moreNum > 0) {
				for (int j = 0; j < moreNum; j++) {
					if (j == moreNum - 1) {
						// add another set of inventory content
						moreLabels.add(new JLabel("           "
								+ inventory.substring((j + 1) * TRUNK_LENGTH)));
						continue;
					}
					moreLabels.add(new JLabel("           "
							+ inventory.substring((j + 1) * TRUNK_LENGTH,
									(j + 2) * TRUNK_LENGTH)));
				}
			}

			playerInfos[i] = new JPanel(new GridLayout(3 + moreNum, 1));
			playerInfos[i].setPreferredSize(new Dimension(300, 100));
			playerInfos[i].setBorder(BorderFactory.createMatteBorder(1, 1, 1,
					1, Color.BLACK));
			playerNames[i] = new JLabel("Player: " + players.get(i).getName());
			playerScores[i] = new JLabel("Score: " + players.get(i).getScore());
			if (moreNum == 0) {
				playerInventories[i] = new JLabel("Inventory: " + inventory);
			} else {
				playerInventories[i] = new JLabel("Inventory: "
						+ inventory.substring(0, TRUNK_LENGTH));
			}
			// hide the inventory information if it's not the current player
			if (!isCurrent) {
				playerInventories[i] = new JLabel("Inventory: "
						+ "*************************");
			}
			playerInfos[i].add(playerNames[i]);
			playerInfos[i].add(playerScores[i]);
			playerInfos[i].add(playerInventories[i]);
			for (int k = 0; k < moreNum; k++) {
				if (!isCurrent) {
					playerInfos[i].add(new JLabel("************************"));
					continue;
				}
				playerInfos[i].add(moreLabels.get(k));

			}
			// add playerInfos[i] to the total pool
			playerInfoPanel.add(playerInfos[i]);
		}
		repaint();
		return;
	}

	public void initialPlayerCommandPanel() {
		playerCommandPanel.setLayout(new GridLayout(6, 1));
		playerCommandPanel.setBorder(BorderFactory.createMatteBorder(2, 2, 2,
				2, Color.DARK_GRAY));
		currentPlayerLabel.setText("Current player: "
				+ game.getCurrentPlayer().getName() + " with score: "
				+ game.getCurrentPlayer().getScore());
		playerCommandPanel.add(currentPlayerLabel);
		JPanel commandPanel = new JPanel();
		commandPanel.setLayout(new FlowLayout());
		// commandPanel.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1,
		// Color.BLACK));
		JLabel commandInstructLabel = new JLabel("Please make your action:");

		JButton placeButton = new JButton("Place");
		// set the listener for place button
		ActionListener placeListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg) {
				if (!gamePanelFlag) {
					return;
				}
				gameMsgLabel.setText("You have pressed the Place button!");
				triggerPlaceSettingPanel();
				// exchangePopupFlag = true;
				gamePanelFlag = false;
				return;
			}
		};
		placeButton.addActionListener(placeListener);

		JButton exchangeButton = new JButton("Exchange");
		// set the listener for exchange button
		ActionListener exchangeListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg) {
				if (!gamePanelFlag) {
					return;
				}
				gameMsgLabel.setText("You have pressed the Exchange button!");
				popupExchangeWindow();
				// exchangePopupFlag = true;
				gamePanelFlag = false;
				return;
			}
		};
		exchangeButton.addActionListener(exchangeListener);

		JButton passButton = new JButton("Pass");
		// set the listener for pass button
		ActionListener passListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg) {
				if (!gamePanelFlag) {
					return;
				}
				game.initialAction();
				Player prevPlayer = game.getCurrentPlayer();
				game.skipOrder();
				gameMsgLabel.setText(prevPlayer.getName()
						+ " have pressed the Pass button! Now it's "
						+ game.getCurrentPlayer().getName() + "'s turn.");
				updateAll();
				return;
			}
		};
		passButton.addActionListener(passListener);

		JButton quitButton = new JButton("Quit");
		// set the listener for pass button
		ActionListener quitListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg) {
				if (!gamePanelFlag) {
					return;
				}
				ArrayList<Player> winners = game.getWinner();
				String winnerString = "The winner(s) is/are:";
				for (Player player : winners) {
					winnerString += " " + player.getName();
				}
				gameMsgLabel.setText(winnerString);
				gamePanelFlag = false;
				return;
			}
		};
		quitButton.addActionListener(quitListener);

		// add buttons to commandPanel
		commandPanel.add(placeButton);
		commandPanel.add(exchangeButton);
		commandPanel.add(passButton);
		commandPanel.add(quitButton);

		JPanel specialTileStorePanel = new JPanel();
		specialTileStorePanel.setLayout(new FlowLayout());

		JLabel specialTileInstructLabel = new JLabel("Want special tile first?");

		specialTiles[0] = new JButton("Boom");
		ActionListener boomListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg) {
				if (!gamePanelFlag) {
					return;
				}
				// gameMsgLabel.setText("You have pressed the Boom button!");
				SpecialTile specialTile = game.buySpecialTile(
						game.getCurrentPlayer(), "Boom");
				if (specialTile == null) {
					gameMsgLabel
							.setText("You don't have enough money to buy special tile Boom!");
					return;
				}
				gameMsgLabel.setText(game.getCurrentPlayer().getName()
						+ "'s score - " + specialTile.getPrice());
				popupSpecialTileWindow(specialTile);
				return;
			}
		};
		specialTiles[0].addActionListener(boomListener);

		specialTiles[1] = new JButton("NegativePoints");
		ActionListener negativePointsListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg) {
				if (!gamePanelFlag) {
					return;
				}
				// gameMsgLabel.setText("You have pressed the NegativePoints button!");
				SpecialTile specialTile = game.buySpecialTile(
						game.getCurrentPlayer(), "NegativePoints");
				if (specialTile == null) {
					gameMsgLabel
							.setText("You don't have enough money to buy special tile NegativePoints!");
					return;
				}
				gameMsgLabel.setText(game.getCurrentPlayer().getName()
						+ "'s score - " + specialTile.getPrice());
				popupSpecialTileWindow(specialTile);
				return;
			}
		};
		specialTiles[1].addActionListener(negativePointsListener);

		specialTiles[2] = new JButton("OneMoreOrder");
		ActionListener oneMoreOrderListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg) {
				if (!gamePanelFlag) {
					return;
				}
				// gameMsgLabel.setText("You have pressed the OneMoreOrder button!");
				SpecialTile specialTile = game.buySpecialTile(
						game.getCurrentPlayer(), "OneMoreOrder");
				if (specialTile == null) {
					gameMsgLabel
							.setText("You don't have enough money to buy special tile OneMoreOrder!");
					return;
				}
				gameMsgLabel.setText(game.getCurrentPlayer().getName()
						+ "'s score - " + specialTile.getPrice());
				popupSpecialTileWindow(specialTile);
				return;
			}
		};
		specialTiles[2].addActionListener(oneMoreOrderListener);

		specialTiles[3] = new JButton("ReversePlayerOrder");
		ActionListener reversePlayerOrderListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg) {
				if (!gamePanelFlag) {
					return;
				}
				// gameMsgLabel.setText("You have pressed the ReversePlayerOrder button!");
				SpecialTile specialTile = game.buySpecialTile(
						game.getCurrentPlayer(), "ReversePlayerOrder");
				if (specialTile == null) {
					gameMsgLabel
							.setText("You don't have enough money to buy special tile ReversePlayerOrder!");
					return;
				}
				gameMsgLabel.setText(game.getCurrentPlayer().getName()
						+ "'s score - " + specialTile.getPrice());
				popupSpecialTileWindow(specialTile);
				return;
			}
		};
		specialTiles[3].addActionListener(reversePlayerOrderListener);

		specialTiles[4] = new JButton("StealMove");
		ActionListener stealMoveListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg) {
				if (!gamePanelFlag) {
					return;
				}
				// gameMsgLabel.setText("You have pressed the ReversePlayerOrder button!");
				SpecialTile specialTile = game.buySpecialTile(
						game.getCurrentPlayer(), "StealMove");
				if (specialTile == null) {
					gameMsgLabel
							.setText("You don't have enough money to buy special tile StealMove!");
					return;
				}
				gameMsgLabel.setText(game.getCurrentPlayer().getName()
						+ "'s score - " + specialTile.getPrice());
				popupSpecialTileWindow(specialTile);
				return;
			}
		};
		specialTiles[4].addActionListener(stealMoveListener);

		for (int j = 0; j < specialTileNum - 2; j++) {
			specialTileStorePanel.add(specialTiles[j]);
			specialTileStorePanel.add(new JLabel("[$ 30]"));
		}

		JPanel specialTileStorePanel_1 = new JPanel();
		specialTileStorePanel_1.setLayout(new FlowLayout());
		specialTileStorePanel_1.add(specialTiles[3]);
		specialTileStorePanel_1.add(new JLabel("[$ 40]"));
		specialTileStorePanel_1.add(specialTiles[4]);
		specialTileStorePanel_1.add(new JLabel("[$ 50]"));

		// add specialTileInstructLabel to playerCommandPanel
		playerCommandPanel.add(specialTileInstructLabel);
		// add specialTilePanel to playerCommandPanel
		playerCommandPanel.add(specialTileStorePanel);
		playerCommandPanel.add(specialTileStorePanel_1);
		// add instruction to playerCommandPanel
		playerCommandPanel.add(commandInstructLabel);
		// add commandPanel to playerCommandPanel
		playerCommandPanel.add(commandPanel);
		repaint();
		return;

	}

	// update the playerCommandPanel
	public void updatePlayerCommandPanel() {
		currentPlayerLabel.setText("Current player: "
				+ game.getCurrentPlayer().getName() + " with score: "
				+ game.getCurrentPlayer().getScore());
		// System.out.println(game.getCurrentPlayer().getName());
		repaint();
		return;
	}

	// update all the panels
	public void updateAll() {
		updateBoardPanel();
		updatePlayerInfoPanel();
		updatePlayerCommandPanel();
	}

	public void initialGameMsgPanel() {
		gameMsgPanel.setLayout(new BorderLayout());
		JPanel helpPanel = new JPanel();
		helpPanel.setLayout(new FlowLayout());
		JPanel twols = new JPanel();
		twols.setPreferredSize(new Dimension(70, 35));
		twols.setOpaque(true);
		twols.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, new Color(
				151, 255, 255)));
		helpPanel.add(twols);
		helpPanel.add(new JLabel(": 2LS  "));

		JPanel threels = new JPanel();
		threels.setPreferredSize(new Dimension(70, 35));
		threels.setOpaque(true);
		threels.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5,
				new Color(127, 255, 212)));
		helpPanel.add(threels);
		helpPanel.add(new JLabel(": 3LS  "));

		JPanel twows = new JPanel();
		twows.setPreferredSize(new Dimension(70, 35));
		twows.setOpaque(true);
		twows.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, new Color(
				255, 174, 185)));
		helpPanel.add(twows);
		helpPanel.add(new JLabel(": 2WS  "));

		JPanel threews = new JPanel();
		threews.setPreferredSize(new Dimension(70, 35));
		threews.setOpaque(true);
		threews.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5,
				new Color(255, 165, 0)));
		helpPanel.add(threews);
		helpPanel.add(new JLabel(": 3WS"));

		gameMsgPanel.add(helpPanel, BorderLayout.NORTH);
		gameMsgPanel.add(gameMsgLabel, BorderLayout.CENTER);
		gameMsgPanel.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2,
				Color.RED));
		repaint();
		return;
	}

	public void setTrue() {
		gamePanelFlag = true;
	}

	public void setGameMsgLabel(String string) {
		gameMsgLabel.setText(string);
	}

	public void removeSettingPanel() {
		eastPanel.remove(settingPanel);
	}

	public void triggerPlaceSettingPanel() {
		settingPanel = new JPanel();
		settingPanel.setLayout(new GridLayout(10, 1));
		settingPanel.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1,
				Color.BLACK));
		JLabel settingInstruct = new JLabel(
				"Please define the start location and direction of your placement:");
		JLabel startLocationInstruct = new JLabel("start location: ");
		JLabel directionInstruct = new JLabel(
				"direction of placement: '1' means horizonal while '0' means vertical.");
		JLabel xInstruct = new JLabel("x coordinate: ");
		JLabel yInstruct = new JLabel("y coordinate: ");
		final JTextField xInput = new JTextField(5);
		final JTextField yInput = new JTextField(5);
		final JTextField direction = new JTextField(5);
		JButton submitButton = new JButton("submit");
		// submitButton.setSize(new Dimension(150, 50));
		submitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg) {
				String xResult = xInput.getText().trim();
				String yResult = yInput.getText().trim();
				String dResult = direction.getText().trim();
				if (xResult.equals("") || yResult.equals("")
						|| dResult.equals("")) {
					gameMsgLabel
							.setText("There is empty content in your input!");
					xInput.setText("");
					yInput.setText("");
					direction.setText("");
					return;
				}
				int x = -1;
				int y = -1;
				int d = -1;
				try {
					x = Integer.parseInt(xResult);
					y = Integer.parseInt(yResult);
					d = Integer.parseInt(dResult);

				} catch (NumberFormatException e) {
					gameMsgLabel
							.setText("There is invalid character in your input!");
					xInput.setText("");
					yInput.setText("");
					direction.setText("");
					return;
				}

				if (!game.getBoard().isOnBoard(x, y) || (d != 1 && d != 0)) {
					gameMsgLabel
							.setText("Your input coordinate is out of board or invalid direction!");
					xInput.setText("");
					yInput.setText("");
					direction.setText("");
					return;
				}
				// gameMsgLabel.setText("x: " + x + " y: " + y + " d: " + d);
				gamePanelFlag = false;
				int length = 0;
				if (d == 1) {
					length = boardLength - y;
				} else {
					length = boardLength - x;
				}
				setPlacementInstruction(x, y, d, length);
				popupPlaceWindow(x, y, d);
			}
		});
		JButton cancelButton = new JButton("cancel");
		// cancelButton.setSize(new Dimension(150, 50));
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg) {
				gameMsgLabel.setText("Player: "
						+ game.getCurrentPlayer().getName()
						+ " has cencelled his/her placement.");
				eastPanel.remove(settingPanel);
				gamePanelFlag = true;
				repaint();
			}
		});
		settingPanel.add(settingInstruct);
		settingPanel.add(startLocationInstruct);
		settingPanel.add(xInstruct);
		settingPanel.add(xInput);
		settingPanel.add(yInstruct);
		settingPanel.add(yInput);
		settingPanel.add(directionInstruct);
		settingPanel.add(direction);
		settingPanel.add(submitButton);
		settingPanel.add(cancelButton);
		eastPanel.add(settingPanel);
	}

	// pop up the special tile placement window
	public void popupSpecialTileWindow(SpecialTile specialTile) {
		final SpecialTile specialTileToPlace = specialTile;
		final String specialTileWindowName = "SpecialTile Coordinate Window For Fancy Scribble 1.0";
		final GamePanelListener gamePanel = this;
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				JFrame frame = new JFrame(specialTileWindowName);
				final SpecialTilePanel specialTilePanel = new SpecialTilePanel(
						specialTileToPlace, gamePanel, game, frame);
				specialTilePanel.setOpaque(true);
				frame.add(specialTilePanel);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setResizable(true);
				frame.pack();
				frame.setVisible(true);
			}
		});

	}

	// pop up the exchange window
	public void popupExchangeWindow() {
		final String exchangeWindowName = "Tile Exchange Window For Fancy Scribble 1.0";
		final GamePanelListener gamePanel = this;
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				JFrame frame = new JFrame(exchangeWindowName);
				final ExchangePanel exchangePanel = new ExchangePanel(
						gamePanel, game, frame);
				exchangePanel.setOpaque(true);
				frame.add(exchangePanel);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setResizable(true);
				frame.pack();
				frame.setVisible(true);
			}
		});
	}

	// give player an instruction of the placement on the board
	public void setPlacementInstruction(int startX, int startY, int dir,
			int length) {
		int deltaX = 0;
		int deltaY = 0;
		if (dir == 1) {
			deltaY = 1;
		} else {
			deltaX = 1;
		}
		for (int i = 0; i < length; i++) {
			int currX = startX + i * deltaX;
			int currY = startY + i * deltaY;
			grids[currX + 1][currY + 1].setBorder(BorderFactory
					.createMatteBorder(10, 10, 10, 10, Color.RED));
		}
	}

	// pop up the tile placement window
	public void popupPlaceWindow(int x, int y, int direction) {
		final String placeWindowName = "Tile Place Window For Fancy Scribble 1.0";
		final GamePanelListener gamePanel = this;
		final int startX = x;
		final int startY = y;
		final int dir = direction;
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				JFrame frame = new JFrame(placeWindowName);
				final PlacePanel placePanel = new PlacePanel(gamePanel, game,
						frame, startX, startY, dir);
				placePanel.setOpaque(true);
				frame.add(placePanel);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setResizable(true);
				frame.pack();
				frame.setVisible(true);
			}
		});
	}

}
