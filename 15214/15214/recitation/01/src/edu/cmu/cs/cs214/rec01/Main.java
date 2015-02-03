package edu.cmu.cs.cs214.rec01;

/**
 * Class containing the main method for this example program.
 */
public class Main {
    /**
     * Method that is called when this program is run (the 'main' method).
     *
     * @param args
     *            command line arguments--not used in this program
     */
    public static void main(String[] args) {
        Animal wolf = new Animal("Ghost", "Bark bark.");
        Person jon = new Person("Jon Snow");

        jon.addPet(wolf);
        jon.speak();
    }
}