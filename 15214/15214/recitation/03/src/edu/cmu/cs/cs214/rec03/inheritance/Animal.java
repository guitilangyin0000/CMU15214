package edu.cmu.cs.cs214.rec03.inheritance;

public abstract class Animal {

    /** The number of legs this <code>Animal</code> has. */
    protected int numLegs;
    private String name;

    /**
     * @param name this Animal's unique identifier
     */
    public Animal(String name) {
        this.name = name;
        numLegs = 4;
    }

    /**
     * Prints the number of legs this <code>Animal</code> has to console.
     */
    public void countLegs() {
        System.out.println(numLegs);
    }

    public String getName() {
        return name;
    }

    /**
     * Prints this <code>Animal</code>'s noise to console.
     */
    public void makeNoise() {
        System.out.println("Animal noise");
    }

    /**
     * Prints the action of this <code>Animal</code> to console.
     */
    public void action() {
        System.out.println("Animal action");
        makeNoise();
    }

    /**
     * Returns a String representing the product that this <code>Animal</code>
     * produces.
     *
     * @return the product of this <code>Animal</code>
     */
    public abstract String getProduct();

}