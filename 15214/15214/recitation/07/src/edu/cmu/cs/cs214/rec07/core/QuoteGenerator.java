package edu.cmu.cs.cs214.rec07.core;

public class QuoteGenerator {

    private static final String DEFAULT_NAME = "Pumpkin King";

    private static final String[] ONE_LINERS = {
        "The human torch was denied a bank loan.",
        "Unique New York. Unique New York.",
        "Ha ha! Ha ha ha ho! Ha ha ho. Ha oh!",
        "You ate the whole wheel of cheese?",
        "Hey, is that pork rind a Bob Dawson's?",
        "Milk was a bad choice.",
        "The skeleton ran out of shampoo in the shower.",
        "Bark twice if you're in Milwaukee.",
        "How now brown cow. How now brown cow.",
        "The arsonist has oddly shaped feet. ",
    };

    private int counter = 0;
    private String name = DEFAULT_NAME;

    public void setName(String nm) {
        name = nm;
    }

    public String getName() {
        return name;
    }

    public String generateQuote() {
        return ONE_LINERS[counter++ % ONE_LINERS.length];
    }

}
