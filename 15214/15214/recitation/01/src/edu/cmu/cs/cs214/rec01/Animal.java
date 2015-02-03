package edu.cmu.cs.cs214.rec01;

/**
 * <code>Animal</code> represents a creature of nature.
 */
public class Animal {
    private String name;
    private String sound;

    /**
     * Class constructor specifying the <code>Animal</code>'s name and the sound
     * the <code>Animal</code> makes.
     *
     * @param name
     *            identifier of the <code>Animal</code>
     * @param sound
     *            text that will be printed out when the <code>Animal</code>
     *            speaks
     */
    public Animal(String name, String sound) {
        this.name = name;
        this.sound = sound;
    }

    /**
     * Prints to standard out out the <code>Animal</code>'s name and the sound
     * it makes.
     */
    public void speak() {
        System.out.println(name + ": " + sound);
    }
}