package edu.cmu.cs.cs214.hw4.core.multipler;

import java.awt.Color;

import edu.cmu.cs.cs214.hw4.core.Tile;
import edu.cmu.cs.cs214.hw4.core.Word;

public abstract class BaseMultipler implements Multipler{

	
	public BaseMultipler(){
	}
	
	public abstract Color getColor();
	
	public abstract String getName();
	
	public abstract void changeWordValue(Word word, Tile tile);
}
