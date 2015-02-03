package edu.cmu.cs.cs214.hw4.core;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import edu.cmu.cs.cs214.hw4.core.specialTile.SpecialTile;

public class Game {

	private static final String PATH = "assets/words.txt";
	private static final int PLAYERMAXNUM = 4;
	private static final int INVENTORYMAXNUM = 7;
	private Controller controller;
	private TilePackage tilePackage;
	private ArrayList<Player> players;
	private int playerNum;
	private Action action;
	private Dictionary dictionary;
	private SpecialTileStore specialTileStore;
	private Board board;
	private Boolean isFirstMove;
	private Boolean negativePointsFlag;
	private Boolean boomFlag;
	private Boolean stealFlag;
	private Player benefier;
	// private Location stealLocation;
	private ArrayList<Location> boomLocations;
	private int boomRange;

	public Game() {
		controller = new Controller();
		tilePackage = new TilePackage();
		players = new ArrayList<Player>();
		playerNum = 0;
		action = new Action();
		dictionary = new Dictionary();
		dictionary.initial(PATH);
		board = new Board(dictionary);
		specialTileStore = new SpecialTileStore();
		negativePointsFlag = false;
		boomFlag = false;
		stealFlag = false;
		benefier = null;
		// stealLocation = null;
		boomLocations = new ArrayList<Location>();
		boomRange = -1;
		isFirstMove = false;
		initialGame();
	}

	/* initial the game */
	public void initialGame() {
		tilePackage.initial();
		board.intialLocations();
		specialTileStore.initial();
	}

	/* initial the inventory of */
	public void initialPlayerInventory() {
		for (Player player : players) {
			giveTiles(getRandomTilesFromPackage(INVENTORYMAXNUM), player);
			System.out.println("player: " + player.getName()
					+ "'s inventory has been succssfully initialized.");
		}
	}

	/* get the number of special tiles in special Tile Store */
	public int getSpecialTileStoreNum() {
		return specialTileStore.getSpNum();
	}

	/* check whether the player is in the game */
	public Boolean isIn(String playerName) {
		for (Player player : players) {
			if (player.getName().equals(playerName)) {
				return true;
			}
		}
		return false;
	}

	public void makeStealMove(Player benefier) {
		stealFlag = true;
		this.benefier = benefier;
		// stealLocation = location;
	}

	public void unSetStealMove() {
		stealFlag = false;
		benefier = null;
		// stealLocation = null;
	}

	/* add the player to the game */
	public void addPlayer(Player player) {
		if (playerNum == PLAYERMAXNUM) {
			System.out.println("The number of players has reached maximum!");
			return;
		}
		if (player == null) {
			System.out.println("The input player is null!");
			return;
		}
		if (isIn(player.getName())) {
			System.out.println("The player has already in the player list!");
			return;
		}
		players.add(player);
		playerNum += 1;
		controller.addPlayer(player);
	}

	/* search the player by name */
	public Player getPlayerByName(String name) {
		for (Player player : players) {
			if (player.getName().equals(name)) {
				return player;
			}
		}
		return null;
	}

	/* get the tile package of game */
	public TilePackage getTilePackage() {
		return tilePackage;
	}

	/* get the controller of game */
	public Controller getController() {
		return controller;
	}

	/* start the game and set the isFirstMove flag to be true */
	public void startGame() {
		isFirstMove = true;
	}

	/* end the game and print list of winners */
	public void endGame() {
		ArrayList<Player> winners = getWinner();
		System.out.println("The winner is/are:");
		for (Player player : winners) {
			System.out.println(" " + player.getName());
		}

	}

	/* skip the order */
	public void skipOrder() {
		initialAction();
		// isFirstMove = false;
		controller.skipOrder();
	}

	/* get the number of players in this game */
	public int getPlayerNum() {
		return playerNum;
	}

	/* get the player list of this game */
	public ArrayList<Player> getPlayers() {
		return players;
	}

	/* get the current player of this game */
	public Player getCurrentPlayer() {
		return controller.getCurrentPlayer();
	}

	/* get the score of the player */
	public int getScore(Player player) {
		if (player == null) {
			throw new IllegalArgumentException("The player is null!");
		}
		return player.getScore();
	}

	/* get random tile list from tile package by number */
	public ArrayList<Tile> getRandomTilesFromPackage(int number) {
		return tilePackage.getTiles(number);
	}

	/* give the tiles back to the tile package */
	public void giveTilesToPackage(ArrayList<Tile> tiles) {
		tilePackage.addTiles(tiles);
	}

	/* remove tile from the player's inventory */
	public void removeTiles(ArrayList<Tile> tiles, Player player) {
		if (tiles == null || player == null) {
			throw new IllegalArgumentException("The input has null!");
		}
		player.removeTiles(tiles);
	}

	/* give tiles to the player's inventory */
	public void giveTiles(ArrayList<Tile> tiles, Player player) {
		if (tiles == null || player == null) {
			System.out.println("The input has null!");
			return;
		}
		player.refillTiles(tiles);
	}

