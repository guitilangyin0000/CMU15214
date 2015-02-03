package edu.cmu.cs.cs214.hw4.core;

import java.util.ArrayList;
import java.util.Random;
import java.util.Set;

import edu.cmu.cs.cs214.hw4.core.multipler.DoubleLetter;
import edu.cmu.cs.cs214.hw4.core.multipler.DoubleWord;
import edu.cmu.cs.cs214.hw4.core.multipler.TripleLetter;
import edu.cmu.cs.cs214.hw4.core.multipler.TripleWord;
import edu.cmu.cs.cs214.hw4.core.specialTile.SpecialTile;

public class Board {

	private static final int LENGTH = 15;
	private Dictionary dic;
	private Location[][] locations;
	private int emptyNum;

	/* input the word dictionary */
	public Board(Dictionary dic) {
		this.dic = dic;
		locations = new Location[LENGTH][LENGTH];
		emptyNum = LENGTH * LENGTH;
	}

	/* initial the board by setting and defining location */
	public void intialLocations() {
		for (int i = 0; i < LENGTH; i++) {
			for (int j = 0; j < LENGTH; j++) {
				locations[i][j] = new Location(i, j);
			}
		}
		// set 3WS to location
		locations[0][0].setMultipler(new TripleWord());
		locations[0][7].setMultipler(new TripleWord());
		locations[0][14].setMultipler(new TripleWord());
		locations[7][0].setMultipler(new TripleWord());
		locations[7][14].setMultipler(new TripleWord());
		locations[14][0].setMultipler(new TripleWord());
		locations[14][7].setMultipler(new TripleWord());
		locations[14][14].setMultipler(new TripleWord());

		// set 2WS to location
		locations[1][1].setMultipler(new DoubleWord());
		locations[2][2].setMultipler(new DoubleWord());
		locations[3][3].setMultipler(new DoubleWord());
		locations[4][4].setMultipler(new DoubleWord());
		locations[7][7].setMultipler(new DoubleWord());
		locations[10][10].setMultipler(new DoubleWord());
		locations[11][11].setMultipler(new DoubleWord());
		locations[12][12].setMultipler(new DoubleWord());
		locations[13][13].setMultipler(new DoubleWord());
		locations[13][1].setMultipler(new DoubleWord());
		locations[12][2].setMultipler(new DoubleWord());
		locations[11][3].setMultipler(new DoubleWord());
		locations[10][4].setMultipler(new DoubleWord());
		locations[4][10].setMultipler(new DoubleWord());
		locations[3][11].setMultipler(new DoubleWord());
		locations[2][12].setMultipler(new DoubleWord());
		locations[1][13].setMultipler(new DoubleWord());

		// set 2LS to location
		locations[0][3].setMultipler(new DoubleLetter());
		locations[0][11].setMultipler(new DoubleLetter());
		locations[2][6].setMultipler(new DoubleLetter());
		locations[2][8].setMultipler(new DoubleLetter());
		locations[3][0].setMultipler(new DoubleLetter());
		locations[3][7].setMultipler(new DoubleLetter());
		locations[3][14].setMultipler(new DoubleLetter());
		locations[6][2].setMultipler(new DoubleLetter());
		locations[6][6].setMultipler(new DoubleLetter());
		locations[6][8].setMultipler(new DoubleLetter());
		locations[6][12].setMultipler(new DoubleLetter());
		locations[7][3].setMultipler(new DoubleLetter());
		locations[7][11].setMultipler(new DoubleLetter());
		locations[8][2].setMultipler(new DoubleLetter());
		locations[8][6].setMultipler(new DoubleLetter());
		locations[8][8].setMultipler(new DoubleLetter());
		locations[8][12].setMultipler(new DoubleLetter());
		locations[11][0].setMultipler(new DoubleLetter());
		locations[11][7].setMultipler(new DoubleLetter());
		locations[11][14].setMultipler(new DoubleLetter());
		locations[12][6].setMultipler(new DoubleLetter());
		locations[12][8].setMultipler(new DoubleLetter());
		locations[14][3].setMultipler(new DoubleLetter());
		locations[14][11].setMultipler(new DoubleLetter());

		// set 3LS to location
		locations[1][5].setMultipler(new TripleLetter());
		locations[1][9].setMultipler(new TripleLetter());
		locations[5][1].setMultipler(new TripleLetter());
		locations[5][5].setMultipler(new TripleLetter());
		locations[5][9].setMultipler(new TripleLetter());
		locations[5][13].setMultipler(new TripleLetter());
		locations[9][1].setMultipler(new TripleLetter());
		locations[9][5].setMultipler(new TripleLetter());
		locations[9][9].setMultipler(new TripleLetter());
		locations[9][13].setMultipler(new TripleLetter());
		locations[13][5].setMultipler(new TripleLetter());
		locations[13][9].setMultipler(new TripleLetter());
	}

