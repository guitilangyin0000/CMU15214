package edu.cmu.cs.cs214.rec03.inheritance;

public class Cow extends Animal {

    public Cow() {
        super("Cow");
    }

    @Override
    public void makeNoise() {
        System.out.println("'Moo!'");
    }

    @Override
    public void action() {
        super.action();
        System.out.println("Cow Action");
    }

    @Override
    public String getProduct() {
        return "Milk";
    }

}