	/* exchange request for exchanging tiles for a player */
	public void exchangeTiles(ArrayList<Tile> tiles1, Player player) {
		// System.out.println("step1");
		if (tiles1 == null || player == null) {
			System.out.println("The input has null!");
			return;
		}
		// System.out.println("step2");
		int size = tiles1.size();

		player.removeTiles(tiles1);

		// System.out.println("step2.5");
		for (Tile tile : tiles1) {
			tilePackage.addTile(tile);
		}
		// System.out.println("step3");
		ArrayList<Tile> tiles2 = tilePackage.getTiles(size);
		player.refillTiles(tiles2);
		// System.out.println("step4");
	}

	/* set start location to action */
	public void setStartLocationToAction(int x, int y) {
		if (!board.isOnBoard(x, y)) {
			System.out.println("The location is not in the board!");
			return;
		}
		Location loc = board.getLocation(x, y);
		action.setStartLocation(loc);
	}

	/* set direction to action */
	public void setDirectionToAction(int direction) {
		action.setDirection(direction);
	}

	/* add tiles to action */
	public void addTileToAction(Tile tile, int x, int y) {
		if (!board.isOnBoard(x, y)) {
			System.out.println("The location is not in the board!");
			return;
		}
		Location loc = board.getLocation(x, y);
		action.addTile(tile, loc);
	}

	/* remove tile from the location on the board by coordinate */
	public void removeTileFromLocation(int x, int y) {
		if (!board.isOnBoard(x, y)) {
			System.out.println("The location is not in the board!");
			return;
		}
		board.removeTileFromLocation(x, y);
	}

	/* remove all the tiles from the action */
	public void removeAllToAction() {
		action.removeAll();
	}

	/* set the special tile to action */
	public void setSpecialTileToAction(SpecialTile specialTile) {
		action.setSpecialTile(specialTile);
	}

	/* set the location of special tile to action */
	public void setSpecialTileLocationToAction(int x, int y) {
		Location location = board.getLocation(x, y);
		action.setSpecialLocation(location);
	}

	/* clear the action for the next player */
	public void initialAction() {
		action = new Action();
	}

	/* call for the board's sub method */
	public Word makeOneWord() {
		return board.makeOneWord(action);
	}

	/* get the action from the game */
	public Action getAction() {
		return action;
	}

	/* set the tile to location on the board by coordinate */
	public void addTileToLocation(Tile tile, int x, int y) {
		if (!board.isOnBoard(x, y)) {
			System.out.println("The location is not in the board!");
			return;
		}
		board.addTileToLocation(tile, x, y);
	}

	/* get the board of this game */
	public Board getBoard() {
		return board;
	}

	/* show the board for debug, feature only in hw4b */
	public void showBoard() {
		board.showBoard();
	}

	/* player buy special tile */
	public SpecialTile buySpecialTile(Player player, String specialTileName) {
		return specialTileStore.buySpecialTile(player, specialTileName);

	}

	/* call for the sub method of controller */
	public void retriveOrder() {
		controller.retriveOrder();
	}

	/* call for the sub method of controller */
	public void updateOrder() {
		initialAction();
		// isFirstMove = false;
		controller.updateOrder();
	}

	public void showBoomPoints(Set<Location> boomPoints) {
		if (boomPoints.size() > 0) {
			for (Location toRemoveLoc : boomPoints) {
				System.out.println("boom location: x: " + toRemoveLoc.getX()
						+ " y: " + toRemoveLoc.getY());
			}
		}
	}