	/* check whether the coordinate is on the board */
	public Boolean isOnBoard(int x, int y) {
		return x < LENGTH && y < LENGTH && x >= 0 && y >= 0;
	}

	public Location[][] getLocations() {
		return locations;
	}

	public Boolean isEmpty() {
		for (int i = 0; i < LENGTH; i++) {
			for (int j = 0; j < LENGTH; j++) {
				if (locations[i][j].isOccupied()) {
					return false;
				}
			}
		}
		return true;
	}

	/* get the length parameter from the board */
	public int getLength() {
		return LENGTH;
	}

	/* get the location by coordinate */
	public Location getLocation(int x, int y) {
		if (!isOnBoard(x, y)) {
			System.out.println("The location is not in the board!");
			return null;
		}
		return locations[x][y];
	}

	/* check whether the word is in the dictionary */
	public Boolean isIn(String string) {
		return dic.isIn(string);
	}

	/* add the tile to the board by location */
	public void addTileToLocation(Tile tile, int x, int y) {
		Location loc = getLocation(x, y);
		if (loc == null || tile == null) {
			System.out.println("The input has null!");
			return;
		}
		loc.addTile(tile);
	}

	/* remove the tile from the board */
	public void removeTileFromLocation(int x, int y) {
		Location loc = getLocation(x, y);
		if (loc == null) {
			System.out.println("The input has null!");
			return;
		}
		loc.removeTile();
	}

	/* check whether the locations of the action are near the */
	/* exist words on the board */
	public Boolean checkNearWord(Action action) {
		if (action == null) {
			System.out.println("The action is null!");
			return false;
		}
		ArrayList<Location> locs = action.getLocations();
		for (Location loc : locs) {
			int x = loc.getX();
			int y = loc.getY();
			if (isOnBoard(x - 1, y) && getLocation(x - 1, y).isOccupied()) {
				return true;
			}
			if (isOnBoard(x + 1, y) && getLocation(x + 1, y).isOccupied()) {
				return true;
			}
			if (isOnBoard(x, y - 1) && getLocation(x, y - 1).isOccupied()) {
				return true;
			}
			if (isOnBoard(x, y + 1) && getLocation(x, y + 1).isOccupied()) {
				return true;
			}
		}
		return false;
	}

	/* make initial word from the action, no adjacent word included */
	public Word makeOneWord(Action action) {
		if (action == null) {
			System.out.println("The action is null!");
			return null;
		}
		ArrayList<Tile> tiles = action.getTiles();
		ArrayList<Location> locs = action.getLocations();
		int dir = action.getDirection();
		Location startLocation = action.getStartLocation();
		// System.out.println(" x : " + startLocation.getX());
		int deltaX = 0;
		int deltaY = 0;
		if (dir == 1) {
			deltaY = -1;
		} else {
			deltaX = -1;
		}
		while (isOnBoard(startLocation.getX() + deltaX, startLocation.getY()
				+ deltaY)
				&& getLocation(startLocation.getX() + deltaX,
						startLocation.getY() + deltaY).isOccupied()) {
			startLocation = getLocation(startLocation.getX() + deltaX,
					startLocation.getY() + deltaY);
		}

		Location endLocation = locs.get(locs.size() - 1);
		int betaX = 0;
		int betaY = 0;
		if (dir == 1) {
			betaY = 1;
		} else {
			betaX = 1;
		}
		while (isOnBoard(endLocation.getX() + betaX, endLocation.getY() + betaY)
				&& getLocation(endLocation.getX() + betaX,
						endLocation.getY() + betaY).isOccupied()) {
			endLocation = getLocation(endLocation.getX() + betaX,
					endLocation.getY() + betaY);
		}
		Word word = new Word(startLocation, dir);
		int index = 0;
		int length = Math.max(endLocation.getX() - startLocation.getX(),
				endLocation.getY() - startLocation.getY()) + 1;
		for (int i = 0; i < length; i++) {
			Location curr = getLocation(startLocation.getX() + i * betaX,
					startLocation.getY() + i * betaY);
			if (curr.isOccupied()) {
				word.addTile(curr.getTile());
			} else {
				word.addTile(tiles.get(index));
				index += 1;
			}
		}

		return word;

	}

