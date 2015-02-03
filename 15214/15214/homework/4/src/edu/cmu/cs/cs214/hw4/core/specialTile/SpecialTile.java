package edu.cmu.cs.cs214.hw4.core.specialTile;

import edu.cmu.cs.cs214.hw4.core.Game;
import edu.cmu.cs.cs214.hw4.core.Location;
import edu.cmu.cs.cs214.hw4.core.Player;

public interface SpecialTile {

	public void makeSpecialEffect(Game game, Location location);
	
	public void setOwner(Player player);
	
	public String getName();
	
	public Player getOwner();
	
	public int getPrice();
	
	
}
