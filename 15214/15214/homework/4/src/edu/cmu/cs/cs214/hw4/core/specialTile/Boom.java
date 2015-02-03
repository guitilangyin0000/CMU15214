package edu.cmu.cs.cs214.hw4.core.specialTile;

import edu.cmu.cs.cs214.hw4.core.Game;
import edu.cmu.cs.cs214.hw4.core.Location;
import edu.cmu.cs.cs214.hw4.core.Player;

public class Boom extends BaseSpecialTile{

	private static final int B_PRICE = 30;
	private static final String B_NAME = "Boom";
	private static final int B_RANGE = 3;
	
	public Boom() {
		super();
	}

	@Override
	public String getName() {
		return B_NAME;
	}

	@Override
	public int getPrice() {
		return B_PRICE;
	}

	@Override
	public void makeSpecialEffect(Game game, Location location) {
		game.setBoom(location, B_RANGE);
	}


}
