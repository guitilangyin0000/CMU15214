package edu.cmu.cs.cs214.hw4.core;

import java.util.ArrayList;

import edu.cmu.cs.cs214.hw4.core.specialTile.SpecialTile;

public class Action {
	
	private Location startLocation;
	private int direction;
	private int tileNum;
	private ArrayList<Location> locations;
	private ArrayList<Tile> tiles;
	private SpecialTile specialTile;
	private Location specialTileLocation;
	
	public Action(){
		this.startLocation = null;
		this.direction = -1; // 1 or 0
		tiles = new ArrayList<Tile>();
		locations = new ArrayList<Location>();
		tileNum = 0;
		specialTile = null;
		specialTileLocation = null;
	}
	
	/* determine whether a location is in the action's location pool*/
	public Boolean isIn(Location location){
		if (location == null){
			return false;
		}
		for (Location loc: locations){
			if (loc.getX() == location.getX() && loc.getY() == location.getY()){
				return true;
			}
		}
		return false;
	}
	
	/* add a tile to this action, it's player's choice*/
	public void addTile(Tile tile, Location location){
		if (tile == null){
			System.out.println("The tile is null!");
			return;
		}	
		if (location == null){
			System.out.println("The location is null!");
			return;
		}
		if (isIn(location)){
			System.out.println("The location is set in the action!");
			return;
		}
		tiles.add(tile);
		locations.add(location);
		tileNum += 1;
	}
	
	/* remove all the tiles in this action*/
	public void removeAll(){
		tiles = new ArrayList<Tile>();
		locations = new ArrayList<Location>();
		tileNum = 0;
		startLocation = null;
		direction = -1;
	}
	
	/* set up the start location of the action*/
	public void setStartLocation(Location startLocation){
		if (startLocation == null){
			System.out.println("Start location is null!");
			return;
		}
		this.startLocation = startLocation;
	}
	
	/* set up the direction of the action*/
	public void setDirection(int direction){
		if (direction != 1 && direction != 0){
			System.out.println("Wrong direction input!");
			return;
		}
		this.direction = direction;
	}
	
	/* get the tiles of this action*/
	public ArrayList<Tile> getTiles(){
		return tiles;
	}
	
	/* get all the locations from this action*/
	public ArrayList<Location> getLocations(){
		return locations;
	}
	
	/* get the start location from action*/
	public Location getStartLocation(){
		return startLocation;
	}
	
	/* get the direction of this action*/
	public int getDirection(){
		return direction;
	}
	
	/* get the special tile of this action*/
	public SpecialTile getSpecialTile(){
		return specialTile;
	}
	
	/* get the location for special tile of this action*/
	public Location getSpecialLocation(){
		return specialTileLocation;
	}
	
	/* get the number of tiles of this action*/
	public int getTileNum(){
		return tileNum;
	}
	
	/* set the special tile to this action*/
	public void setSpecialTile(SpecialTile specialTile){
		if (specialTile == null){
			System.out.println("The specialTile is null!");
			return;
		}
		this.specialTile = specialTile;
	}
	
	/* set the location of special tile to this action*/
	public void setSpecialLocation(Location specialTileLocation){
		if (specialTileLocation == null){
			System.out.println("The specialTile location is null!");
			return;
		}
		this.specialTileLocation = specialTileLocation;
	}
	
	/* search the specified location in the board by x, y coordinate*/
	public Location getLocation(Board board, int x, int y) {
		if (board == null) {
			System.out.println("The board is null!");
			return null;
		}
		if (!board.isOnBoard(x, y)) {
			System.out.println("The location is not in the board!");
			return null;
		}
		return board.getLocation(x, y);
	}

}
