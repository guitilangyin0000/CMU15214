package edu.cmu.cs.cs214.hw4.core.specialTile;

import edu.cmu.cs.cs214.hw4.core.Game;
import edu.cmu.cs.cs214.hw4.core.Location;

public class StealMove extends BaseSpecialTile{

	private static final int S_PRICE = 50;
	private static final String S_NAME = "StealMove";
	
	public StealMove() {
		super();
	}
	
	@Override
	public String getName() {
		return S_NAME;
	}

	@Override
	public int getPrice() {
		return S_PRICE;
	}

	@Override
	public void makeSpecialEffect(Game game, Location location) {
		game.makeStealMove(getOwner());
	}

	
}
