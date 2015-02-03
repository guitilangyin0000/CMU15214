package edu.cmu.cs.cs214.hw3.items.animals;

import edu.cmu.cs.cs214.hw3.Location;
import edu.cmu.cs.cs214.hw3.World;
import edu.cmu.cs.cs214.hw3.ai.AI;
import edu.cmu.cs.cs214.hw3.commands.Command;
import edu.cmu.cs.cs214.hw3.items.LivingItem;

public abstract class GeneralArenaAnimal extends GeneralAnimal implements ArenaAnimal{

	protected int energy;
	protected final AI ai;
	
    public GeneralArenaAnimal(AI ai, Location initialLocation){
    	super(initialLocation);
    	this.ai = ai;
    }
	
	public LivingItem Breed() {
        GeneralArenaAnimal child = getChild();
        this.energy = energy / 2;
        child.energy = energy / 2;
        return child;
    }
	
	public abstract GeneralArenaAnimal getChild();


    public abstract int getCoolDownPeriod();

    public int getEnergy() {
        return energy;
    }

    public abstract int getMaxEnergy();

    public int getMeatCalories() {
        return energy;
    }

    public abstract int getMinimumBreedingEnergy();
    
    public Command getNextAction(World world) {
        Command nextAction = ai.getNextAction(world, this);
        this.energy--;
        return nextAction;
    }

    public abstract int getViewRange();

    public boolean isDead() {
        return energy <= 0;
    }

    public void loseEnergy(int energyLoss) {
        this.energy = this.energy - energyLoss;
    }


}
