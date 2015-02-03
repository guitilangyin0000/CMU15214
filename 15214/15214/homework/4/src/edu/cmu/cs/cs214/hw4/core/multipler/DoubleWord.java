package edu.cmu.cs.cs214.hw4.core.multipler;

import java.awt.Color;

import edu.cmu.cs.cs214.hw4.core.Tile;
import edu.cmu.cs.cs214.hw4.core.Word;

public class DoubleWord extends BaseMultipler{

	private final static String DW_NAME = "2WS";
	private final static Color TWO_WS = new Color(255, 174, 185);
	
	public DoubleWord() {
		super();
	}

	@Override
	public void changeWordValue(Word word, Tile tile) {
		word.setTimer(2 * word.getTimer());		
	}

	@Override
	public String getName() {
		return DW_NAME;
	}

	@Override
	public Color getColor() {
		// TODO Auto-generated method stub
		return TWO_WS;
	}

	
}
