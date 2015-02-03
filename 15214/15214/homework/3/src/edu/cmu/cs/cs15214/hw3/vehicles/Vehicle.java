package edu.cmu.cs.cs15214.hw3.vehicles;

import javax.swing.ImageIcon;

import edu.cmu.cs.cs214.hw3.Actor;
import edu.cmu.cs.cs214.hw3.Direction;
import edu.cmu.cs.cs214.hw3.Util;
import edu.cmu.cs.cs214.hw3.Location;
import edu.cmu.cs.cs214.hw3.World;
import edu.cmu.cs.cs214.hw3.commands.Command;
import edu.cmu.cs.cs214.hw3.items.MoveableItem;

public abstract class Vehicle implements MoveableItem, Actor {

	protected Location location;
	protected int cooldown;
	protected boolean isCrashed;
	// the acceleration of the vehicle when it adds or minus speed
	protected int acc;
	protected Direction dir;

	public Vehicle(Location location) {
		this.location = location;
		isCrashed = false;
	}

	// check if the vehicle can speed up
	public boolean canAddSpeed(int upperBound) {
		int changedSpeed = cooldown - acc;
		if (changedSpeed < upperBound) {
			return false;
		}
		return true;
	}

	// check if the vehicle can speed down
	public boolean canMinusSpeed(int lowerBound) {
		int changedSpeed = cooldown + acc;
		if (changedSpeed > lowerBound) {
			return false;
		}
		return true;
	}

	// speed up
	public void addSpeed() {
		cooldown -= acc;
	}

	// speed down
	public void minusSpeed() {
		cooldown += acc;
	}

	// the vehicle can only turn when it's on a low speed
	public boolean canTurn(int lowerBound) {
		return cooldown == lowerBound;
	}

	public abstract Command getNextAction(World world);

	public abstract ImageIcon getImage();

	public abstract String getName();

	public Location getLocation() {
		return location;
	}

	public abstract int getStrength();

	public void loseEnergy(int energy) {
		isCrashed = true;
	}

	public boolean isDead() {
		return isCrashed == true;
	}

	// the random number generation method, return 0 or 1
	public boolean randomGenerator() {
		int number = Util.RAND.nextInt(2);
		return number == 0;
	}

	public int getPlantCalories() {
		return 0;
	}

	public int getMeatCalories() {
		return 0;
	}

	public int getCoolDownPeriod() {
		return cooldown;
	}

	public Direction getDirection(){
		return dir;
	}
	
	public void moveTo(Location targetLocation) {
		location = targetLocation;
	}
}
