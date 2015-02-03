package edu.cmu.cs.cs214.hw4.core;

import java.util.HashSet;
import java.util.Set;

import edu.cmu.cs.cs214.hw4.core.multipler.Multipler;
import edu.cmu.cs.cs214.hw4.core.multipler.Normal;
import edu.cmu.cs.cs214.hw4.core.specialTile.SpecialTile;

public class Location {
	
	private Multipler multipler;
	private int x;
	private int y;
	private Tile tile;
	private SpecialTile specialTile;
	
	public Location(int x, int y){
		this.x = x;
		this.y = y;
		tile = null;
		specialTile = null;
		multipler = new Normal();
	}
	
	public Tile getTile(){
		return tile;
	}
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	
	/* activate the effect of the special tile*/
	public void makeSpecialEffect(Game game){
		if (specialTile != null){
			specialTile.makeSpecialEffect(game, this);
		}
	}
	

	public SpecialTile getSpecialTile(){
		return specialTile;
	}
	
	public Multipler getMultipler(){
		return multipler;
	}
	
	public void removeSpecialTile(){
		if (specialTile != null){
			specialTile = null;
		}
		return;
	}
	
	/* set the special tile*/	
	public void setSpecialTile(SpecialTile specialTile){
		if (specialTile == null){
			System.out.println("The input specialTile is null!");
			return;
		}
		this.specialTile = specialTile;
	}
	
	/* set the multipler of the location*/
	public void setMultipler(Multipler multipler){
		if (multipler == null){
			System.out.println("The input multipler is null!");
			return;
		}
		this.multipler = multipler;
	}
	
	public Boolean isOccupied(){
		return tile != null;
	}
	
	/* add the tile to the lcoation*/
	public void addTile(Tile tile){
		if (isOccupied()){
			System.out.println("This location has been occupied!");
			return;
		}
		if (tile == null){
			System.out.println("The added tile is null!");
			return;
		}
		this.tile = tile;
	}
	
	/* change the value of the word by different multipler*/
	public void changeWordValue(Word word, Tile tile){
		getMultipler().changeWordValue(word, tile);
	}
	
	/* remove the tile from the location*/
	public void removeTile(){
		if (!isOccupied()){
			System.out.println("This location is empty!");
			return;
		}
		this.tile = null;
	}

}
