package edu.cmu.cs.cs15214.hw3.miracles;

import javax.swing.ImageIcon;

import edu.cmu.cs.cs214.hw3.Actor;
import edu.cmu.cs.cs214.hw3.Location;
import edu.cmu.cs.cs214.hw3.World;
import edu.cmu.cs.cs214.hw3.commands.Command;
import edu.cmu.cs.cs214.hw3.items.Item;
import edu.cmu.cs.cs214.hw3.items.MoveableItem;

public abstract class Miracle implements Actor, MoveableItem{
	
	protected Location location;
	protected boolean isCrashed;
	
	public Miracle(Location location){
		this.location = location;
		isCrashed = false;
	}
	
	public abstract int getCoolDownPeriod();

    public abstract Command getNextAction(World world);
    
    public abstract ImageIcon getImage();

    public abstract String getName();

    public Location getLocation(){
    	return location;
    }

    public abstract int getStrength();

    public void loseEnergy(int energy){
    	isCrashed = true;
    }

    public boolean isDead(){
    	return isCrashed == true;
    }
    
	public int getPlantCalories() {
		return 0;
	}

	public int getMeatCalories() {
		return 0;
	}

	public void moveTo(Location targetLocation) {
		location = targetLocation;	
	}

}
