package edu.cmu.cs.cs15214.hw3.miracles;

import javax.swing.ImageIcon;

import edu.cmu.cs.cs214.hw3.Location;
import edu.cmu.cs.cs214.hw3.Util;
import edu.cmu.cs.cs214.hw3.World;
import edu.cmu.cs.cs214.hw3.commands.Command;
import edu.cmu.cs.cs214.hw3.commands.MoveCommand;
import edu.cmu.cs.cs214.hw3.commands.WaitCommand;
import edu.cmu.cs.cs214.hw3.items.Grass;

public class SunShine extends Miracle{

	/**
	 * Sunshine is a bless for grass, if there is a sunshine in a location, it will
	 * change all empty grids into grass for surrounded 3 * 3 grids. It can jump to 
	 * anywhere in the world, so any corner could have the opportunity of having such
	 * bless. By the way, this kind of bless may be terminated by the vehicles.
	 */

	private static final int COOLDOWN = 3;
	private static final int STRENGTH = 200;
	private static final ImageIcon sunShineImage = Util.loadImage("sunShine.gif");
	
	public SunShine(Location location) {
		super(location);
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
	            	if (i == x && j == y){
	            		continue;
	            	}
	                if (Math.abs(i - x) < 2 && Math.abs(j - y) < 2){
	                	if (Util.isLocationEmpty(world, new Location(i, j))){
	                		Grass newGrass = new Grass(new Location(i, j));
	                		world.addItem(newGrass);
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
		return sunShineImage;
	}

	@Override
	public String getName() {
		return "SunShine";
	}

	@Override
	public int getStrength() {
		return STRENGTH;
	}

	@Override
	public int getMovingRange() {
		return Integer.MAX_VALUE;
	}
	

}