	/* if the action is validated, make action to the board and make effect */
	public void makeAction() {
		Player player = getCurrentPlayer();
		/* active the effect of special tiles */
		board.activeSpecialEffect(action, this, player);

		/* if there are boom special tiles and they are activated */
		Set<Location> boomPoints = new HashSet<Location>();
		if (boomFlag) {
			for (int i = 0; i < boomLocations.size(); i++) {
				Location curr = boomLocations.get(i);
				int centerX = curr.getX();
				int centerY = curr.getY();
				int radius = boomRange;
				for (int j = 0; j < board.getLength(); j++) {
					for (int k = 0; k < board.getLength(); k++) {
						if (Math.abs(j - centerX) < radius
								&& Math.abs(k - centerY) < radius) {
							Location toAdd = board.getLocation(j, k);
							boomPoints.add(toAdd);
						}
					}
				}

			}

		}

		//showBoomPoints(boomPoints);

		/* set the special tile to the location in the board */
		// board.setSpecialTileToLocation(action);
		/* add the score of the action to the player */
		if (stealFlag == false && benefier == null) {
			addScoreToPlayer(player);
			/* set the tiles of the action to the board */
			board.setTilesToLocation(action);
			removeTiles(action.getTiles(), player);
			/* refill the tiles from the tile package */
			int size = INVENTORYMAXNUM - player.getTileNum();
			ArrayList<Tile> tiles = getRandomTilesFromPackage(size);
			giveTiles(tiles, player);
		} else {
			removeTiles(action.getTiles(), player);
			int size = INVENTORYMAXNUM - player.getTileNum();
			ArrayList<Tile> tiles = getRandomTilesFromPackage(size);
			giveTiles(tiles, player);
			giveTiles(action.getTiles(), benefier);
		}
		/* if there are booms, reset the tiles and the score */
		if (boomPoints.size() > 0) {
			Word word = board.makeOneWord(action);
			ArrayList<Word> adjacentWords = board.makeAdjacentWords(action);
			adjacentWords.add(word);
			Set<Location> actionLocs = new HashSet<Location>();
			for (Word singleWord : adjacentWords) {
				Location startLocation = singleWord.getStartLocation();
				int length = singleWord.getLength();
				int direction = singleWord.getDirection();
				int deltaX = 0;
				int deltaY = 0;
				if (direction == 1) {
					deltaY = 1;
				} else {
					deltaX = 1;
				}
				for (int m = 0; m < length; m++) {
					Location curr_1 = board.getLocation(startLocation.getX()
							+ m * deltaX, startLocation.getY() + m * deltaY);
					actionLocs.add(curr_1);
				}
			}

			Set<Location> interSet = new HashSet<Location>();
			for (Location loc_1 : boomPoints) {
				for (Location loc_2 : actionLocs) {
					if (loc_1.getX() == loc_2.getX()
							&& loc_1.getY() == loc_2.getY()) {
						interSet.add(loc_2);
					}
				}
			}

			if (interSet.size() > 0) {
				int deleteScore = 0;
				for (Location loc : interSet) {
					if (loc.isOccupied()) {
						// System.out.println("x: " + loc.getX() + " y: " +
						// loc.getY());
						// System.out.println(loc.getTile().getValue());
						deleteScore += loc.getTile().getValue();
					}
				}
				if (stealFlag == false && benefier == null) {
					player.addScore(-1 * deleteScore);
				}
			}

			// remove all the tiles in the boom area
			for (Location toRemoveLoc : boomPoints) {
				if (toRemoveLoc.isOccupied()) {
					toRemoveLoc.removeTile();
				}
			}
		}
		unSetBoom();
		unSetStealMove();
		initialAction();
		updateOrder();
	}

	/* check the validation of the action */
	public int checkValidAction() {
		Boolean start = true;
		System.out.println("isFirstMove: " + isFirstMove.toString());
		if (isFirstMove || board.isEmpty()) {
			start &= board.isOnStar(action);
			if (!start) {
				return 0;
			}
			// isFirstMove = false;
		}
		start &= board.checkValidWord(action);
		if (!start) {
			return 3;
		}
		if (!isFirstMove && !board.isEmpty()) {
			start &= board.checkNearWord(action);
			if (!start) {
				return 2;
			}
			start &= board.checkAdjacentWords(action);
			if (!start) {
				return 4;
			}
		}
		return 1;
	}

	public Boolean isFirstMove() {
		return isFirstMove;
	}

	public void resetFirstMove() {
		isFirstMove = true;
	}

	public void setFirstMove() {
		isFirstMove = false;
	}

	/* reverse the order */
	public void reverseOrder() {
		controller.reverseOrder();
	}

	/* set the flag for NegativePoints special tile */
	public void setNegativePoints() {
		negativePointsFlag = true;
	}

	/* reset the flag for NegativePoints special tile */
	public void unSetNegativePoints() {
		negativePointsFlag = false;
	}

	/* set the boom relevant parameters for the game */
	public void setBoom(Location location, int range) {
		if (location == null) {
			System.out.println("The location is null!");
			return;
		}
		boomFlag = true;
		boomLocations.add(location);
		boomRange = range;
	}

	/* reset the parameters of boom for the game */
	public void unSetBoom() {
		boomFlag = false;
		boomLocations = new ArrayList<Location>();
		boomRange = -1;
	}

	/* add the score of the action to the player */
	public void addScoreToPlayer(Player player) {
		if (player == null) {
			System.out.println("The player is null!");
			return;
		}
		int timer = 1;
		if (negativePointsFlag == true) {
			timer = -1;
		}
		int score = board.getActionScore(action) * timer;
		// System.out.println("score:" + score);
		unSetNegativePoints();
		player.addScore(score);
	}

	/* show the inventory of the player */
	public void showPlayerInventory() {
		for (Player player : players) {
			player.showInventory();
		}
	}

	/* get the winner of the game */
	public ArrayList<Player> getWinner() {
		ArrayList<Player> winners = new ArrayList<Player>();
		int maxScore = Integer.MIN_VALUE;
		for (Player player : players) {
			if (player.getScore() > maxScore) {
				maxScore = player.getScore();
			}
		}
		for (Player player : players) {
			if (player.getScore() == maxScore) {
				winners.add(player);
			}
		}
		return winners;
	}
}
