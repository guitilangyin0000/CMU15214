package edu.cmu.cs.cs214.hw4.core.multipler;

import java.awt.Color;

import edu.cmu.cs.cs214.hw4.core.Tile;
import edu.cmu.cs.cs214.hw4.core.Word;

public interface Multipler {
	
	public void changeWordValue(Word word, Tile tile);
	
	public String getName();
	
	public Color getColor();

}
