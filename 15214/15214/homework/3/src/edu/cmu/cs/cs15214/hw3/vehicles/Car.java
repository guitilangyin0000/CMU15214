package edu.cmu.cs.cs15214.hw3.vehicles;

import javax.swing.ImageIcon;

import edu.cmu.cs.cs214.hw3.Location;
import edu.cmu.cs.cs214.hw3.Util;
import edu.cmu.cs.cs214.hw3.World;
import edu.cmu.cs.cs214.hw3.commands.Command;
import edu.cmu.cs.cs214.hw3.commands.MoveCommand;
import edu.cmu.cs.cs214.hw3.commands.WaitCommand;
import edu.cmu.cs.cs214.hw3.items.Item;

public class Car extends Vehicle {

	/**
	 * This is a simple implementation of a Car. Car can crash anything with
	 * lower strength, and at the same time, it could be crashed by truck, it
	 * will not change its direction after it has been defined once. And I use
	 * random number to control when it should add speed or minus speed. It has
	 * more flexible speed range than truck.
	 */

	private static final int STRENGTH = 150;
	private static final int INITIAL_COOLDOWN = 3;
	private static final int UPPER_COOLDOWN = 1;
	private static final int LOWER_COOLDOWN = 5;
	private static final int ACCELERATION = 2;
	private static final ImageIcon carImage = Util.loadImage("car.gif");

	// @require Util.isValidLocation(world, location) == true
	// @require Util.isLocationEmpty(world, location) == true
	public Car(Location location) {
		super(location);
		acc = ACCELERATION;
		// set and keep the direction of Car unchanged for the first time called
		dir = Util.getRandomDirection();
		cooldown = INITIAL_COOLDOWN;
	}

	@Override
	public int getMovingRange() {
		return 1;
	}

	@Override
	// @require world != null
	// @ensure result != null
	public Command getNextAction(World world) {
		if (world == null) {
			return null;
		}

		// speeding up and speeding down all have
		// 50% choice
		if (randomGenerator()) {
			if (canAddSpeed(UPPER_COOLDOWN)) {
				addSpeed();
			}
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
		// or, we make the vehicle wait
		return new WaitCommand();
	}

	@Override
	public ImageIcon getImage() {
		return carImage;
	}

	@Override
	public String getName() {
		return "Car";
	}

	@Override
	public int getStrength() {
		return STRENGTH;
	}

}
