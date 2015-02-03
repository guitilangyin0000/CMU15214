package edu.cmu.cs.cs214.hw4.core.specialTile;

import edu.cmu.cs.cs214.hw4.core.Game;
import edu.cmu.cs.cs214.hw4.core.Location;
import edu.cmu.cs.cs214.hw4.core.Player;

public class ReversePlayerOrder extends BaseSpecialTile {

	private static final int R_PRICE = 40;
	private static final String R_NAME = "ReversePlayerOrder";

	public ReversePlayerOrder() {
		super();
	}

	@Override
	public int getPrice() {
		return R_PRICE;
	}

	@Override
	public void makeSpecialEffect(Game game, Location location) {
		game.reverseOrder();
	}

	@Override
	public String getName() {
		return R_NAME;
	}

}
