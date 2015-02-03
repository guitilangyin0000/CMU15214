package edu.cmu.cs.cs214.hw4.core.specialTile;

import edu.cmu.cs.cs214.hw4.core.Game;
import edu.cmu.cs.cs214.hw4.core.Location;
import edu.cmu.cs.cs214.hw4.core.Player;

public class OneMoreOrder extends BaseSpecialTile {

	private static final int O_PRICE = 30;
	private static final String O_NAME = "OneMoreOrder";

	public OneMoreOrder() {
		super();
	}

	@Override
	public String getName() {
		return O_NAME;
	}

	@Override
	public int getPrice() {
		return O_PRICE;
	}

	public void makeSpecialEffect(Game game, Location location) {
		game.retriveOrder();
	}

}
