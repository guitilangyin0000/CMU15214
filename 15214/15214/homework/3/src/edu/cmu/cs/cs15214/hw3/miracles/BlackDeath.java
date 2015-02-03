package edu.cmu.cs.cs15214.hw3.miracles;

import javax.swing.ImageIcon;

import edu.cmu.cs.cs214.hw3.Location;
import edu.cmu.cs.cs214.hw3.Util;
import edu.cmu.cs.cs214.hw3.World;
import edu.cmu.cs.cs214.hw3.commands.Command;
import edu.cmu.cs.cs214.hw3.commands.WaitCommand;
import edu.cmu.cs.cs214.hw3.items.Item;

public class BlackDeath extends Miracle {

	/**
	 * BlackDeath can kill anything livable in a 3 * 3 range, more important is,
	 * it can spread to one empty grid in this range.
	 */

	private static final int COOLDOWN = 5;
	private static final int STRENGTH = 100;
	private static final ImageIcon blackDeathImage = Util
			.loadImage("blackDeath.gif");

	public BlackDeath(Location location) {
		super(location);
	}

	@Override
	public int getMovingRange() {
		return 1;
	}

	@Override
	public int getCoolDownPeriod() {
		return COOLDOWN;
	}

	@Override
	public Command getNextAction(World world) {

		int x = location.getX();
		int y = location.getY();

		boolean setNew = false;
		for (int i = 0; i < world.getWidth(); i++) {
			for (int j = 0; j < world.getHeight(); j++) {
				if (i == x && j == y) {
					continue;
				}
				if (Math.abs(i - x) < 3 && Math.abs(j - y) < 3) {
					if (Util.isLocationEmpty(world, new Location(i, j)) && !setNew) {
						BlackDeath blackDeath_1 = new BlackDeath(new Location(i,
								j));
						world.addActor(blackDeath_1);
						world.addItem(blackDeath_1);
						setNew = true;
					} else {
						for (Item item : world.getItems()) {
							if (item.getLocation().equals(new Location(i, j))
									&& item.getMeatCalories() > 0) {
								item.loseEnergy(Integer.MAX_VALUE);
							}
						}
					}
				}
			}
		}
		loseEnergy(Integer.MAX_VALUE);
		return new WaitCommand();

	}

	@Override
	public ImageIcon getImage() {
		return blackDeathImage;
	}

	@Override
	public String getName() {
		return "BlackDeath";
	}

	@Override
	public int getStrength() {
		return STRENGTH;
	}

}