	/* check whether all the adjacent words are in the dictionary */
	public Boolean checkAdjacentWords(Action action) {
		ArrayList<Word> words = makeAdjacentWords(action);
		if (words.size() == 0) {
			return true;
		}
		for (Word word : words) {
			if (!isIn(word.toString().toLowerCase())) {
				return false;
			}
		}
		return true;
	}

	/* after validation, set all the tiles of the action to the board */
	public void setTilesToLocation(Action action) {
		ArrayList<Tile> tiles = action.getTiles();
		ArrayList<Location> locations = action.getLocations();
		for (int i = 0; i < tiles.size(); i++) {
			locations.get(i).addTile(tiles.get(i));
		}
	}

	/* active the special effect if the action trigger special tiles */
	public void activeSpecialEffect(Action action, Game game, Player player) {
		Word word = makeOneWord(action);
		Location startLocation = word.getStartLocation();
		int direction = word.getDirection();
		int length = word.getLength();
		int deltaX = 0;
		int deltaY = 0;
		if (direction == 1) {
			deltaY = 1;
		} else {
			deltaX = 1;
		}
		for (int i = 0; i < length; i++) {
			Location curr = getLocation(startLocation.getX() + deltaX * i,
					startLocation.getY() + deltaY * i);
			if (curr.getSpecialTile() != null) {
				if (curr.getSpecialTile().getOwner().getName() == player.getName()){
					continue;
				}
				curr.makeSpecialEffect(game);
				curr.removeSpecialTile();
			}
		}
	}

	/* set the location of special tile to board from action */
	public void setSpecialTileToLocation(SpecialTile specialTile, int x, int y) {
		if (specialTile == null) {
			System.out.println("Null special tile when setting!");
			return;
		}
		Location location = getLocation(x, y);
		if (location == null) {
			System.out.println("Null location when setting!!");
			return;
		}
		if (location.isOccupied()) {
			System.out
					.println("Occupied location could not be set with special tile!");
			return;
		}
		if (location.getSpecialTile() != null
				&& location.getSpecialTile().getName() == "StealMove") {
			Player owner = location.getSpecialTile().getOwner();
			Location randomLoc = null;
			while (randomLoc == null) {
				Random rand = new Random();
				int randomX = rand.nextInt(LENGTH);
				int randomY = rand.nextInt(LENGTH);
				if (!getLocation(randomX, randomY).isOccupied()) {
					randomLoc = getLocation(randomX, randomY);
					specialTile.setOwner(owner);
					randomLoc.setSpecialTile(specialTile);
				}
			}
		} else {
			location.setSpecialTile(specialTile);
		}
	}

	/* set the special tile to board from the action */
	public void setSpecialTileToLocation(Action action) {
		SpecialTile specialTile = action.getSpecialTile();
		Location specialTileLocation = action.getSpecialLocation();
		if (specialTile != null && specialTileLocation != null) {
			specialTileLocation.setSpecialTile(specialTile);
		}
	}

	/* calculate the score of this action */
	public int getActionScore(Action action) {
		int score = 0;
		Word word = makeOneWord(action);
		// System.out.println(word.toString());
		word.calculateValue(this);
		score += word.getValue();
		// System.out.println(word.getValue());
		ArrayList<Word> nearWords = makeAdjacentWords(action);
		if (nearWords.size() > 0) {
			for (Word curr : nearWords) {
				// System.out.println(curr.toString());
				curr.calculateValue(this);
				score += curr.getValue();
				// System.out.println(curr.getValue());
			}
		}
		// System.out.println("real score: " + score);
		return score;
	}

