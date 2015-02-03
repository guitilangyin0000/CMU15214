package edu.cmu.cs.cs214.hw4.core.specialTile;

import edu.cmu.cs.cs214.hw4.core.Game;
import edu.cmu.cs.cs214.hw4.core.Location;
import edu.cmu.cs.cs214.hw4.core.Player;

public class NegativePoints extends BaseSpecialTile{

	private static final int N_PRICE = 30;
	private static final String N_NAME = "NegativePoints";
	
	public NegativePoints() {
		super();
	}

	@Override
	public void makeSpecialEffect(Game game, Location location) {
		game.setNegativePoints();
		return;
	}

	@Override
	public int getPrice() {
		return N_PRICE;
	}

	@Override
	public String getName() {
		return N_NAME;
	}

}
