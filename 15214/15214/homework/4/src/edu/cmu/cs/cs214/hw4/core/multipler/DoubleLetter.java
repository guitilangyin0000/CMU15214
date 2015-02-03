package edu.cmu.cs.cs214.hw4.core.multipler;

import java.awt.Color;

import edu.cmu.cs.cs214.hw4.core.Tile;
import edu.cmu.cs.cs214.hw4.core.Word;

public class DoubleLetter extends BaseMultipler{

	private final static String DL_NAME = "2LS";
	private final static Color TWO_LS = new Color(151, 255, 255);
	
	public DoubleLetter(){
		super();
	}

	@Override
	public void changeWordValue(Word word, Tile tile) {	
		word.addValue(tile.getValue());
	}

	@Override
	public String getName() {
		return DL_NAME;
	}

	@Override
	public Color getColor() {
		return TWO_LS;
	}
}
