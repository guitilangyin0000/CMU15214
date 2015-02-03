package edu.cmu.cs.cs214.hw4.core;

import java.util.ArrayList;

public class Player {

	private static final int TILEMAXNUM = 7;
	private String name;
	private int score;
	private int tileNum;
	private ArrayList<Tile> inventory;

	public Player(String name) {
		this.name = name;
		score = 0;
		tileNum = 0;
		inventory = new ArrayList<Tile>();
	}

	public String getName() {
		return name;
	}

	public int getScore() {
		return score;
	}

	public int getTileNum() {
		return tileNum;
	}

	public void addScore(int value) {
		score += value;
	}

	/* refill the tiles to the player's inventory*/
	public void refillTiles(ArrayList<Tile> tiles) {
		if (tiles == null) {
			throw new IllegalArgumentException("The input tiles are null!");
		}

		if (tiles.size() == 0) {
			return;
		}

//		if (tiles.size() + getTileNum() > TILEMAXNUM) {
//			System.out.println("Exceed the upper limit!");
//			return;
//		}

		for (Tile tile : tiles) {
			inventory.add(tile);
			tileNum += 1;
		}
	}

	public Tile getTile(char letter, int value) {
		for (Tile tile : inventory) {
			if (tile.getLetter() == letter && tile.getValue() == value) {
				return tile;
			}
		}
		return null;
	}

	public Boolean isIn(Tile tile){
		for (int i = 0; i < inventory.size(); i++) {
			if (inventory.get(i).getLetter() == tile.getLetter()
					&& inventory.get(i).getValue() == tile.getValue()) {
				return true;
			}
		}
		return false;
	}
	// judge whether tiles are in the inventory
	public Boolean isIn(ArrayList<Tile> tiles){
		if (tiles == null || tiles.size() > inventory.size()){
			return false;
		}
		ArrayList<Tile> deleteTiles = new ArrayList<Tile>(tiles);
		ArrayList<Tile> checkTiles = new ArrayList<Tile>(inventory);
		int sizePrev = checkTiles.size();
		int deleteSize = deleteTiles.size();
		for (Tile tile: deleteTiles){
			for (int i = 0; i < checkTiles.size(); i++) {
				if (checkTiles.get(i).getLetter() == tile.getLetter()
						&& checkTiles.get(i).getValue() == tile.getValue()) {
					checkTiles.remove(i);
					break;
				}
			}
		}
		if (checkTiles.size() != sizePrev - deleteSize){
			return false;
		}
		return true;
	}
	
	/* remove the tiles array from the player's inventory*/
	public void removeTiles(ArrayList<Tile> tiles) {
		if (tiles == null) {
			System.out.println("The removed tiles are null!");
			return;
		}
		
		//System.out.println(tiles.size());
		ArrayList<Tile> deleteTiles = new ArrayList<Tile>(tiles);
		for (int j = 0; j < deleteTiles.size(); j++) {
			//System.out.println("tiles_size: " + deleteTiles.size());
			//System.out.println(deleteTiles.get(j).getLetter());
			if (!isIn(deleteTiles.get(j))){
				System.out.println("The removed tiles are not in!");
				return;
			}
			int i;
			for (i = 0; i < inventory.size(); i++) {
				if (inventory.get(i).getLetter() == deleteTiles.get(j).getLetter()
						&& inventory.get(i).getValue() == deleteTiles.get(j).getValue()) {
					break;
				}
			}
			//System.out.println(i);
			inventory.remove(i);
			//showInventory();
			tileNum -= 1;
			//System.out.println(tileNum);
		}
		//System.out.println("fuck");
	}

	public ArrayList<Tile> getTiles() {
		return inventory;
	}

	public String showInventory() {
		//System.out.println("Player: " + name + " 's inventory:");
		StringBuffer sb = new StringBuffer();
		for (Tile tile : inventory) {
			sb.append(" | " + tile.getLetter() + " " + tile.getValue());
		}
		sb.append(" | ");
		System.out.println(sb.toString().length());
		return sb.toString();
	}

	public void skipOrder(Game game) {
		if (game == null) {
			throw new IllegalArgumentException("The game is null!");
		}
		game.skipOrder();
	}

}
