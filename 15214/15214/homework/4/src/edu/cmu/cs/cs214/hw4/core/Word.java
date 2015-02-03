package edu.cmu.cs.cs214.hw4.core;

import java.util.ArrayList;

import edu.cmu.cs.cs214.hw4.core.multipler.Multipler;

public class Word {

	private ArrayList<Tile> tiles;
	private int value;
	private Location startLocation;
	private int direction; // 1 means horizontal, 0 means vertical
	private int length;
	private int timer; // the bonus for the own word

	public Word(Location startLocation, int direction) {
		this.startLocation = startLocation;
		this.direction = direction;
		value = 0;
		length = 0;
		timer = 1;
		tiles = new ArrayList<Tile>();
	}

	public ArrayList<Tile> getTiles() {
		return tiles;
	}

	public void addTile(Tile tile) {
		if (tile == null) {
			throw new IllegalArgumentException("The added tile is null!");
		}
		tiles.add(tile);
		length += 1;
	}

	public int getValue() {
		return value;
	}

	public void addValue(int value) {
		this.value += value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public void setTimer(int timer) {
		this.timer = timer;
	}

	public Location getLocation(Board board, int x, int y) {
		if (board == null) {
			throw new IllegalArgumentException("The board is null!");
		}
		if (!board.isOnBoard(x, y)) {
			throw new IllegalArgumentException(
					"The location is not in the board!");
		}
		return board.getLocation(x, y);
	}

	public Location getStartLocation() {
		return startLocation;
	}

	public int getDirection() {
		return direction;
	}

	public int getTimer() {
		return timer;
	}

	public int getLength() {
		return length;
	}

	/* implement the toString() method of the word*/
	public String toString() {
		StringBuffer output = new StringBuffer();
		for (Tile tile : tiles) {
			output.append(tile.getLetter());
		}
		// System.out.println(output.toString());
		return output.toString();
	}

	/* calculate the value of the word*/
	public void calculateValue(Board board) {
		if (board == null) {
			System.out.println("The board is null!");
			return;
		}
		for (Tile tile : tiles) {
			addValue(tile.getValue());
		}
		int deltaX = 0;
		int deltaY = 0;
		if (direction == 1) {
			deltaY = 1;
		} else {
			deltaX = 1;
		}
		for (int i = 0; i < length; i++) {
			int currX = getStartLocation().getX() + i * deltaX;
			int currY = getStartLocation().getY() + i * deltaY;
			Location loc = getLocation(board, currX, currY);
			if (!loc.isOccupied()) {
				loc.changeWordValue(this, tiles.get(i));
			}
		}
		setValue(getValue() * getTimer());
		// System.out.println(getValue());
	}

}
