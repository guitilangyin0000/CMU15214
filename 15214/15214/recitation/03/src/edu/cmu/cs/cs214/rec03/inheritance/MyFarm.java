package edu.cmu.cs.cs214.rec03.inheritance;

public class MyFarm extends Farm {

    private static final int NUM_ANIMALS = 10;

    public MyFarm() {
        for (int i = 0; i < NUM_ANIMALS; i++) {
            if (i % 5 == 4) { // arbitrary but deterministic
                addAnimal(new Cow());
            } else {
                addAnimal(new Sheep());
            }
        }
    }

}
