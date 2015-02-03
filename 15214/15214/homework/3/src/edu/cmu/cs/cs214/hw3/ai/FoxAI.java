package edu.cmu.cs.cs214.hw3.ai;

import java.util.Set;

import edu.cmu.cs.cs214.hw3.ArenaWorld;
import edu.cmu.cs.cs214.hw3.Location;
import edu.cmu.cs.cs214.hw3.commands.BreedCommand;
import edu.cmu.cs.cs214.hw3.commands.Command;
import edu.cmu.cs.cs214.hw3.commands.EatCommand;
import edu.cmu.cs.cs214.hw3.commands.MoveCommand;
import edu.cmu.cs.cs214.hw3.commands.WaitCommand;
import edu.cmu.cs.cs214.hw3.items.Item;
import edu.cmu.cs.cs214.hw3.items.animals.ArenaAnimal;

/**
 * Your Fox AI.
 */
public class FoxAI implements AI {

	// the breedBound is a bound that if the energy of the fox
	// is higher than this bound, I let the fox breed
	private double breedBound;
	private final double RATIO = 1.2;

	// the constructor of FoxAI
	public FoxAI() {
		breedBound = 0;
	}

	// set the value of breedBound
	public void setBreedBound(double bound) {
		breedBound = bound;
	}

	@Override
	public Command getNextAction(ArenaWorld world, ArenaAnimal animal) {

		int bound = animal.getMinimumBreedingEnergy();
		setBreedBound(RATIO * bound);
		int x = animal.getLocation().getX();
		int y = animal.getLocation().getY();
		Set<Item> items = world.searchSurroundings(animal);

		// if its energy is more than the breadBound energy
		// , then breed
		if (animal.getEnergy() >= breedBound) {
			Location breedLocation = null;
			for (int i = 0; i < world.getWidth(); i++) {
				for (int j = 0; j < world.getHeight(); j++) {
					// if we encounter current location, just continue
					if (i == x && j == y) {
						continue;
					}
					// if the traverse location is near the current location
					if ((j == y && Math.abs(i - x) < 2)
							|| (Math.abs(j - y) < 2 && i == x)) {
						boolean flag = true;
						for (Item item : items) {
							if (item.getLocation().equals(new Location(i, j))) {
								// if the traverse location is occupied by an item
								// then set this breedLocation flag to be false
								flag = false;
							}
						}
						// if the flag is true, we can breed here
						if (flag) {
							breedLocation = new Location(i, j);
							break;
						}
					}
				}
			}

			if (breedLocation != null) {
				return new BreedCommand(animal, breedLocation);
			}
		} else {
			// if there is a rabbit near the fox, the fox will 
			// directly eat it.
			for (Item item : items) {
				int item_x = item.getLocation().getX();
				int item_y = item.getLocation().getY();
				if ((item_y == y && Math.abs(item_x - x) < 2)
						|| (item_x == x && Math.abs(item_y - y) < 2)) {
					if (item.getName() == "Rabbit") {
						return new EatCommand(animal, item);
					}
				}
			}

			// else, move to an empty grid near current location
			Location targetLocation = null;
			for (int i = 0; i < world.getWidth(); i++) {
				for (int j = 0; j < world.getHeight(); j++) {
					if (i == x && j == y) {
						continue;
					}
					if ((j == y && Math.abs(i - x) < 2)
							|| (Math.abs(j - y) < 2 && i == x)) {
						boolean flag = true;
						for (Item item : items) {
							if (item.getLocation().equals(new Location(i, j))) {
								flag = false;
							}
						}
						if (flag) {
							targetLocation = new Location(i, j);
							break;
						}
					}
				}
			}

			if (targetLocation != null) {
				return new MoveCommand(animal, targetLocation);
			}

		}

		return new WaitCommand();
	}

}
