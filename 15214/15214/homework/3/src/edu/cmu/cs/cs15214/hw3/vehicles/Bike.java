package edu.cmu.cs.cs15214.hw3.vehicles;

import javax.swing.ImageIcon;

import edu.cmu.cs.cs214.hw3.Location;
import edu.cmu.cs.cs214.hw3.Util;
import edu.cmu.cs.cs214.hw3.World;
import edu.cmu.cs.cs214.hw3.commands.Command;
import edu.cmu.cs.cs214.hw3.commands.MoveCommand;
import edu.cmu.cs.cs214.hw3.commands.WaitCommand;
import edu.cmu.cs.cs214.hw3.items.Item;

public class Bike extends Vehicle {

	/**
	 * This is a simple implementation of a Bike. Bar can crash anything with
	 * lower strength, and at the same time, it could be crashed by truck or
	 * car, it will not change its direction after it has been defined once.
	 * Moreover, the bike will speed up to its upper bound, then it will speed
	 * down to its lower bound, then recycle.
	 */

	private static final int STRENGTH = 120;
	private static final int INITIAL_COOLDOWN = 4;
	private static final int UPPER_COOLDOWN = 2;
	private static final int LOWER_COOLDOWN = 6;
	private static final int ACCELERATION = 1;
	private static final ImageIcon bikeImage = Util.loadImage("bike.gif");

	// record the status of speeding up or speeding down
	private boolean accFlag;

	// @require Util.isValidLocation(world, location) == true
	// @require Util.isLocationEmpty(world, location) == true
	public Bike(Location location) {
		super(location);
		acc = ACCELERATION;
		dir = Util.getRandomDirection();
		cooldown = INITIAL_COOLDOWN;
		accFlag = true;
	}

	@Override
	public int getMovingRange() {
		return 1;
	}

	@Override
	// @require world != null
	// @ensure result != null
	public Command getNextAction(World world) {
		// handle exception if world != null
		if (world == null){
			return null;
		}
		// use the acceleration flag to judge
		// whether to speed up or speed down
		if (accFlag) {
			// make sure it's on the low speed
			if (canAddSpeed(UPPER_COOLDOWN)) {
				addSpeed();
			} else {
				// set the flag reversed
				accFlag = !accFlag;
			}
		} else {
			// if we can speed down
			if (canMinusSpeed(LOWER_COOLDOWN)) {
				minusSpeed();
			} else {
				// set the flag reversed
				accFlag = !accFlag;
			}
		}
		
		Location targetLocation = new Location(this.getLocation(), dir);
		// keep the direction unchanged
		if (!Util.isValidLocation(world, targetLocation)) {
			// if the vehicle's target location is out of bound,
			// then make this vehicle crashed
			loseEnergy(Integer.MAX_VALUE);
		} else {
			if (Util.isLocationEmpty(world, targetLocation)) {
				return new MoveCommand(this, targetLocation);
			}
			// if there is an item at the target location, 
			// then make the crash function realized
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
		// or, we have to make the vehicle wait
		return new WaitCommand();
	}

	@Override
	public ImageIcon getImage() {
		return bikeImage;
	}

	@Override
	public String getName() {
		return "Bike";
	}

	@Override
	public int getStrength() {
		return STRENGTH;
	}
}
