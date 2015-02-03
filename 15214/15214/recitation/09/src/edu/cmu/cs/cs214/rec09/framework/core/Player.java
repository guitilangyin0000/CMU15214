package edu.cmu.cs.cs214.rec09.framework.core;

/**
 * Represents a player in the game. Each player has an associated name and
 * symbol (the first letter of the player's name).
 */
public class Player {

    private final String name;

    public Player(String n) {
        name = n;
    }

    public String getName() {
        return name;
    }

    public String getSymbol() {
        return name.substring(0, 1);
    }

}