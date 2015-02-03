package edu.cmu.cs.cs214.hw3.items.animals;

import javax.swing.ImageIcon;

import edu.cmu.cs.cs214.hw3.Food;
import edu.cmu.cs.cs214.hw3.Location;
import edu.cmu.cs.cs214.hw3.World;
import edu.cmu.cs.cs214.hw3.commands.Command;
import edu.cmu.cs.cs214.hw3.items.LivingItem;

public abstract class GeneralAnimal implements LivingItem{
	
	protected Location location;
	
	public GeneralAnimal(Location initialLocation){
        this.location = initialLocation;
    }

    public abstract ImageIcon getImage();

    public abstract String getName();

    public Location getLocation() {
        return location;
    }

    public int getPlantCalories() {
        return 0;
    }

    public abstract int getMeatCalories();

    public abstract void loseEnergy(int energy);
   

    public abstract boolean isDead();

    public void moveTo(Location targetLocation) {
        location = targetLocation;
    }

    public abstract int getCoolDownPeriod();

    public abstract Command getNextAction(World world);

    public abstract int getStrength();
    
    public abstract int getEnergy();

    public abstract LivingItem breed();

    public abstract void eat(Food food);

    public abstract int getMovingRange();

}