	/* extract all the adjacent words from the action */
	public ArrayList<Word> makeAdjacentWords(Action action) {
		Word word = makeOneWord(action);
		ArrayList<Word> words = new ArrayList<Word>();
		ArrayList<Tile> tiles = word.getTiles();
		int dir = word.getDirection();
		Location startLocation = word.getStartLocation();
		int length = word.getLength();
		// System.out.println("word length: " + length);
		int betaX = 0;
		int betaY = 0;
		if (dir == 1) {
			betaY = 1;

		} else {
			betaX = 1;
		}
		for (int i = 0; i < length; i++) {
			Location curr = getLocation(startLocation.getX() + i * betaX,
					startLocation.getY() + i * betaY);

			if (!curr.isOccupied()) {
				// System.out.println("curr: " + "x: " + curr.getX() + " y: " +
				// curr.getY());
				ArrayList<Tile> tmp = new ArrayList<Tile>();
				tmp.add(tiles.get(i));
				// System.out.println("tile: " + tiles.get(i).getLetter());
				Location upper = curr;
				Location lower = curr;
				while (isOnBoard(upper.getX() - betaY, upper.getY() - betaX)
						&& getLocation(upper.getX() - betaY,
								upper.getY() - betaX).isOccupied()) {
					upper = getLocation(upper.getX() - betaY, upper.getY()
							- betaX);
					tmp.add(0, upper.getTile());
				}
				while (isOnBoard(lower.getX() + betaY, lower.getY() + betaX)
						&& getLocation(lower.getX() + betaY,
								lower.getY() + betaX).isOccupied()) {
					lower = getLocation(lower.getX() + betaY, lower.getY()
							+ betaX);
					tmp.add(lower.getTile());
				}

				// System.out.println("upper: " + "x: " + upper.getX() + " y: "
				// + upper.getY());
				// System.out.println("lower: " + "x: " + lower.getX() + " y: "
				// + lower.getY());

				if (upper.getX() != lower.getX()
						|| upper.getY() != lower.getY()) {
					Word tmp_word = new Word(upper, Math.abs(dir - 1));
					for (Tile tile : tmp) {
						// System.out.println("tile: " + tile.getLetter());
						tmp_word.addTile(tile);
					}
					// System.out.println("tmp_word: " + tmp_word.toString());
					words.add(tmp_word);
				}

			}
		}
		return words;

	}

	/* check whether the initial word is valid from this action */
	public Boolean checkValidWord(Action action) {
		if (action == null) {
			System.out.println("The action is null!");
			return false;
		}
		Word word = makeOneWord(action);
		if (word == null) {
			return false;
		}
		return isIn(word.toString().toLowerCase());
	}

	/* for the first move, check whether the action is on the star signal */
	public Boolean isOnStar(Action action) {
		if (action == null) {
			System.out.println("The input action is null!");
			return false;
		}
		ArrayList<Location> locations = action.getLocations();
		for (Location curr : locations) {
			if (curr.getX() == (LENGTH - 1) / 2
					&& curr.getY() == (LENGTH - 1) / 2) {
				return true;
			}
		}
		return false;
	}

	// for debug, show all the properties of the board
	public void showBoard() {
		System.out.println("******* Current Board *******");
		System.out.println("******* Tile Board *******");
		for (int i = 0; i < LENGTH; i++) {
			for (int j = 0; j < LENGTH; j++) {
				Location curr = getLocation(i, j);
				if (curr.isOccupied()) {
					System.out.print(curr.getTile().getLetter());
				} else {
					System.out.print('-');
				}
				if (j == LENGTH - 1) {
					System.out.println("");
				}
			}
		}
		System.out.println("******* Value Board *******");
		for (int i = 0; i < LENGTH; i++) {
			for (int j = 0; j < LENGTH; j++) {
				Location curr = getLocation(i, j);
				if (curr.isOccupied()) {
					System.out.print(curr.getTile().getValue());
				} else {
					System.out.print('-');
				}
				if (j == LENGTH - 1) {
					System.out.println("");
				}
			}
		}
		System.out.println("******* Multipler Board *******");
		for (int i = 0; i < LENGTH; i++) {
			for (int j = 0; j < LENGTH; j++) {
				Location curr = getLocation(i, j);
				if (curr.getMultipler().getName() != "NORMAL") {
					System.out.print(curr.getMultipler().getName());
				} else {
					System.out.print("---");
				}
				if (j == LENGTH - 1) {
					System.out.println("");
				}
			}
		}
		System.out.println("******* SpecialTile Board *******");
		for (int i = 0; i < LENGTH; i++) {
			for (int j = 0; j < LENGTH; j++) {
				Location curr = getLocation(i, j);
				if (curr.getSpecialTile() != null) {
					System.out.print(curr.getSpecialTile().getName().charAt(0));
				} else {
					System.out.print('-');
				}
				if (j == LENGTH - 1) {
					System.out.println("");
				}
			}
		}
	}

}
