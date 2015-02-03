package edu.cmu.cs.cs214.hw4.core;

import java.util.ArrayList;

import edu.cmu.cs.cs214.hw4.core.specialTile.Boom;
import edu.cmu.cs.cs214.hw4.core.specialTile.NegativePoints;
import edu.cmu.cs.cs214.hw4.core.specialTile.OneMoreOrder;
import edu.cmu.cs.cs214.hw4.core.specialTile.ReversePlayerOrder;
import edu.cmu.cs.cs214.hw4.core.specialTile.SpecialTile;
import edu.cmu.cs.cs214.hw4.core.specialTile.StealMove;

public class SpecialTileStore {
	
	private ArrayList<SpecialTile> specialTileNames;
	private int spNum;
	
	public SpecialTileStore(){
		specialTileNames = new ArrayList<SpecialTile>();
	}
	
	public void initial(){
		specialTileNames.add(new Boom());
		specialTileNames.add(new OneMoreOrder());
		specialTileNames.add(new NegativePoints());
		specialTileNames.add(new ReversePlayerOrder());
		specialTileNames.add(new StealMove());
		spNum = specialTileNames.size();
	}
	
	public int getSpNum(){
		return spNum;
	}
	
	public Boolean isInStore(String specialTileName){
		for (SpecialTile specialTile: specialTileNames){
			if (specialTileName.equals(specialTile.getName())){
				return true;
			}
		}
		return false;
	}
	
	/* check whether the player is able to buy the special tile*/
	public SpecialTile buySpecialTile(Player player, String specialTileName){
		if (player == null){
			System.out.println("The input has null parameter!");
			return null;
		}
		for (SpecialTile specialTile: specialTileNames){
			if (specialTileName.equals(specialTile.getName())){
				int price = specialTile.getPrice();
				if (player.getScore() >= price) {
					player.addScore(-1 * price);
					specialTile.setOwner(player);
					return specialTile;
				}else{
					return null;
				}
			}
		}
		return null;
	}
	

}
