package edu.cmu.cs.cs214.rec03.inheritance;

public class Sheep extends Animal {
    private static final int SHEEP_NUM_LEGS = 2;

    public Sheep() {
        super("Sheep");
        numLegs = SHEEP_NUM_LEGS; // "Four legs good, two legs better!"
    }

    @Override
    public void makeNoise() {
        System.out.println("'Baahh.'");
    }

    @Override
    public void action() {
        System.out.println("Sheep Action");
    }

    @Override
    public String getProduct() {
        return "Wool";
    }

}
