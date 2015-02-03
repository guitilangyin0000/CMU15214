package edu.cmu.cs.cs214.rec03.inheritance;

public class FarmMain {

    public static void main(String[] args) {

        /*
         * 1. Why doesn't the following line compile?
         */

        // Animal animal = new Animal();

        Animal animalSheep = new Sheep();
        Animal animalCow = new Cow();
        Sheep sheep = new Sheep();
        Cow cow = new Cow();

        /*
         * 2. Even though sheep is a Sheep and cow is a Cow, how can we be sure
         * that we can call getProduct() on both objects? See
         * FarmMain#makeProduct(Animal).
         */

       System.out.println("\n2. Even though sheep is a Sheep and cow is a Cow,"
               + " how can we be sure that we can call getProduct() on both"
               + " objects? See FarmMain#makeProduct(Animal).");

       makeProduct(sheep);
       makeProduct(cow);

        /*
         * 3. What will the following print?
         */

       System.out.println("\n3. What will the following print?");

       animalSheep.makeNoise();
       animalCow.makeNoise();

        /*
         * 4. Which of the following assignments will fail?
         */

        Sheep newSheep0 = (Sheep) animalSheep;
        Sheep newSheep1 = (Sheep) sheep;
        Cow newCow0 = (Cow) animalSheep;

        /*
         * 5. How many legs does each animal have? (What is the output of the
         * following?)
         */

       System.out.println("\n5. How many legs does each animal have?");

       cow.countLegs();
       sheep.countLegs();

        /*
         * 6. What happens when the following code is run? Why?
         */

       System.out.println("\n4. What happens when the following code is run? Why?");

        final int NUM_ANIMALS = 10;
        Animal[] animals = new Animal[NUM_ANIMALS];
        Animal[] sheepArray = new Sheep[NUM_ANIMALS];
        for (int i = 0; i < NUM_ANIMALS; i++) {
            if (i % 2 == 1) {
                animals[i] = new Cow();
            } else {
                animals[i] = new Sheep();
            }
            sheepArray[i] = new Sheep(); // new Sheep();
        }

        /*
         * 7. If we iterate through animals , which makeNoise()
         * methods are called?
         */

        System.out.println("\n7. If we iterate through animals, which "
                + "makeNoise() methods are called?");

        for (int i = 0; i < NUM_ANIMALS; i++) {
            animals[i].makeNoise();
        }

        /*
         * 8. What will print out when the following is run?
         */

       System.out.println("\n8. What will print out when the following is run?");

       animalSheep.action();
       animalCow.action();

        Farm oldMacDonald = new MyFarm();

        /*
         * 9. Though MyFarm doesn't contain the method addAnimal(), there is no
         * error in the following lines. Why?
         */

       System.out.println("\n9. Though MyFarm doesn't contain the method "
               + "addAnimal(), there is no error in the following lines "
               + "(oldMacDonald is an instance of MyFarm). Why?");

       oldMacDonald.addAnimal(animalSheep);
       oldMacDonald.addAnimal(sheep);
    }

    /**
     * Given an {@link Animal}, prints to console the product it makes.
     *
     * @param animal
     *            the {@link Animal} to get a produce from
     */
    public static void makeProduct(Animal animal) {
        System.out.println(animal.getProduct());
    }

}