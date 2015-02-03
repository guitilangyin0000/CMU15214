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
 * Your Rabbit AI.
 */
public class RabbitAI implements AI {

	// the breedBound of the rabbit
	private double breedBound;
	private final double RATIO = 1.1;

	public RabbitAI() {
		breedBound = 0;
	}

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
        // if it can breed, then breed
		if (animal.getEnergy() >= breedBound) {
			Location breedLocation = null;
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
							breedLocation = new Location(i, j);
							break;
						}
					}
				}
			}
            // if we can find a empty breed location, then breed
			if (breedLocation != null) {
				return new BreedCommand(animal, breedLocation);
			}
		} else {
			// if there is grass near current location, rabbit then eat it.
			for (Item item : items) {
				int item_x = item.getLocation().getX();
				int item_y = item.getLocation().getY();
				if ((item_y == y && Math.abs(item_x - x) < 2)
						|| (item_x == x && Math.abs(item_y - y) < 2)) {
					if (item.getName() == "Grass") {
						return new EatCommand(animal, item);
					}
				}
			}
            // else, walk to another grid near current location
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
        // if we cannot satisfy the need, wait instead
		return new WaitCommand();
	}

}
