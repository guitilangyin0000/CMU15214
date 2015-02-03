package edu.cmu.cs.cs214.rec01;

/**
 * <code>Person</code> is a class representing a pet-owner.
 */
public class Person {
    private String name;
    private Animal[] pets;
    private int numPets;

    /**
     * Class constructor specifying the name/identifier of the
     * <code>Person</code>.
     *
     * @param name
     *            identifier of the <code>Person</code>
     */
    public Person(String name) {
        this.name = name;
        this.pets = new Animal[42];
    }

    /**
     * Adds the specified <code>Animal</code> to the <code>Person</code>'s
     * extensive (or not) collection of pets.
     *
     * @param newPet
     *            animal to enslave
     */
    public void addPet(Animal newPet) {
		//TODO: FILL ME IN
    }

    /**
     * Prints out a list of what the <code>Person</code>'s pets say or, if the
     * <code>Person</code> does not have any pets, a declaration of loneliness.
     */
    public void speak() {
        if (numPets > 0) {
            System.out.println("I am " + name + " and my pets say:");
            for (int i = 0; i < numPets; i++) {
                pets[i].speak();
            }
        } else {
            System.out.println("My name is " + name
                    + " and I am so, so lonely.");
        }
    }
}