package edu.cmu.cs.cs15214.hw3.miracles;

import javax.swing.ImageIcon;

import edu.cmu.cs.cs214.hw3.Location;
import edu.cmu.cs.cs214.hw3.Util;
import edu.cmu.cs.cs214.hw3.World;
import edu.cmu.cs.cs214.hw3.commands.Command;
import edu.cmu.cs.cs214.hw3.commands.MoveCommand;
import edu.cmu.cs.cs214.hw3.commands.WaitCommand;
import edu.cmu.cs.cs214.hw3.items.Item;

public class Crossing extends Miracle {

	/**
	 * Crossing is interesting, it can kill any items in the same row or in the
	 * same line. It can jump to any empty grids in this world.
	 */

	private static final int COOLDOWN = 5;
	private static final int STRENGTH = 100;
	private static final ImageIcon crossingImage = Util
			.loadImage("crossing.gif");

	public Crossing(Location location) {
		super(location);
	}

	@Override
	public int getMovingRange() {
		return Integer.MAX_VALUE;
	}

	@Override
	public int getCoolDownPeriod() {
		return COOLDOWN;
	}

	@Override
	public Command getNextAction(World world) {

		Location targetLocation = Util.getRandomEmptyLocation(world);

		if (Util.isValidLocation(world, targetLocation)
				&& Util.isLocationEmpty(world, targetLocation)) {
			int x = targetLocation.getX();
			int y = targetLocation.getY();

			for (int i = 0; i < world.getWidth(); i++) {
				for (int j = 0; j < world.getHeight(); j++) {
					if (i == x && j == y) {
						continue;
					}
					if (i == x || j == y) {
						for (Item item : world.getItems()) {
							if (item.getLocation().equals(new Location(i, j))) {
								item.loseEnergy(Integer.MAX_VALUE);
							}
						}
					}
				}
			}

			return new MoveCommand(this, targetLocation);

		}

		return new WaitCommand();

	}

	@Override
	public ImageIcon getImage() {
		return crossingImage;
	}

	@Override
	public String getName() {
		return "Crossing";
	}

	@Override
	public int getStrength() {
		return STRENGTH;
	}

}
