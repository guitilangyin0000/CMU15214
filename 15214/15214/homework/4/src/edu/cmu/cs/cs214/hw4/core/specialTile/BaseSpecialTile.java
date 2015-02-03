package edu.cmu.cs.cs214.hw4.core.specialTile;

import edu.cmu.cs.cs214.hw4.core.Game;
import edu.cmu.cs.cs214.hw4.core.Location;
import edu.cmu.cs.cs214.hw4.core.Player;

public abstract class BaseSpecialTile implements SpecialTile{
	
	private Player owner;
	
	public BaseSpecialTile(){
	}
	
	public void setOwner(Player player){
		this.owner = player;
	}
	
	
	public abstract String getName();
	
	public abstract int getPrice();
	
	public Player getOwner(){
		return owner;
	}
	
	public abstract void makeSpecialEffect(Game game, Location location);

}
