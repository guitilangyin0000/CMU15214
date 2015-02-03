package edu.cmu.cs.cs214.hw4.core.multipler;

import java.awt.Color;

import edu.cmu.cs.cs214.hw4.core.Tile;
import edu.cmu.cs.cs214.hw4.core.Word;

public class Normal extends BaseMultipler{

	private final static String NORMAL_NAME = "NOR";
	private final static Color NORMAL = new Color(255, 235, 205);
	
	public Normal() {
		super();
	}

	@Override
	public void changeWordValue(Word word, Tile tile) {
		return;		
	}

	@Override
	public String getName() {
		return NORMAL_NAME;
	}
	
	public Color getColor(){
		return NORMAL;
	}

}
