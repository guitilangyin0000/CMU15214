package edu.cmu.cs.cs214.hw4.core.multipler;

import java.awt.Color;

import edu.cmu.cs.cs214.hw4.core.Tile;
import edu.cmu.cs.cs214.hw4.core.Word;

public class TripleWord extends BaseMultipler{

	private final static String TW_NAME = "3WS";
	private final static Color THREE_WS = new Color(255, 165, 0);
	
	public TripleWord() {
		super();
	}

	@Override
	public void changeWordValue(Word word, Tile tile) {
		word.setTimer(3 * word.getTimer());	
	}

	@Override
	public String getName() {
		return TW_NAME;
	}

	@Override
	public Color getColor() {
		// TODO Auto-generated method stub
		return THREE_WS;
	}

}
