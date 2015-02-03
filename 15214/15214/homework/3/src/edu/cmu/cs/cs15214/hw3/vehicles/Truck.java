package edu.cmu.cs.cs15214.hw3.vehicles;

import javax.swing.ImageIcon;

import edu.cmu.cs.cs214.hw3.Location;
import edu.cmu.cs.cs214.hw3.Util;
import edu.cmu.cs.cs214.hw3.World;
import edu.cmu.cs.cs214.hw3.commands.Command;
import edu.cmu.cs.cs214.hw3.commands.MoveCommand;
import edu.cmu.cs.cs214.hw3.commands.WaitCommand;
import edu.cmu.cs.cs214.hw3.items.Item;

public class Truck extends Vehicle {

	/**
	 * This is a simple implementation of a Truck. Truck can crash anything with
	 * lower strength, if it's at its lower speed, it will turn immediately. And
	 * I use random number to control when it should add speed or minus speed.
	 */
	private static final int STRENGTH = 300;
	private static final int INITIAL_COOLDOWN = 4;
	private static final int UPPER_COOLDOWN = 3;
	private static final int LOWER_COOLDOWN = 5;
	private static final int ACCELERATION = 1;
	private static final ImageIcon truckImage = Util.loadImage("truck.gif");

	// @require Util.isValidLocation(world, location) == true
	// @require Util.isLocationEmpty(world, location) == true
	public Truck(Location location) {
		super(location);
		cooldown = INITIAL_COOLDOWN;
		acc = ACCELERATION;
		dir = Util.getRandomDirection();
	}

	// @require: world != null
	// @ensure: result != null
	public Command getNextAction(World world) {
        if (world == null){
        	return null;
        }
        // if we can turn, then turn directly
		if (canTurn(LOWER_COOLDOWN)) {
			dir = Util.getRandomDirection();
		}
		// then we can speed up or speed down
		// with 50% chance
		if (randomGenerator()) {
			if (canAddSpeed(UPPER_COOLDOWN)) {
				addSpeed();
			} else {
				if (canMinusSpeed(LOWER_COOLDOWN)) {
					minusSpeed();
				}
			}

			// make the crash function realized
			Location targetLocation = new Location(this.getLocation(), dir);
			if (!Util.isValidLocation(world, targetLocation)) {
				loseEnergy(Integer.MAX_VALUE);
			} else {
				if (Util.isLocationEmpty(world, targetLocation)) {
					return new MoveCommand(this, targetLocation);
				}
				for (Item item : world.getItems()) {
					if (item.getLocation().equals(targetLocation)) {
						if (getStrength() > item.getStrength()) {
							item.loseEnergy(Integer.MAX_VALUE);
							return new MoveCommand(this, targetLocation);
						} else {
							loseEnergy(Integer.MAX_VALUE);
							break;
						}
					}
				}
			}
		}
		// or we just make the vehicle wait
		return new WaitCommand();
	}

	@Override
	public ImageIcon getImage() {
		return truckImage;
	}

	@Override
	public String getName() {
		return "Truck";
	}

	@Override
	public int getStrength() {
		return STRENGTH;
	}

	@Override
	public int getMovingRange() {
		return 1;
	}

}
