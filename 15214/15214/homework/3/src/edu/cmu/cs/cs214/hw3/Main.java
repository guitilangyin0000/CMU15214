package edu.cmu.cs.cs214.hw3;

import javax.swing.SwingUtilities;

import edu.cmu.cs.cs15214.hw3.miracles.BlackDeath;
import edu.cmu.cs.cs15214.hw3.miracles.Crossing;
import edu.cmu.cs.cs15214.hw3.miracles.SunShine;
import edu.cmu.cs.cs15214.hw3.vehicles.Bike;
import edu.cmu.cs.cs15214.hw3.vehicles.Car;
import edu.cmu.cs.cs15214.hw3.vehicles.Truck;
import edu.cmu.cs.cs214.hw3.ai.FoxAI;
import edu.cmu.cs.cs214.hw3.ai.RabbitAI;
import edu.cmu.cs.cs214.hw3.items.Gardener;
import edu.cmu.cs.cs214.hw3.items.Grass;
import edu.cmu.cs.cs214.hw3.items.animals.Fox;
import edu.cmu.cs.cs214.hw3.items.animals.Gnat;
import edu.cmu.cs.cs214.hw3.items.animals.HungryFrog;
import edu.cmu.cs.cs214.hw3.items.animals.JumpTiger;
import edu.cmu.cs.cs214.hw3.items.animals.Rabbit;
import edu.cmu.cs.cs214.hw3.items.animals.SillyCow;
import edu.cmu.cs.cs214.hw3.staff.WorldImpl;
import edu.cmu.cs.cs214.hw3.staff.WorldUI;

/**
 * The Main class initialize a world with some {@link Grass}, {@link Rabbit}s,
 * {@link Fox}es, {@link Gnat}s, {@link Gardener}, etc.
 *
 * You may modify or add Items/Actors to the World.
 *
 */
public class Main {

    static final int X_DIM = 40;
    static final int Y_DIM = 40;
    static final int SPACES_PER_GRASS = 7;
    static final int INITIAL_GRASS = X_DIM * Y_DIM / SPACES_PER_GRASS;
    static final int INITIAL_GNATS = INITIAL_GRASS / 4;
    static final int INITIAL_RABBITS = INITIAL_GRASS / 4;
    static final int INITIAL_FOXES = INITIAL_GRASS / 32;
    static final int INITIAL_JUMPTIGER = INITIAL_GRASS / 32;
    static final int INITIAL_SILLYCOW = INITIAL_GRASS / 32;
    static final int INITIAL_HUNGRYFROG = INITIAL_GRASS / 32;
    static final int INITIAL_TRUCK = INITIAL_GRASS / 64;
    static final int INITIAL_CAR = INITIAL_GRASS / 32;
    static final int INITIAL_BIKE = INITIAL_GRASS / 32;
    static final int INITIAL_SUNSHINE = INITIAL_GRASS / 64;
    static final int INITIAL_BLACKDEATH = 1;
    static final int INITIAL_CROSSING = 1;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Main().createAndShowWorld();
            }
        });
    }

    public void createAndShowWorld() {
        World world = new WorldImpl(X_DIM, Y_DIM);
        initialize(world);
        new WorldUI(world).show();
    }

    public void initialize(World world) {
        addGrass(world);
        world.addActor(new Gardener());

        addGnats(world);
        addFoxes(world);
        addRabbits(world);
        addJumpTiger(world);
        addSillyCow(world);
        addHungryFrog(world);
        addTruck(world);
        addCar(world);
        addBike(world);
        addSunShine(world);
        addBlackDeath(world);
        addCrossing(world);

        // TODO: You may add your own creatures here!
    }

    private void addGrass(World world) {
        for (int i = 0; i < INITIAL_GRASS; i++) {
            Location loc = Util.getRandomEmptyLocation(world);
            world.addItem(new Grass(loc));
        }
    }

    private void addCrossing(World world) {
        for (int i = 0; i < INITIAL_CROSSING; i++) {
            Location loc = Util.getRandomEmptyLocation(world);
            Crossing crossing = new Crossing(loc);
            world.addItem(crossing);
            world.addActor(crossing);
        }
    }
    
    private void addBlackDeath(World world) {
        for (int i = 0; i < INITIAL_BLACKDEATH; i++) {
            Location loc = Util.getRandomEmptyLocation(world);
            BlackDeath blackDeath = new BlackDeath(loc);
            world.addItem(blackDeath);
            world.addActor(blackDeath);
        }
    }
    
    private void addSunShine(World world) {
        for (int i = 0; i < INITIAL_SUNSHINE; i++) {
            Location loc = Util.getRandomEmptyLocation(world);
            SunShine sunShine = new SunShine(loc);
            world.addItem(sunShine);
            world.addActor(sunShine);
        }
    }
    
    private void addGnats(World world) {
        for (int i = 0; i < INITIAL_GNATS; i++) {
            Location loc = Util.getRandomEmptyLocation(world);
            Gnat gnat = new Gnat(loc);
            world.addItem(gnat);
            world.addActor(gnat);
        }
    }
    
    private void addSillyCow(World world) {
        for (int i = 0; i < INITIAL_SILLYCOW; i++) {
            Location loc = Util.getRandomEmptyLocation(world);
            SillyCow sillyCow = new SillyCow(loc);
            world.addItem(sillyCow);
            world.addActor(sillyCow);
        }
    }
    
    private void addHungryFrog(World world) {
        for (int i = 0; i < INITIAL_HUNGRYFROG; i++) {
            Location loc = Util.getRandomEmptyLocation(world);
            HungryFrog hungryFrog = new HungryFrog(loc);
            world.addItem(hungryFrog);
            world.addActor(hungryFrog);
        }
    }
    
    private void addJumpTiger(World world) {
        for (int i = 0; i < INITIAL_JUMPTIGER; i++) {
            Location loc = Util.getRandomEmptyLocation(world);
            JumpTiger jumpTiger = new JumpTiger(loc);
            world.addItem(jumpTiger);
            world.addActor(jumpTiger);
        }
    }
    
    private void addTruck(World world) {
        for (int i = 0; i < INITIAL_TRUCK; i++) {
            Location loc = Util.getRandomEmptyLocation(world);
            Truck truck = new Truck(loc);
            world.addItem(truck);
            world.addActor(truck);
        }
    }
    
    private void addCar(World world) {
        for (int i = 0; i < INITIAL_CAR; i++) {
            Location loc = Util.getRandomEmptyLocation(world);
            Car car = new Car(loc);
            world.addItem(car);
            world.addActor(car);
        }
    }
    
    private void addBike(World world) {
        for (int i = 0; i < INITIAL_BIKE; i++) {
            Location loc = Util.getRandomEmptyLocation(world);
            Bike bike = new Bike(loc);
            world.addItem(bike);
            world.addActor(bike);
        }
    }

    private void addFoxes(World world) {
        FoxAI foxAI = new FoxAI();
        for (int i = 0; i < INITIAL_FOXES; i++) {
            Location loc = Util.getRandomEmptyLocation(world);
            Fox fox = new Fox(foxAI, loc);
            world.addItem(fox);
            world.addActor(fox);
        }
    }

    private void addRabbits(World world) {
        RabbitAI rabbitAI = new RabbitAI();
        for (int i = 0; i < INITIAL_RABBITS; i++) {
            Location loc = Util.getRandomEmptyLocation(world);
            Rabbit rabbit = new Rabbit(rabbitAI, loc);
            world.addItem(rabbit);
            world.addActor(rabbit);
        }
    }

}
