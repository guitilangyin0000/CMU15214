package edu.cmu.cs.cs214.hw3;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.cmu.cs.cs15214.hw3.vehicles.Bike;
import edu.cmu.cs.cs15214.hw3.vehicles.Car;
import edu.cmu.cs.cs15214.hw3.vehicles.Truck;
import edu.cmu.cs.cs214.hw3.commands.Command;
import edu.cmu.cs.cs214.hw3.commands.InvalidCommandException;
import edu.cmu.cs.cs214.hw3.staff.WorldImpl;

public class VehiclesTest {
	
    private World world;
    private Truck truck;
    private Car car;
    private Bike bike;
    
	/** Called before each test case method. */
	@Before
	public void setUp() throws Exception {
		world = new WorldImpl(5, 5);
        truck = new Truck(new Location(2, 2));
        car = new Car(new Location(0, 0));
        bike = new Bike(new Location(4, 4));
        world.addItem(truck);
        world.addActor(truck);
        world.addItem(car);
        world.addActor(car);
        world.addItem(bike);
        world.addActor(bike);
	}

	/** Called after each test case method. */
	@After
	public void tearDown() throws Exception {
		// Don't need to do anything here.
	}

	/** Tests that  */
	@Test
	public void testName() {	
		assertEquals("Truck", truck.getName());
		assertEquals("Car", car.getName());
		assertEquals("Bike", bike.getName());
	}
	
	@Test
	public void testMoveRange() {	
		assertEquals(1, truck.getMovingRange());
		assertEquals(1, car.getMovingRange());
		assertEquals(1, bike.getMovingRange());
	}
	
	@Test
	public void testStrength() {	
		assertEquals(300, truck.getStrength());
		assertEquals(150, car.getStrength());
		assertEquals(120, bike.getStrength());
	}
	
	@Test
	public void testPlantColory() {	
		assertEquals(0, truck.getPlantCalories());
		assertEquals(0, car.getPlantCalories());
		assertEquals(0, bike.getPlantCalories());
	}
	
	@Test
	public void testMeatColory() {	
		assertEquals(0, truck.getMeatCalories());
		assertEquals(0, car.getMeatCalories());
		assertEquals(0, bike.getMeatCalories());
	}
	
	@Test
	public void testLoseEnergy() {
		truck.loseEnergy(0);
		car.loseEnergy(0);
		bike.loseEnergy(0);
		assertEquals(true, truck.isDead());
		assertEquals(true, car.isDead());
		assertEquals(true, bike.isDead());
	}
	
	@Test
	public void testSpeed() {
		truck.addSpeed();
		truck.minusSpeed();
		car.addSpeed();
		car.minusSpeed();
		bike.addSpeed();
		bike.minusSpeed();
		assertEquals(4, truck.getCoolDownPeriod());
		assertEquals(3, car.getCoolDownPeriod());
		assertEquals(4, bike.getCoolDownPeriod());
	}
	
	@Test
	public void testChangeSpeed() {
		assertEquals(true, truck.canAddSpeed(3));
		assertEquals(true, truck.canMinusSpeed(5));
		assertEquals(true, car.canAddSpeed(1));
		assertEquals(true, car.canMinusSpeed(5));
		assertEquals(true, bike.canAddSpeed(2));
		assertEquals(true, bike.canMinusSpeed(6));
	}
	
	@Test
	public void testLocation() {
		assertEquals(2, truck.getLocation().getX());
		assertEquals(2, truck.getLocation().getY());
		assertEquals(0, car.getLocation().getX());
		assertEquals(0, car.getLocation().getY());
		assertEquals(4, bike.getLocation().getX());
		assertEquals(4, bike.getLocation().getY());
	}
	
	
	@Test
	public void testTruckNextAction() throws InvalidCommandException {
		Command command = truck.getNextAction(world);
		command.execute(world);
		assertTrue(truck.getDirection().equals(Direction.EAST) 
				|| truck.getDirection().equals(Direction.NORTH)
			    || truck.getDirection().equals(Direction.SOUTH)
			    || truck.getDirection().equals(Direction.WEST));
		
		assertTrue(truck.getCoolDownPeriod() == 3
				|| truck.getCoolDownPeriod() == 5);
	}
	
	@Test
	public void testCarNextAction() throws InvalidCommandException {
		Command command = car.getNextAction(world);
		command.execute(world);
		assertTrue(car.getDirection().equals(Direction.EAST) 
				|| car.getDirection().equals(Direction.NORTH)
			    || car.getDirection().equals(Direction.SOUTH)
			    || car.getDirection().equals(Direction.WEST));
		assertTrue(car.getCoolDownPeriod() == 5
				|| car.getCoolDownPeriod() == 1);
	}
	
	@Test
	public void testBikeNextAction() throws InvalidCommandException {
		Command command = bike.getNextAction(world);
		command.execute(world);
		assertTrue(bike.getDirection().equals(Direction.EAST) 
				|| bike.getDirection().equals(Direction.NORTH)
			    || bike.getDirection().equals(Direction.SOUTH)
			    || bike.getDirection().equals(Direction.WEST));
		assertEquals(3, bike.getCoolDownPeriod());
	}
}

//targetLocation.equals(yourVehicle.getLocation())