package edu.cmu.cs.cs214.hw3.items.animals;

	import javax.swing.ImageIcon;

	import edu.cmu.cs.cs214.hw3.Direction;
	import edu.cmu.cs.cs214.hw3.Food;
	import edu.cmu.cs.cs214.hw3.Location;
	import edu.cmu.cs.cs214.hw3.Util;
	import edu.cmu.cs.cs214.hw3.World;
	import edu.cmu.cs.cs214.hw3.commands.Command;
	import edu.cmu.cs.cs214.hw3.commands.MoveCommand;
	import edu.cmu.cs.cs214.hw3.commands.WaitCommand;
	import edu.cmu.cs.cs214.hw3.items.LivingItem;

	/**
	 * This is a simple implementation of a SillyCow. It never loses energy and has a 
	 * unchanged direction when it's firstly defined. It moves and will stop if it 
	 * encounters a item. It's so silly that it does not change its direction and stays
	 * on that location forever.
	 */
	public class SillyCow extends GeneralAnimal {
	    private static final ImageIcon sillyCowImage = Util.loadImage("sillyCow.gif");

	    private static final int MEAT_CALORIES = 150;
	    private static final int STRENGTH = 160;

	    private boolean isDead;
	    private Direction dir;

	    /**
	     * Create a new JumpTiger at <code>initialLocation</code>. The
	     * <code>initialLocation</code> must be valid and empty.
	     *
	     * @param initialLocation the location where the Gnat will be created
	     */
	    public SillyCow(Location initialLocation) {
	        super(initialLocation);
	        this.dir = Util.getRandomDirection();
	        this.isDead = false;
	    }

	    @Override
	    public ImageIcon getImage() {
	        return sillyCowImage;
	    }

	    @Override
	    public String getName() {
	        return "SillyCow";
	    }


	    @Override
	    public int getMeatCalories() {
	        return MEAT_CALORIES;
	    }

	    @Override
	    public void loseEnergy(int energy) {
	        isDead = true; // Dies if it loses energy.
	    }

	    @Override
	    public boolean isDead() {
	        return isDead;
	    }


	    @Override
	    public int getCoolDownPeriod() {
	        return 3;
	    }

	    @Override
	    public Command getNextAction(World world) {   	
	        Location targetLocation = new Location(location, dir);

	        if (Util.isValidLocation(world, targetLocation)
	                && Util.isLocationEmpty(world, targetLocation)) {
	            return new MoveCommand(this, targetLocation);
	        }

	        return new WaitCommand();
	    }

	    @Override
	    public int getStrength() {
	        return STRENGTH;
	    }

	    @Override
	    public int getEnergy() {
	        // doesn't every die, except when run over by a Vehicle
	        return 100;
	    }

	    @Override
	    public LivingItem breed() {
	        return null; // Never eats.
	    }

	    @Override
	    public void eat(Food food) {
	        // Never eats.
	    }

	    @Override
	    public int getMovingRange() {
	        return 1; // Can only move to adjacent locations.
	    }
	}
