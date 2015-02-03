package edu.cmu.cs.cs214.hw4.core.multipler;

import java.awt.Color;

import edu.cmu.cs.cs214.hw4.core.Tile;
import edu.cmu.cs.cs214.hw4.core.Word;

public class TripleLetter extends BaseMultipler{

	private final static String TL_NAME = "3LS";
	private final static Color THREE_LS = new Color(127, 255, 212);
	
	public TripleLetter() {
		super();

	}

	@Override
	public void changeWordValue(Word word, Tile tile) {
		word.addValue(2 * tile.getValue());
	}

	@Override
	public String getName() {
		return TL_NAME;
	}

	@Override
	public Color getColor() {
		// TODO Auto-generated method stub
		return THREE_LS;
	}

}
